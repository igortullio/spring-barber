package com.igortullio.barber.adapter.database.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "permission_group")
public class PermissionGroupEntity extends AbstractEntity implements GrantedAuthority {

    public static final String ADMIN = "GROUP_ADMIN";
    public static final String USER = "GROUP_USER";

    @Column(nullable = false, unique = true)
    private String name;

    @Override
    public String getAuthority() {
        return this.getName();
    }

}
