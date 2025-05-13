package com.mistura_boa.mistura_boa.services;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mistura_boa.mistura_boa.models.dtos.ResetSenhaDTO;
import com.mistura_boa.mistura_boa.models.dtos.UsuarioDTO;
import com.mistura_boa.mistura_boa.models.dtos.UsuarioInsertDTO;
import com.mistura_boa.mistura_boa.models.entities.Pessoa;
import com.mistura_boa.mistura_boa.models.entities.Usuario;
import com.mistura_boa.mistura_boa.models.enums.RoleUsuarioEnum;
import com.mistura_boa.mistura_boa.repositories.IPessoaRepository;
import com.mistura_boa.mistura_boa.repositories.IUsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final PessoaService pessoaService;
    private final IUsuarioRepository usuarioRepository;
	private final IPessoaRepository pessoaRepository;
	private final UsuarioService usuarioService;
    private final ModelMapper modelMapper;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return usuarioRepository.findByEmail(email);
	}

	public void resetSenha(ResetSenhaDTO dto) throws Exception {
		var pessoa = pessoaRepository.findByCpf(dto.getCpf());

		if (pessoa == null || pessoa.getUsuario() == null) {
			throw new Exception("Não foi encontrado usuário com essas informações!");
		}
		

		var usuario = pessoa.getUsuario();

		if (!pessoa.getNome().equals(dto.getNome()) || !usuario.getEmail().equals(dto.getEmail())) {
			throw new Exception("Não foi encontrado usuário com essas informações!");
		}

		if (usuarioService.validateSenha(dto.getNovaSenha())) {
			throw new Exception(
					"Senha inválida!\nA nova senha deve conter pelo menos: 8 caracteres, uma letra maiscula, uma letra miniscula e um número");
		}

		String senhaCriptografada = new BCryptPasswordEncoder().encode(dto.getNovaSenha());
		usuario.setSenha(senhaCriptografada);

		usuarioRepository.save(usuario);

	}

    public void novoUsuario(UsuarioInsertDTO usuarioDTO) throws Exception {
        if (usuarioService.isEmailUnique(usuarioDTO.getEmail(), null)){
            throw new Exception("E-mail já em uso");
        }
		
		if (pessoaService.isCpfUnique(usuarioDTO.getPessoa().getCpf(), null)) {
			throw new Exception("Cpf já em uso");
		}

		if (usuarioService.validateSenha(usuarioDTO.getSenha())) {
			throw new Exception(
					"Senha inválida!\nA senha deve conter pelo menos: 8 caracteres, uma letra maiscula, uma letra miniscula e um número");
		}

		String senhaCriptografada = new BCryptPasswordEncoder().encode(usuarioDTO.getSenha());
		usuarioDTO.setSenha(senhaCriptografada);
		usuarioDTO.setRoleUsuario(RoleUsuarioEnum.CLIENTE);
		
		var pessoa = pessoaRepository.save(modelMapper.map(usuarioDTO.getPessoa(), Pessoa.class));
        var usuario = modelMapper.map(usuarioDTO, Usuario.class);
        usuario.setPessoa(pessoa);
		usuarioRepository.save(usuario);

		
    }

}
