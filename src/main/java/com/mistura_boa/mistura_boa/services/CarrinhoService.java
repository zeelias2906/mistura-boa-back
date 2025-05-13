package com.mistura_boa.mistura_boa.services;


import java.math.BigDecimal;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.mistura_boa.mistura_boa.models.dtos.CarrinhoDTO;
import com.mistura_boa.mistura_boa.models.entities.Carrinho;
import com.mistura_boa.mistura_boa.models.entities.ProdutoCarrinho;
import com.mistura_boa.mistura_boa.repositories.ICarrinhoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarrinhoService {

    private final ICarrinhoRepository carrinhoRepository;
    private final ProdutoCarrinhoService produtoCarrinhoService;
    private final ModelMapper modelMapper;

    public CarrinhoDTO save(CarrinhoDTO dto) {
        var carrinho = this.carrinhoRepository.findByIdUsuario(dto.getUsuario().getId());
        if(carrinho == null){
            dto.setValorTotal(BigDecimal.valueOf(dto.getProdutosCarrinho().stream().map(produto -> produto.getProduto().getValor()).toList().get(0)));
            dto.getProdutosCarrinho().get(0).setCarrinho(dto);
            carrinho = carrinhoRepository.save(modelMapper.map(dto, Carrinho.class));
        }else{
            var prodCar = modelMapper.map(dto.getProdutosCarrinho().get(0), ProdutoCarrinho.class);
            prodCar.setCarrinho(carrinho);
            // this.produtoCarrinhoService.save(modelMapper.map(prodCar, ProdutoCarrinhoDTO.class));
            carrinho.getProdutosCarrinho().add(prodCar);
            carrinho.setValorTotal(carrinho.getValorTotal().add(BigDecimal.valueOf(prodCar.getProduto().getValor())));
            carrinhoRepository.save(carrinho);
        }
        
        return modelMapper.map(carrinho, CarrinhoDTO.class);
    }

    public CarrinhoDTO getByIdUsuario(Long idUsuario) throws Exception {
        var carrinho = this.carrinhoRepository.findByIdUsuario(idUsuario);
        if(carrinho == null){
            throw new Exception("Carrinho vazio, adicione produtos ao carinho!");
        }

        return modelMapper.map(carrinho, CarrinhoDTO.class);
    }

    public CarrinhoDTO getById(Long id) throws Exception {
        var carrinho = this.carrinhoRepository.findById(id).orElseThrow(() -> new Exception("Carrinho não encontrado"));

        return modelMapper.map(carrinho, CarrinhoDTO.class);
    }

    public void delete(Long idCarrinho, Long idProdutoCarrinho) throws Exception {
        var carrinho = this.carrinhoRepository.findById(idCarrinho).orElseThrow(() -> new Exception("Carrinho não encontrado"));
        var prodCar = this.produtoCarrinhoService.getById(idProdutoCarrinho);

        carrinho.setValorTotal(carrinho.getValorTotal().subtract(BigDecimal.valueOf(prodCar.getProduto().getValor())));
        this.carrinhoRepository.save(carrinho);
        this.produtoCarrinhoService.delete(idProdutoCarrinho);
        
    }

    public void clearCarrinho(Long idCarrinho) {
        this.carrinhoRepository.deleteById(idCarrinho);
    }

}
