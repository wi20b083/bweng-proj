package com.example.bwengproj.model.status;

import com.example.bwengproj.model.User;
import org.springframework.security.core.GrantedAuthority;

/**
 * Possible roles of a {@link User}
 */
public enum Role implements GrantedAuthority {
    ROLE_USER,
    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
