package com.guidopierri.pantrybe.permissions;


public enum Permissions {
    ADMIN_READ("admin:read"),
    ADMIN_WRITE("admin:write"),
    ADMIN_DELETE("admin:delete"),
    USER_READ("user:read"),
    USER_WRITE("user:write");

    public final String permission;
    Permissions(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
