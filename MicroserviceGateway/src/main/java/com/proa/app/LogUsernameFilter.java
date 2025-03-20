package com.proa.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Component
public class LogUsernameFilter implements WebFilter {
	private static final Logger LOGGER = LoggerFactory.getLogger(LogUsernameFilter.class);

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		return ReactiveSecurityContextHolder.getContext().flatMap(context -> {
			Authentication authentication = context.getAuthentication();
			if (authentication != null && authentication.isAuthenticated()) {
				if (authentication.getPrincipal() instanceof Jwt) {
					String username = ((Jwt) authentication.getPrincipal()).getClaimAsString("preferred_username");

					LOGGER.info("USER AUTH {}" + username);
				} else {
					LOGGER.warn("JWT no contiene el campo preferred_username");
				}
			}
			return chain.filter(exchange);
		}).switchIfEmpty(chain.filter(exchange));
	}

}
