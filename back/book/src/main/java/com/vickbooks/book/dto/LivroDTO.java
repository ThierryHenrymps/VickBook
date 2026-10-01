package com.vickbooks.book.dto;

import org.modelmapper.ModelMapper;

import com.vickbooks.book.model.Livro;

import lombok.Data;

import lombok.Data;

@Data 
public class LivroDTO {
    
    private Long id;
    private String titulo;
    private String autor;
    private String categoria;
    private String descricao;
    private String capa;
    private String arquivo;
    private String fonte;

    public static LivroDTO create (Livro l){
        ModelMapper modelmapper = new ModelMapper();
        return modelmapper.map(l, LivroDTO.class);
    }

}
