package com.example.lab2.model;

import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public enum Role {
    USER(Stream.of(Permission.GET_SELF_NOTES,Permission.ADD_SELF_NOTE, Permission.EDIT_SELF_NOTE, Permission.GET_SELF_NOTE, Permission.DELETE_SELF_NOTE).collect(Collectors.toSet()));
    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
