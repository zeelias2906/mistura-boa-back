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

import com.mistura_boa.mistura_boa.models.dtos.CarrinhoDTO;
import com.mistura_boa.mistura_boa.services.CarrinhoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("carrinho")
@RequiredArgsConstructor
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    @PostMapping("/")
    @PreAuthorize("hasRole('CLIENTE')")
	public ResponseEntity<?> save(@RequestBody @Validated CarrinhoDTO dto) throws Exception {
		try {
            return ResponseEntity.ok(carrinhoService.save(dto));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}

    @GetMapping("/by-usuario/{idUsuario}")
    @PreAuthorize("hasRole('CLIENTE')")
	public ResponseEntity<?> getByIdUsuario(@PathVariable Long idUsuario) throws Exception {
		try {
            return ResponseEntity.ok(carrinhoService.getByIdUsuario(idUsuario));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CLIENTE')")
	public ResponseEntity<?> getById(@PathVariable Long id) throws Exception {
		try {
            return ResponseEntity.ok(carrinhoService.getById(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}

	@DeleteMapping("remove-product/{idCarrinho}/{idProdutoCarrinho}")
    @PreAuthorize("hasRole('CLIENTE')")
	public ResponseEntity<?> deleteProdutoCarrinho(@PathVariable Long idCarrinho, @PathVariable Long idProdutoCarrinho ) throws Exception {
		try {
            carrinhoService.delete(idCarrinho, idProdutoCarrinho);
            return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}

}
