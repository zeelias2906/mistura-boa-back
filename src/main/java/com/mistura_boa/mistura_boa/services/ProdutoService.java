package com.mistura_boa.mistura_boa.services;


import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.mistura_boa.mistura_boa.models.dtos.ProdutoDTO;
import com.mistura_boa.mistura_boa.models.entities.Produto;
import com.mistura_boa.mistura_boa.models.filters.FilterSimple;
import com.mistura_boa.mistura_boa.models.grids.ProdutoCategoriaGrid;
import com.mistura_boa.mistura_boa.repositories.IProdutoRepository;
import com.mistura_boa.mistura_boa.repositories.impl.ImplProdutoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ImplProdutoRepository implProdutoRepository;
    private final IProdutoRepository produtoRepository;
    private final ModelMapper modelMapper;

    public ProdutoDTO save(ProdutoDTO dto) throws Exception{
        if(dto.getId() != null){
            this.produtoRepository.findById(dto.getId()).orElseThrow(() -> new Exception("Produto não encontrado"));
        }


        if(this.produtoRepository.existsProdutoByNome(dto.getNome(), dto.getId())){
            throw new Exception(" Já Existe um produto com esse nome");
        }
        var produto = this.produtoRepository.save(modelMapper.map(dto, Produto.class));
        dto.setId(produto.getId());
        return dto;
    }

    public ProdutoDTO getById(Long id) throws Exception{
        var produto = this.produtoRepository.findById(id).orElseThrow(() -> new Exception("Produto não encontrado"));
        return modelMapper.map(produto, ProdutoDTO.class);
    }

    public List<ProdutoDTO> getAll() throws Exception{
        var produtos = this.produtoRepository.findAll();
        return produtos.stream().map(produto -> modelMapper.map(produto, ProdutoDTO.class)).toList();
    }

    public ProdutoDTO desativarAtivarProduto(Long id) throws Exception{
        var produto = this.produtoRepository.findById(id).orElseThrow(() -> new Exception("Produto não encontrado"));

        if(produto.getDataExclusao() == null){
            produto.setDataExclusao(LocalDateTime.now());
        }else{
            produto.setDataExclusao(null);
        }

        this.produtoRepository.save(produto);
        
        return modelMapper.map(produto, ProdutoDTO.class);
    }

    public List<ProdutoDTO> getAllByIdCategoria(Long idCategoria) throws Exception{
        var produtos = this.produtoRepository.findAllByIdCategoria(idCategoria);

        return produtos.stream().map(produto -> modelMapper.map(produto, ProdutoDTO.class)).toList();
    }

    public List<ProdutoDTO> search(FilterSimple filter) throws Exception{
        if(filter==null){
            throw new Exception("Filtro inválido");
        }
        var produtos = this.implProdutoRepository.search(filter);
        return produtos.stream().map(produto -> modelMapper.map(produto, ProdutoDTO.class)).toList();
    }

    public List<ProdutoDTO> searchActive(FilterSimple filter) throws Exception{
        if(filter==null){
            throw new Exception("Filtro inválido");
        }
        var produtos = this.implProdutoRepository.searchActive(filter);
        return produtos.stream().map(produto -> modelMapper.map(produto, ProdutoDTO.class)).toList();
    }


    public List<ProdutoCategoriaGrid> searchGridProdCat(FilterSimple filter) throws Exception{
        if(filter==null){
            throw new Exception("Filtro inválido");
        }

        return  this.implProdutoRepository.searchGridProdCat(filter);
    }

    

}
