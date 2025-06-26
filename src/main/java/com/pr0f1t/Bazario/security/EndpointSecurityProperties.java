package com.pr0f1t.Bazario.security;

import com.pr0f1t.Bazario.SecuredEndpoint;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "security")
public class EndpointSecurityProperties {
    private List<SecuredEndpoint> endpoints;

    public List<SecuredEndpoint> getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(List<SecuredEndpoint> endpoints) {
        this.endpoints = endpoints;
    }
}
