package com.mistura_boa.mistura_boa.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.mistura_boa.mistura_boa.models.dtos.CancelarPedidoDTO;
import com.mistura_boa.mistura_boa.models.dtos.PedidoDTO;
import com.mistura_boa.mistura_boa.models.dtos.ProdutoPedidoDTO;
import com.mistura_boa.mistura_boa.models.entities.Pedido;
import com.mistura_boa.mistura_boa.models.enums.StatusPedidoEnum;
import com.mistura_boa.mistura_boa.models.filters.PedidoFilter;
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
        var produtosPedido = carrinho.getProdutosCarrinho().stream().map(produtoCarrinho -> modelMapper.map(produtoCarrinho, ProdutoPedidoDTO.class)).toList();
        produtosPedido.stream().forEach(produto -> produto.setPedido(dto));
        produtosPedido.stream().forEach(produto -> produto.setId(null));

        dto.setNumeroPedido( UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
        dto.setProdutosPedido(produtosPedido);
        dto.setDataPedido(LocalDateTime.now());
        dto.setStatusPedido(StatusPedidoEnum.AGUARDANDO_CONFIRMACAO);
        
        var pedido = this.pedidoRepository.save(modelMapper.map(dto, Pedido.class));

        this.carrinhoService.clearCarrinho(idCarrinho);
        
        dto.setId(pedido.getId());
        return dto;
    }


    public List<PedidoDTO> getByIdUsuario(Long idUsuario) throws Exception {
        var pedidos = this.pedidoRepository.findByIdUsuario(idUsuario);
        if(pedidos.isEmpty()){
            throw new Exception("Você ainda não fez nenhum pedido!");
        }

        return pedidos.stream().map(pedido -> modelMapper.map(pedido, PedidoDTO.class)).toList();
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

    public List<PedidoDTO> search(PedidoFilter filter){
        var pedidos = this.implPedidoRepository.search(filter);
        return pedidos.stream().map(pedido -> modelMapper.map(pedido, PedidoDTO.class)).toList();
    }


}
