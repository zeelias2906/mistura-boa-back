package com.mistura_boa.mistura_boa.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mistura_boa.mistura_boa.models.dtos.LoginDTO;
import com.mistura_boa.mistura_boa.models.dtos.ResetSenhaDTO;
import com.mistura_boa.mistura_boa.models.dtos.UsuarioDTO;
import com.mistura_boa.mistura_boa.models.dtos.UsuarioInsertDTO;
import com.mistura_boa.mistura_boa.models.entities.Usuario;
import com.mistura_boa.mistura_boa.security.TokenService;
import com.mistura_boa.mistura_boa.services.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
	private final TokenService tokenService;
	private final AuthenticationService authenticationService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Validated LoginDTO dto) throws Exception {
		try {
			var auth = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha()));
			var usuario = (Usuario) auth.getPrincipal();
			var token = tokenService.generateToken(usuario);
			return ResponseEntity.status(HttpStatus.OK).body(Map.of("token", token));
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BadCredentialsException("Usuário ou senha invalidos!"));
		} catch (InternalAuthenticationServiceException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new InternalAuthenticationServiceException("Usuário ou senha invalidos!"));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}

	@PostMapping("/reset-password")
	public ResponseEntity<?> resetSenha(@RequestBody @Validated ResetSenhaDTO  dto) throws Exception {
		try {
			authenticationService.resetSenha(dto);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}

    @PostMapping("new-user")
    public ResponseEntity<?> novoUsuario(@RequestBody UsuarioInsertDTO usuarioDTO) throws Exception {
		try {
			authenticationService.novoUsuario(usuarioDTO);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}

    }
    

}
