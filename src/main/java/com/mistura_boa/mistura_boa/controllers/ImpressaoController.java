package com.mistura_boa.mistura_boa.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mistura_boa.mistura_boa.services.ImpressaoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("impressao-pedido")
@RequiredArgsConstructor
public class ImpressaoController {
    
    private final ImpressaoService impressaoService;

    @GetMapping("/{idPedido}")
    @PreAuthorize("hasRole('GERENTE')")
	public ResponseEntity<?> print(@PathVariable Long idPedido) throws Exception {
		try {
            return ResponseEntity.ok(this.impressaoService.imprimirPedido(idPedido));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}

}
