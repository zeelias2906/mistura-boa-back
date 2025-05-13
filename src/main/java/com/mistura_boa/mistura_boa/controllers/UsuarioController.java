package com.mistura_boa.mistura_boa.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mistura_boa.mistura_boa.models.dtos.UsuarioInsertDTO;
import com.mistura_boa.mistura_boa.models.enums.RoleUsuarioEnum;
import com.mistura_boa.mistura_boa.models.enums.StatusPedidoEnum;
import com.mistura_boa.mistura_boa.models.filters.FilterSimple;
import com.mistura_boa.mistura_boa.services.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("usuario")
@RequiredArgsConstructor
public class UsuarioController {
    
    private final UsuarioService usuarioService;


    @PostMapping("/")
    @PreAuthorize("hasRole('CLIENTE')")
	public ResponseEntity<?> update(@RequestBody @Validated UsuarioInsertDTO dto) throws Exception {
		try {
            return ResponseEntity.ok(usuarioService.update(dto));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}

    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CLIENTE')")
	public ResponseEntity<?> findById(@PathVariable Long id) throws Exception {
		try {
            return ResponseEntity.ok(usuarioService.findById(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}

	@PostMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> search(@RequestBody @Validated FilterSimple filter) throws Exception {
		try {
            return ResponseEntity.ok(usuarioService.search(filter));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}

	@GetMapping("/change-tipo-usuario/{id}/{tipoUsuario}")
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> changeStatusPedido(@PathVariable Long id, @PathVariable RoleUsuarioEnum tipoUsuario) throws Exception {
		try {
			this.usuarioService.changeTipoUsuario(id, tipoUsuario);
            return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}

}
