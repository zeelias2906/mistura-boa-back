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

import com.mistura_boa.mistura_boa.models.dtos.ProdutoDTO;
import com.mistura_boa.mistura_boa.models.filters.FilterSimple;
import com.mistura_boa.mistura_boa.services.ProdutoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("produto")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping("/")
    @PreAuthorize("hasRole('GERENTE')")
	public ResponseEntity<?> save(@RequestBody @Validated ProdutoDTO dto) throws Exception {
		try {
            return ResponseEntity.ok(produtoService.save(dto));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") Long id) throws Exception {
		try {
            return ResponseEntity.ok(produtoService.getById(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}

	@GetMapping("/")
    @PreAuthorize("hasRole('GERENTE')")
	public ResponseEntity<?> getAll() throws Exception {
		try {
            return ResponseEntity.ok(produtoService.getAll());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}

	@GetMapping("/desativar-ativar/{id}")
    @PreAuthorize("hasRole('GERENTE')")
	public ResponseEntity<?> desativarAtivarProduto(@PathVariable("id") Long id) throws Exception {
		try {
            return ResponseEntity.ok(produtoService.desativarAtivarProduto(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}

	@GetMapping("/produto-by-categoria/{idCategoria}")
    @PreAuthorize("hasRole('GERENTE')")
	public ResponseEntity<?> getAllByIdCategoria(@PathVariable("idCategoria") Long idCategoria) throws Exception {
		try {
            return ResponseEntity.ok(produtoService.getAllByIdCategoria(idCategoria));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}

	@PostMapping("search")
    @PreAuthorize("hasRole('GERENTE')")
	public ResponseEntity<?> search(@RequestBody FilterSimple filter) throws Exception {
		try {
            return ResponseEntity.ok(produtoService.search(filter));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}

	@PostMapping("search-active")
	public ResponseEntity<?> searchActive(@RequestBody FilterSimple filter) throws Exception {
		try {
            return ResponseEntity.ok(produtoService.searchActive(filter));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}

	@PostMapping("search-grid")
	public ResponseEntity<?> searchGridProdCat(@RequestBody FilterSimple filter) throws Exception {
		try {
            return ResponseEntity.ok(produtoService.searchGridProdCat(filter));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}


}