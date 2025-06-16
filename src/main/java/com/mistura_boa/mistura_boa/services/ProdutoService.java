package com.mistura_boa.mistura_boa.services;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.mistura_boa.mistura_boa.models.dtos.ProdutoDTO;
import com.mistura_boa.mistura_boa.models.entities.Produto;
import com.mistura_boa.mistura_boa.models.entities.TamanhoPreco;
import com.mistura_boa.mistura_boa.models.filters.FilterSimplePageable;
import com.mistura_boa.mistura_boa.models.grids.OptionsSelects;
import com.mistura_boa.mistura_boa.models.grids.PageResponse;
import com.mistura_boa.mistura_boa.models.grids.ProdutoCategoriaGrid;
import com.mistura_boa.mistura_boa.models.grids.ProdutoGrid;
import com.mistura_boa.mistura_boa.repositories.IProdutoRepository;
import com.mistura_boa.mistura_boa.repositories.impl.ImplProdutoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ImplProdutoRepository implProdutoRepository;
    private final IProdutoRepository produtoRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public ProdutoDTO save(ProdutoDTO dto) throws Exception{
        if(dto.getId() != null){
            this.produtoRepository.findById(dto.getId()).orElseThrow(() -> new Exception("Produto não encontrado"));
        }

        if(this.produtoRepository.existsProdutoByNome(dto.getNome(), dto.getId())){
            throw new Exception(" Já Existe um produto com esse nome");
        }

        if(dto.getOrdenacao()==null){
            Long qtdProdutos = produtoRepository.count();
            dto.setOrdenacao(qtdProdutos+1);
        }
        
        var produto = modelMapper.map(dto, Produto.class);
        produto.getTamanhoPrecos().stream().forEach((tamanho) -> tamanho.setProduto(produto));

        if(!produto.getIsTamanhoUnico()){
            produto.setValor(null);
            this.findMenorValor(produto);
        }else{
            produto.setMenorValor(null);
        }

        this.produtoRepository.save(produto);
        dto.setId(produto.getId());
        return dto;
    }

    public ProdutoDTO getById(Long id) throws Exception{
        var produto = this.produtoRepository.findById(id).orElseThrow(() -> new Exception("Produto não encontrado"));
        return modelMapper.map(produto, ProdutoDTO.class);
    }

    public ProdutoDTO getByIdActives(Long id) throws Exception{
        var produto = this.produtoRepository.findById(id).orElseThrow(() -> new Exception("Produto não encontrado"));
        produto.setTamanhoPrecos(produto.getTamanhoPrecos().stream().filter(tp -> tp.getDataExclusao() == null).toList());
        return modelMapper.map(produto, ProdutoDTO.class);
    }

    public List<ProdutoDTO> getAll() throws Exception{
        var produtos = this.produtoRepository.findAll();
        return produtos.stream().map(produto -> modelMapper.map(produto, ProdutoDTO.class)).toList();
    }

    public ProdutoDTO desativarAtivarProduto(Long id) throws Exception{
        var produto = this.produtoRepository.findById(id).orElseThrow(() -> new Exception("Produto não encontrado"));

        if(produto.getDataExclusao() == null){
            produto.setDataExclusao(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
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

    public PageResponse<ProdutoGrid> search(FilterSimplePageable filterPageable) throws Exception{
        if(filterPageable.getFilter()==null){
            throw new Exception("Filtro inválido");
        }
        var produtosPageable = this.implProdutoRepository.search(filterPageable.getFilter(), PageRequest.of(filterPageable.getPage(), filterPageable.getSize()));
        return new PageResponse<>(produtosPageable);
    }

    public PageResponse<ProdutoDTO> searchActive(FilterSimplePageable filterPageable) throws Exception{
        if(filterPageable.getFilter()==null){
            throw new Exception("Filtro inválido");
        }
        
        var produtosPage = this.implProdutoRepository.searchActive(filterPageable.getFilter(), PageRequest.of(filterPageable.getPage(), filterPageable.getSize()));
        List<ProdutoDTO> contentDto = produtosPage.getContent().stream().map(produto -> modelMapper.map(produto, ProdutoDTO.class)).toList();
        Page<ProdutoDTO> dtoPage = new PageImpl<>(contentDto,produtosPage.getPageable(),produtosPage.getTotalElements());

        return new PageResponse<>(dtoPage);

    }


    public PageResponse<ProdutoCategoriaGrid> searchGridProdCat(FilterSimplePageable filterPageable) throws Exception{
        if(filterPageable.getFilter()==null){
            throw new Exception("Filtro inválido");
        }

        return new PageResponse<>(this.implProdutoRepository.searchGridProdCat(filterPageable.getFilter(), PageRequest.of(filterPageable.getPage(), filterPageable.getSize())));
    }

    public void ordenarProdutos(Map<Long, Long> newList){
        var listProdutos = new ArrayList<Produto>();
        for (Map.Entry<Long, Long> entry : newList.entrySet()) {
            Produto produto = produtoRepository.findById(entry.getKey()).orElseThrow(() -> new RuntimeException("Produto não encontrado: " + entry.getKey()));

            produto.setOrdenacao(entry.getValue());
            listProdutos.add(produto);
        }

        if(!listProdutos.isEmpty()){
            produtoRepository.saveAll(listProdutos);
        }
            
    }

    public List<OptionsSelects> getOptionsSelectsByIdCategoria(Long idCategoria){
        return this.produtoRepository.getOptionsSelectsByIdCategoria(idCategoria);
    }

    private void findMenorValor(Produto produto){
        Optional<Float> menorValor = produto.getTamanhoPrecos().stream().filter(tp -> tp.getDataExclusao() == null ).map(TamanhoPreco::getValor).filter(Objects::nonNull).min(Comparator.naturalOrder());

        menorValor.ifPresent(produto::setMenorValor);
    
    }
    

}
