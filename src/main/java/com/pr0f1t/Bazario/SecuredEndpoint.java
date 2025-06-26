package com.pr0f1t.Bazario;

import org.springframework.http.HttpMethod;

import java.util.List;

public class SecuredEndpoint {
    private String path;
    private HttpMethod method;
    private List<String> roles = List.of();

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }


    public List<String> getRoles() {
        return roles;
    }
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}

