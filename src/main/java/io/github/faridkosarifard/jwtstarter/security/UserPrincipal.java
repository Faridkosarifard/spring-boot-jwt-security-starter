package io.github.faridkosarifard.jwtstarter.security;

import io.github.faridkosarifard.jwtstarter.domain.UserAccount;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class UserPrincipal implements UserDetails {

    private final UserAccount user;
    private final Set<GrantedAuthority> authorities;

    public UserPrincipal(UserAccount user) {
        this.user = user;
        Set<GrantedAuthority> granted = new LinkedHashSet<>();
        user.roleNames().forEach(role -> granted.add(new SimpleGrantedAuthority(role)));
        user.permissionNames().forEach(permission -> granted.add(new SimpleGrantedAuthority(permission)));
        this.authorities = Set.copyOf(granted);
    }

    public UserAccount getUser() {
        return user;
    }

    public Long getId() {
        return user.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.isLockedNow();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
