package com.iqy.im.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

public class BaseController {

    protected String getUserId() {
        return (String) getAuthentication().getPrincipal();
    }

    protected List<String> getRoles() {
        return getAuthentication().getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .filter(s -> s.startsWith("ROLE_"))
                .collect(Collectors.toList());
    }

    protected List<String> getPermissions() {
        return getAuthentication().getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .filter(s -> !s.startsWith("ROLE_"))
                .collect(Collectors.toList());
    }

    protected Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
