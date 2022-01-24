package com.io.ReadingIsGood.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.io.ReadingIsGood.db.entity.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;


public class UserPrinciple implements UserDetails {
    private static final long serialVersionUID = 1L;

    private UUID id;

    private String username;

    private String email;

    private String token;

    @JsonIgnore
    private String password;

    public UserPrinciple(UUID id,
                         String username, String email, String token, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.token = token;
        this.password = password;
    }

    public static UserPrinciple build(Customer user) {

        return new UserPrinciple(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getHashedPassword()
        );
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPrinciple user = (UserPrinciple) o;
        return Objects.equals(id, user.id);
    }
}