package com.mistura_boa.mistura_boa.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.mistura_boa.mistura_boa.models.dtos.ProdutoCarrinhoDTO;
import com.mistura_boa.mistura_boa.models.entities.ProdutoCarrinho;
import com.mistura_boa.mistura_boa.repositories.IProdutoCarrinhoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdutoCarrinhoService {

    private final IProdutoCarrinhoRepository produtoCarrinhoRepository;
    private final ModelMapper modelMapper;

    public ProdutoCarrinhoDTO save(ProdutoCarrinhoDTO dto){
        var produtoCarrinho = this.produtoCarrinhoRepository.save(modelMapper.map(dto, ProdutoCarrinho.class));
        return modelMapper.map(produtoCarrinho, ProdutoCarrinhoDTO.class);
    }

    @Transactional
    public ProdutoCarrinhoDTO getById(Long id) throws Exception {

        var prodCarrinho = this.produtoCarrinhoRepository.findByIdProdutoCarrinho(id);

        if (prodCarrinho == null) {
            throw new Exception("Produto não está no carrinho");
        }

        return modelMapper.map(prodCarrinho, ProdutoCarrinhoDTO.class);
    }

    public void delete(Long id){        
        this.produtoCarrinhoRepository.deleteById(id);
    }

}
