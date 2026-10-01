package com.vickbooks.book.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.validation.constraints.NotBlank;

@AllArgsConstructor 
@NoArgsConstructor 
@ToString 
@Getter 
@Setter
@Entity
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank 
    private String titulo;
    @NotBlank 
    private String autor;
    @NotBlank 
    private String categoria;
    private String descricao;
    private String capa;
    private String arquivo;
    private String fonte;
    private boolean favorito;

    
}
