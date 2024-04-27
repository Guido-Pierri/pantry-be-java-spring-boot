package com.guidopierri.pantrybe.permissions;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

import static com.guidopierri.pantrybe.permissions.Permissions.*;

public enum Roles {
    ADMIN(List.of(ADMIN_READ, ADMIN_WRITE, ADMIN_DELETE)),
    USER(List.of(USER_READ, USER_WRITE));
    private final List<Permissions> permissions;

    Roles(List<Permissions> permissions) {
        this.permissions = permissions;
    }

    public List<Permissions> getPermissions() {
        return permissions;
    }

    public List<SimpleGrantedAuthority> getGrantedAuthorities() {
        List<SimpleGrantedAuthority> p = new ArrayList<>(getPermissions().stream().map(
                index -> new SimpleGrantedAuthority(index.getPermission())
        ).toList());
        p.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return p;

    }

}