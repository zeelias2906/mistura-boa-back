package com.mistura_boa.mistura_boa.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.mistura_boa.mistura_boa.models.dtos.CancelarPedidoDTO;
import com.mistura_boa.mistura_boa.models.dtos.PedidoDTO;
import com.mistura_boa.mistura_boa.models.dtos.ProdutoCarrinhoDTO;
import com.mistura_boa.mistura_boa.models.dtos.ProdutoPedidoDTO;
import com.mistura_boa.mistura_boa.models.entities.Pedido;
import com.mistura_boa.mistura_boa.models.enums.StatusPedidoEnum;
import com.mistura_boa.mistura_boa.models.filters.PedidoByUsuarioPageable;
import com.mistura_boa.mistura_boa.models.filters.PedidoFilterPageable;
import com.mistura_boa.mistura_boa.models.grids.PageResponse;
import com.mistura_boa.mistura_boa.repositories.IPedidoRepository;
import com.mistura_boa.mistura_boa.repositories.impl.ImplPedidoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final IPedidoRepository pedidoRepository;
    private final ModelMapper modelMapper;
    private final CarrinhoService carrinhoService;
    private final ImplPedidoRepository implPedidoRepository;


    @Transactional
    public PedidoDTO save(Long idCarrinho, PedidoDTO dto) throws Exception {
        var carrinho = this.carrinhoService.getById(idCarrinho);

        var produtosPedido = mapProdCarrinhoToProdPedido(carrinho.getProdutosCarrinho());

        produtosPedido.forEach(produto -> {
            produto.setPedido(dto);
            produto.setId(null);
        });

        dto.setNumeroPedido( UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
        dto.setProdutosPedido(produtosPedido);
        dto.setDataPedido(LocalDateTime.now());
        dto.setStatusPedido(StatusPedidoEnum.AGUARDANDO_CONFIRMACAO);
        
        var pedido = this.pedidoRepository.save(modelMapper.map(dto, Pedido.class));

        this.carrinhoService.clearCarrinho(idCarrinho);
        
        dto.setId(pedido.getId());
        return dto;
    }

    private List<ProdutoPedidoDTO> mapProdCarrinhoToProdPedido(List<ProdutoCarrinhoDTO> produtosCarrinho) {
        List<ProdutoPedidoDTO> produtosPedido = new ArrayList<>();
        
        for(var prodCar: produtosCarrinho){
            var prodPed = modelMapper.map(prodCar, ProdutoPedidoDTO.class);
            if(prodCar.getTamanhoPreco()==null){
                prodPed.setValorMomentoCompra(prodCar.getProduto().getValor());
            }else{
                prodPed.setTamanhoMomentoCompra(prodCar.getTamanhoPreco().getTamanho());
                prodPed.setValorMomentoCompra(prodCar.getTamanhoPreco().getValor());
            }
            produtosPedido.add(prodPed);
        }

        return produtosPedido;
    }

    public PageResponse<PedidoDTO> getByIdUsuario(PedidoByUsuarioPageable pedidoByUsuarioPageable) throws Exception {
        var pedidosPage = this.pedidoRepository.findByIdUsuario(pedidoByUsuarioPageable.getIdUsuario(), PageRequest.of(pedidoByUsuarioPageable.getPage(), pedidoByUsuarioPageable.getSize()));
        if(!pedidosPage.hasContent()){
            throw new Exception("Você ainda não fez nenhum pedido!");
        }

        List<PedidoDTO> contentDto = pedidosPage.getContent().stream().map(pedido -> modelMapper.map(pedido, PedidoDTO.class)).toList();
        Page<PedidoDTO> dtoPage = new PageImpl<>(contentDto,pedidosPage.getPageable(),pedidosPage.getTotalElements());

        return new PageResponse<>(dtoPage);
    }

    public PedidoDTO getById(Long id) throws Exception {
        var pedido = this.pedidoRepository.findById(id).orElseThrow(() -> new Exception("Pedido não encontrado"));

        return modelMapper.map(pedido, PedidoDTO.class);
    }

    public List<PedidoDTO> getAll() {
        var pedidos = this.pedidoRepository.findAll();
        return pedidos.stream().map(pedido -> modelMapper.map(pedido, PedidoDTO.class)).toList();
    }

    public List<PedidoDTO> getAllTodayByStatus(StatusPedidoEnum status){
        LocalDate hoje = LocalDate.now();
        LocalDateTime startOfDay = hoje.atStartOfDay();
        LocalDateTime endOfDay = hoje.atTime(LocalTime.MAX);
        var pedidos  = this.pedidoRepository.findAllTodayByStatus(startOfDay, endOfDay, status);
        return pedidos.stream().map(pedido -> modelMapper.map(pedido, PedidoDTO.class)).toList();
    }

    public void cancelByClient(Long id) throws Exception{
        var pedido = this.pedidoRepository.findById(id).orElseThrow(() -> new Exception("Pedido não encontrado"));
        this.pedidoRepository.delete(pedido);
    }

    public void cancelByRestaurante(CancelarPedidoDTO cancelarDto) throws Exception{
        var pedido = this.pedidoRepository.findById(cancelarDto.getIdPedido()).orElseThrow(() -> new Exception("Pedido não encontrado"));

        pedido.setDataFechamentoPedido(LocalDateTime.now());
        pedido.setJustificativa(cancelarDto.getJustificativa());
        pedido.setStatusPedido(StatusPedidoEnum.CANCELADO);

        this.pedidoRepository.save(pedido);
    }


    public void changeStatusPedido(Long id, StatusPedidoEnum status) throws Exception{
        var pedido = this.getById(id);

        if(status == StatusPedidoEnum.FINALIZADO){
            pedido.setDataFechamentoPedido(LocalDateTime.now());
        }

        pedido.setStatusPedido(status);
        this.pedidoRepository.save(modelMapper.map(pedido, Pedido.class));
    }

    public PageResponse<PedidoDTO> search(PedidoFilterPageable filterPageable) throws Exception{
        var pedidosPage = this.implPedidoRepository.search(filterPageable.getFilter(),PageRequest.of(filterPageable.getPage(), filterPageable.getSize()));
        List<PedidoDTO> contentDto = pedidosPage.getContent().stream().map(pedido -> modelMapper.map(pedido, PedidoDTO.class)).toList();

        Page<PedidoDTO> dtoPage = new PageImpl<>(contentDto,pedidosPage.getPageable(),pedidosPage.getTotalElements());
        return new PageResponse<>(dtoPage);
    }


}
