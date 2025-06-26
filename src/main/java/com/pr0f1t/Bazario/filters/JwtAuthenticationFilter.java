package com.pr0f1t.Bazario.filters;

import com.pr0f1t.Bazario.security.EndpointSecurityValidator;
import com.pr0f1t.Bazario.security.jwt.JwtService;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.nio.charset.StandardCharsets;

@Component
public class JwtAuthenticationFilter implements GatewayFilter {

    private final JwtService jwtService;
    private final EndpointSecurityValidator securityValidator;

    public JwtAuthenticationFilter(JwtService jwtService, EndpointSecurityValidator securityValidator) {
        this.jwtService = jwtService;
        this.securityValidator = securityValidator;
    }
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();
        HttpMethod method = exchange.getRequest().getMethod();

        if (securityValidator.isPublic(path, method)) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange, "Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);

        if (!jwtService.validateToken(token)) {
            return unauthorized(exchange, "Invalid or expired token");
        }

        String userRole = jwtService.extractUserRole(token);

        if (!securityValidator.hasAccess(path, method, userRole)) {
            return unauthorized(exchange, "Access denied");
        }

        return chain.filter(exchange);
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
        exchange.getResponse().getHeaders().add("Content-Type", "text/plain; charset=UTF-8");
        return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(bytes)));
    }
}
