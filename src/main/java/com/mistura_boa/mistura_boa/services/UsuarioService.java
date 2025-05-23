package com.mistura_boa.mistura_boa.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.mistura_boa.mistura_boa.models.dtos.UsuarioDTO;
import com.mistura_boa.mistura_boa.models.dtos.UsuarioInsertDTO;
import com.mistura_boa.mistura_boa.models.entities.Usuario;
import com.mistura_boa.mistura_boa.models.enums.RoleUsuarioEnum;
import com.mistura_boa.mistura_boa.models.filters.FilterSimplePageable;
import com.mistura_boa.mistura_boa.models.grids.PageResponse;
import com.mistura_boa.mistura_boa.repositories.IUsuarioRepository;
import com.mistura_boa.mistura_boa.repositories.impl.ImplUsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final IUsuarioRepository usuarioRepository;
	private final ImplUsuarioRepository implUsuarioRepository;
	private final PessoaService pessoaService;
	private final ModelMapper modelMapper;

	public UsuarioDTO update(UsuarioInsertDTO dto) throws Exception{
		var usuario = this.usuarioRepository.findById(dto.getId()).orElseThrow(() -> new Exception("Usuário não encontrado"));
		dto.setSenha(usuario.getSenha());
		dto.setRoleUsuario(usuario.getRoleUsuario());

		if(isEmailUnique(dto.getEmail(), dto.getId())){
			throw new Exception("E-mail já cadastrado no sistema");
		}

		pessoaService.update(dto.getPessoa());
	
		usuarioRepository.save(modelMapper.map(dto, Usuario.class));
		dto.setSenha(null);
		dto.setRoleUsuario(null);
        return modelMapper.map(dto, UsuarioDTO.class);
    }

    public UsuarioDTO findById(Long id) throws Exception {
        var usuario = this.usuarioRepository.findById(id).orElseThrow(() -> new Exception("Usuário não encontrado"));

        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    public boolean validateSenha(String senha) {
		String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
		return !senha.matches(regex);
	}

	public boolean isEmailUnique(String email, Long id){
		return  usuarioRepository.isEmailUnique(email, id);
	}

    public PageResponse<UsuarioDTO> search(FilterSimplePageable filterPageable) throws Exception {
	    if(filterPageable.getFilter()==null){
            throw new Exception("Filtro inválido");
        }

		var usuariosPage = this.implUsuarioRepository.search(filterPageable.getFilter(),PageRequest.of(filterPageable.getPage(), filterPageable.getSize()));
        List<UsuarioDTO> contentDto = usuariosPage.getContent().stream().map(usuario -> modelMapper.map(usuario, UsuarioDTO.class)).toList();

        Page<UsuarioDTO> dtoPage = new PageImpl<>(contentDto,usuariosPage.getPageable(),usuariosPage.getTotalElements());
        return new PageResponse<>(dtoPage);
    }

	public void changeTipoUsuario(Long id, RoleUsuarioEnum newTipoUsuario) throws Exception{
		var usuario = this.usuarioRepository.findById(id).orElseThrow(() -> new Exception("Usuário não encontrado"));

		usuario.setRoleUsuario(newTipoUsuario);
		this.usuarioRepository.save(usuario);
	}

}
