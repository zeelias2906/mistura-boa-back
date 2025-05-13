package com.mistura_boa.mistura_boa.services;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.mistura_boa.mistura_boa.models.dtos.CategoriaDTO;
import com.mistura_boa.mistura_boa.models.entities.Categoria;
import com.mistura_boa.mistura_boa.models.filters.FilterSimple;
import com.mistura_boa.mistura_boa.repositories.ICategoriaRepository;
import com.mistura_boa.mistura_boa.repositories.impl.ImplCategoriaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final ICategoriaRepository categoriaRepository;
    private final ImplCategoriaRepository implCategoriaRepository;
    private final ProdutoService produtoService;
    private final ModelMapper modelMapper;

    public CategoriaDTO save(CategoriaDTO dto) throws Exception{
        if(dto.getId() != null){
            this.categoriaRepository.findById(dto.getId()).orElseThrow(() -> new Exception("Categoria não encontrada"));
        }

        if(this.categoriaRepository.existsCategoriaByNome(dto.getNome(), dto.getId())){
            throw new Exception(" Já Existe uma categoria com esse nome");
        }
        var categoria = this.categoriaRepository.save(modelMapper.map(dto, Categoria.class));
        dto.setId(categoria.getId());
        return dto;
    }

    public List<CategoriaDTO> search(FilterSimple filter) throws Exception{
        if(filter==null){
            throw new Exception("Filtro inválido");
        }
        var categorias = this.implCategoriaRepository.search(filter.getNome());
        return categorias.stream().map(categoria -> modelMapper.map(categoria, CategoriaDTO.class)).toList();
    }

    public CategoriaDTO getById(Long id) throws Exception{
        var categoria = this.categoriaRepository.findById(id).orElseThrow(() -> new Exception("Categoria não encontrada"));

        return modelMapper.map(categoria, CategoriaDTO.class);
    }

    public List<CategoriaDTO> getAll() throws Exception{
        var categorias = this.categoriaRepository.findAll();
        return categorias.stream().map(categoria -> modelMapper.map(categoria, CategoriaDTO.class)).toList();
    }

    public CategoriaDTO desativarAtivarCategoria(Long id) throws Exception{
        var categoria = this.categoriaRepository.findById(id).orElseThrow(() -> new Exception("Categoria não encontrada"));
        

        if(categoria.getDataExclusao() == null){
            validateCategoriaByProduto(categoria.getId());
            categoria.setDataExclusao(LocalDateTime.now());
        }else{
            categoria.setDataExclusao(null);
        }

        this.categoriaRepository.save(categoria);
        
        return modelMapper.map(categoria, CategoriaDTO.class);
    }

    private void validateCategoriaByProduto(Long id) throws Exception {
        var produtos = this.produtoService.getAllByIdCategoria(id);
        for(var produto: produtos){
            if(produto.getDataExclusao() == null){
                throw new Exception("Existe produtos ativos para essa categoria, para desativa-lá, desative antes seus respetivos produtos");
            }
        }
    }


}
