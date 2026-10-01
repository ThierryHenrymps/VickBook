package com.vickbooks.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vickbooks.book.model.Livro;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByTituloContainingIgnoreCase(String titulo);
    List<Livro> findByAutorContainingIgnoreCase(String autor);
    List<Livro> findByCategoriaContainingIgnoreCase(String categoria);
    List<Livro> findByFavoritoTrue();

}
