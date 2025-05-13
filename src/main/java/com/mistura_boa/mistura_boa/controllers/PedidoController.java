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

import com.mistura_boa.mistura_boa.models.dtos.CancelarPedidoDTO;
import com.mistura_boa.mistura_boa.models.dtos.PedidoDTO;
import com.mistura_boa.mistura_boa.models.enums.StatusPedidoEnum;
import com.mistura_boa.mistura_boa.models.filters.PedidoFilter;
import com.mistura_boa.mistura_boa.services.PedidoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("pedido")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping("/save/{idCarrinho}")
    @PreAuthorize("hasRole('CLIENTE')")
	public ResponseEntity<?> save(@PathVariable Long idCarrinho, @RequestBody @Validated PedidoDTO dto) throws Exception {
		try {
            return ResponseEntity.ok(pedidoService.save(idCarrinho, dto));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}

    @GetMapping("/by-usuario/{idUsuario}")
    @PreAuthorize("hasRole('CLIENTE')")
	public ResponseEntity<?> getByIdUsuario(@PathVariable Long idUsuario) throws Exception {
		try {
            return ResponseEntity.ok(pedidoService.getByIdUsuario(idUsuario));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CLIENTE')")
	public ResponseEntity<?> getById(@PathVariable Long id) throws Exception {
		try {
            return ResponseEntity.ok(pedidoService.getById(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}

    @GetMapping("/")
    @PreAuthorize("hasRole('GERENTE')")
	public ResponseEntity<?> getAll() throws Exception {
		try {
            return ResponseEntity.ok(pedidoService.getAll());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}

	@GetMapping("/today/{status}")
    @PreAuthorize("hasRole('GERENTE')")
	public ResponseEntity<?> getAllTodayByStatus(@PathVariable StatusPedidoEnum status) throws Exception {
		try {
            return ResponseEntity.ok(pedidoService.getAllTodayByStatus(status));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}

	@GetMapping("/cancel-by-client/{id}")
    @PreAuthorize("hasRole('CLIENTE')")
	public ResponseEntity<?> cancelbyClient(@PathVariable Long id) throws Exception {
		try {
			this.pedidoService.cancelByClient(id);
            return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}

	@GetMapping("/change-status/{id}/{status}")
    @PreAuthorize("hasRole('GERENTE')")
	public ResponseEntity<?> changeStatusPedido(@PathVariable Long id, @PathVariable StatusPedidoEnum status) throws Exception {
		try {
			this.pedidoService.changeStatusPedido(id, status);
            return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}


	@PostMapping("/cancel-by-restaurante")
    @PreAuthorize("hasRole('GERENTE')")
	public ResponseEntity<?> cancelByRestaurante(@RequestBody @Validated CancelarPedidoDTO dto) throws Exception {
		try {
			this.pedidoService.cancelByRestaurante(dto);
            return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}

	@PostMapping("/search")
    @PreAuthorize("hasRole('GERENTE')")
	public ResponseEntity<?> search(@RequestBody @Validated PedidoFilter filter) throws Exception {
		try {
            return ResponseEntity.ok(this.pedidoService.search(filter));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}

}
