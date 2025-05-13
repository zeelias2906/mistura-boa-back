package com.mistura_boa.mistura_boa.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mistura_boa.mistura_boa.models.enums.PublicRouteEnum;
import com.mistura_boa.mistura_boa.repositories.IUsuarioRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class SecurityFilter  extends OncePerRequestFilter {

    private final TokenService tokenService;

	private final IUsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException{
		
		String requestURI = request.getRequestURI();
		String method = request.getMethod();
		
		if (PublicRouteEnum.isPublicRoute(requestURI, method)) {
			filterChain.doFilter(request, response);
			return;
		}

		var token = this.recoverToken(request);
		if (token != null) {
			var email = tokenService.validateToken(token);
			UserDetails usuario = usuarioRepository.findByEmail(email);

			var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

			SecurityContextHolder.getContext().setAuthentication(authentication);

		}

		filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
		var authHeader = request.getHeader("Authorization");
		if (authHeader == null)
			return null;

		return authHeader.replace("Bearer ", "");
	}

}
