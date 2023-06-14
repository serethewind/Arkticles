package com.serethewind.Arkticles.securityConfig;

import com.serethewind.Arkticles.entity.UsersEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class CustomUserDetails implements UserDetails {

    private String name;
    private String password;
    private Set<GrantedAuthority> grantedAuthorities;

    CustomUserDetails(UsersEntity user){
        this.name = user.getUsername();
        this.password = user.getPassword();
//        this.grantedAuthorities
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
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
}
