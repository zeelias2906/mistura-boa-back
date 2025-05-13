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

import com.mistura_boa.mistura_boa.models.dtos.CategoriaDTO;
import com.mistura_boa.mistura_boa.models.filters.FilterSimple;
import com.mistura_boa.mistura_boa.services.CategoriaService;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("categoria")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping("/")
    @PreAuthorize("hasRole('GERENTE')")
	public ResponseEntity<?> save(@RequestBody @Validated CategoriaDTO dto) throws Exception {
		try {
            return ResponseEntity.ok(categoriaService.save(dto));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}

	@GetMapping("/{id}")
    @PreAuthorize("hasRole('GERENTE')")
	public ResponseEntity<?> getById(@PathVariable Long id) throws Exception {
		try {
            return ResponseEntity.ok(categoriaService.getById(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}

	@PostMapping("search")
    @PreAuthorize("hasRole('GERENTE')")
	public ResponseEntity<?> search(@RequestBody FilterSimple filter) throws Exception {
		try {
            return ResponseEntity.ok(categoriaService.search(filter));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}

	@GetMapping("/")
    @PreAuthorize("hasRole('GERENTE')")
	public ResponseEntity<?> getAll() throws Exception {
		try {
            return ResponseEntity.ok(categoriaService.getAll());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}

	@GetMapping("desativar-ativar/{id}")
    @PreAuthorize("hasRole('GERENTE')")
	public ResponseEntity<?> desativarAtivarCategoria(@PathVariable Long id) throws Exception {
		try {
            return ResponseEntity.ok(categoriaService.desativarAtivarCategoria(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}


}