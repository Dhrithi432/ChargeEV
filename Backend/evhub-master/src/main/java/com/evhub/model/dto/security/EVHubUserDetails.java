package com.evhub.model.dto.security;

import com.evhub.model.dto.user.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class EVHubUserDetails implements UserDetails {

    private final UserDTO user;

    public EVHubUserDetails(UserDTO userDTO) {
        this.user = userDTO;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public Integer getUserID() {
        return this.user.getUserID();
    }

    public String getUserName() {
        return this.user.getUserName();
    }
}
