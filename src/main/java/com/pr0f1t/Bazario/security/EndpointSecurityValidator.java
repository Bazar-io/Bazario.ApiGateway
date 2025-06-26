package com.pr0f1t.Bazario.security;

import com.pr0f1t.Bazario.SecuredEndpoint;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

@Component
public class EndpointSecurityValidator {

    private final List<SecuredEndpoint> endpoints;

    public EndpointSecurityValidator(EndpointSecurityProperties properties) {
        this.endpoints = properties.getEndpoints();
    }

    public boolean isPublic(String path, HttpMethod method) {
        return endpoints.stream()
                .filter(ep -> matches(ep, path, method))
                .anyMatch(ep -> ep.getRoles().isEmpty());
    }

    public boolean hasAccess(String path, HttpMethod method, String userRoles) {
        return endpoints.stream()
                .filter(ep -> matches(ep, path, method))
                .anyMatch(ep -> ep.getRoles().isEmpty() || ep.getRoles().contains(userRoles));
    }

    private boolean matches(SecuredEndpoint ep, String path, HttpMethod method) {
        return Pattern.compile("^" + ep.getPath() + "$").matcher(path).matches() && ep.getMethod() == method;
    }
}
