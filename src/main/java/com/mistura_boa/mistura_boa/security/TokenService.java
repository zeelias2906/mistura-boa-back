package com.mistura_boa.mistura_boa.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mistura_boa.mistura_boa.models.entities.Usuario;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;


@Service
public class TokenService {

	@Value("${api.security.token.secret}")
	private String secret;


    	public String generateToken(Usuario usuario) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			String token = JWT.create().
					withIssuer("misturaboa")
					.withSubject(usuario.getEmail())
					.withClaim("name", usuario.getPessoa().getNome())
					.withClaim("id", usuario.getId())
					.withClaim("role", usuario.getRoleUsuario().name())
					.withExpiresAt(getExpirationDate()).sign(algorithm);

			return token;

		} catch (JWTCreationException e) {
			throw new RuntimeException("Erro ao gerar token! " + e.getMessage());
		}
	}

	public String validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm).withIssuer("misturaboa").build().verify(token).getSubject();

		} catch (JWTVerificationException e) {
			throw new RuntimeException("Usuário não autorizado" + e.getMessage());
		}
	}

	private Instant getExpirationDate() {
		return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
	}


}
