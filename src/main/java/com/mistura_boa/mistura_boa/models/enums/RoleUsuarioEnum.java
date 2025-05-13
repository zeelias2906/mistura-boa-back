package com.mistura_boa.mistura_boa.models.enums;

import lombok.Getter;

@Getter
public enum RoleUsuarioEnum {
	ADMIN("Admin"),
    GERENTE("Gerente"),
	CLIENTE("Cliente");

	private String role;

	RoleUsuarioEnum(String role) {
		this.role = role;
	}
}
