package com.mistura_boa.mistura_boa.models.entities;


import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.mistura_boa.mistura_boa.models.enums.RoleUsuarioEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "USUARIO")
public class Usuario implements UserDetails {

    private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_USUARIO")
	private Long id;

	@Column(name = "EMAIL", unique = true)
	private String email;

	@Column(name = "SENHA")
	private String senha;

	@Column(name = "ROLE_USUARIO")
	@Enumerated(EnumType.STRING)
	private RoleUsuarioEnum roleUsuario;

	@Column(name = "DT_EXCLUSAO")
	private LocalDateTime dataExclusao;

    @OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_pessoa", referencedColumnName = "id_pessoa")
	private Pessoa pessoa;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.roleUsuario == RoleUsuarioEnum.ADMIN) {
			return List.of(new SimpleGrantedAuthority("ROLE_" + RoleUsuarioEnum.ADMIN.name()),
					new SimpleGrantedAuthority("ROLE_" + RoleUsuarioEnum.GERENTE.name()),
					new SimpleGrantedAuthority("ROLE_" + RoleUsuarioEnum.CLIENTE.name()));
		} else if (this.roleUsuario == RoleUsuarioEnum.GERENTE) {
			return List.of(new SimpleGrantedAuthority("ROLE_" + RoleUsuarioEnum.GERENTE.name()),
					new SimpleGrantedAuthority("ROLE_" + RoleUsuarioEnum.CLIENTE.name()));
		} else {
			return List.of(new SimpleGrantedAuthority("ROLE_" + RoleUsuarioEnum.CLIENTE.name()));
		}
    }

    @Override
    public String getPassword() {
       return senha;
    }

    @Override
    public String getUsername() {
       return email;
    }

}
