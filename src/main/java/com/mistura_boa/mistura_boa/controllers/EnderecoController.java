package com.mistura_boa.mistura_boa.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mistura_boa.mistura_boa.models.dtos.EnderecoDTO;
import com.mistura_boa.mistura_boa.services.EnderecoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("endereco")
@RequiredArgsConstructor
public class EnderecoController {

    private final EnderecoService enderecoService;

    @PostMapping("/")
    @PreAuthorize("hasRole('CLIENTE')")
	public ResponseEntity<?> save(@RequestBody @Validated EnderecoDTO dto) throws Exception {
		try {
            return ResponseEntity.ok(enderecoService.save(dto));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}

    
    @GetMapping("/by-usuario/{idUsuario}")
    @PreAuthorize("hasRole('CLIENTE')")
	public ResponseEntity<?> findByUsuario(@PathVariable Long idUsuario) throws Exception {
		try {
            return ResponseEntity.ok(enderecoService.findAllByUsuario(idUsuario));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}

    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CLIENTE')")
	public ResponseEntity<?> findById(@PathVariable Long id) throws Exception {
		try {
            return ResponseEntity.ok(enderecoService.findById(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('CLIENTE')")
	public ResponseEntity<?> delete(@PathVariable Long id) throws Exception {
		try {
			enderecoService.delete(id);
            return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}

}
