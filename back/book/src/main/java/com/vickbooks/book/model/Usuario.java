package com.vickbooks.book.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor 
@NoArgsConstructor 
@Getter 
@Setter 
@ToString 
@Entity 

public class Usuario implements UserDetails {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id 
    private Long id;

    @Column (unique = true)
    private String login;
    private String senha;

    @Override 
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override 
    public String getUsername() {
        return login;
    }

    @Override 
    public String getPassword() {
        return senha;
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
