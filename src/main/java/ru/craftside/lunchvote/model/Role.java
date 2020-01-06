package ru.craftside.lunchvote.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created at 04.01.2020
 *
 * @author Pavel Tolstenkov
 */
public enum Role implements GrantedAuthority {
    ROLE_USER,
    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
