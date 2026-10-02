package com.vickbooks.book.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vickbooks.book.repository.UserRepository;

@Service 
public class AutenticacaoService implements UserDetailsService {

    @Autowired 
    private UserRepository rep;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return rep.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + login));
    }
    
}
