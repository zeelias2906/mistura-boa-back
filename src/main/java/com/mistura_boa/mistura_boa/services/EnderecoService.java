package com.mistura_boa.mistura_boa.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.mistura_boa.mistura_boa.models.dtos.EnderecoDTO;
import com.mistura_boa.mistura_boa.models.entities.Endereco;
import com.mistura_boa.mistura_boa.repositories.IEnderecoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final IEnderecoRepository enderecoRepository;
    private final ModelMapper modelMapper;

    public EnderecoDTO save(EnderecoDTO dto){
        var endereco = this.enderecoRepository.save(modelMapper.map(dto, Endereco.class));
        dto.setId(endereco.getId());
        return dto;
    }

    public List<EnderecoDTO> findAllByUsuario(Long idUsuario){
        var enderecos = this.enderecoRepository.findAllByIdUsuario(idUsuario);
        if(enderecos.isEmpty()){
            return new ArrayList<>();
        }

        return enderecos.stream().map(endereco -> modelMapper.map(endereco, EnderecoDTO.class)).toList();
    }

    public EnderecoDTO findById(Long IdEndereco) throws Exception{
        var endereco = this.enderecoRepository.findById(IdEndereco).orElseThrow(() -> new Exception("Endereco não encontrado"));

        return modelMapper.map(endereco, EnderecoDTO.class);
    }


    public void delete(Long IdEndereco) throws Exception{
        var endereco = this.enderecoRepository.findById(IdEndereco).orElseThrow(() -> new Exception("Endereco não encontrado"));

        endereco.setDataExclusao(LocalDateTime.now());
        this.enderecoRepository.save(endereco);
    }


}
