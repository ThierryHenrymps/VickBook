package com.vickbooks.book.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vickbooks.book.model.Usuario;
import java.util.List;


public interface UserRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByLogin(String login);
    
}
