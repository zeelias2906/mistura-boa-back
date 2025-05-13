package com.mistura_boa.mistura_boa.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.mistura_boa.mistura_boa.models.dtos.PessoaDTO;
import com.mistura_boa.mistura_boa.models.entities.Pessoa;
import com.mistura_boa.mistura_boa.repositories.IPessoaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final IPessoaRepository pessoaRepository;
    private final ModelMapper modelMapper;

    public boolean isCpfUnique(String cpf, Long id){
       return pessoaRepository.isCpfUnique(cpf, id);
    }

    public void update(PessoaDTO pessoa) throws Exception {
        if(isCpfUnique(pessoa.getCpf(), pessoa.getId())){
			throw new Exception("CPF já cadastrado no sistema");
		}

        pessoaRepository.save(modelMapper.map(pessoa, Pessoa.class));
    }

}
