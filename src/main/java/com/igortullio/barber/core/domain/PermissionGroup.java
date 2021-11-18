package com.igortullio.barber.core.domain;

import java.util.Set;

public class PermissionGroup extends AbstractDomain {

    public static final String ADMIN = "GROUP_ADMIN";
    public static final String USER = "GROUP_USER";
    public static final String BARBERSHOP_OWNER = "GROUP_BARBERSHOP_OWNER";

    private String name;
    private Set<Permission> permissionSet;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Permission> getPermissionSet() {
        return permissionSet;
    }

    public void setPermissionSet(Set<Permission> permissionSet) {
        this.permissionSet = permissionSet;
    }

}
