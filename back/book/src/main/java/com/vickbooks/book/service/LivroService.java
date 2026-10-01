package com.vickbooks.book.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vickbooks.book.model.Livro;
import com.vickbooks.book.repository.LivroRepository;

@Service 
public class LivroService {
    @Autowired 
    private LivroRepository  rep;

    public List<Livro> getLivros(){

        return rep.findAll();
    }

    public Livro getById(Long id){
        return rep.findById(id).orElse(null);
    }

    public List<Livro> buscarPorTitulo(String titulo){
        return rep.findByTituloContainingIgnoreCase(titulo);
    }

    public Livro salvar(Livro livro){
        return rep.save(livro);
    }

    public List<Livro> buscarPorCategoria(String categoria){
        return rep.findByCategoriaContainingIgnoreCase(categoria);
    }

    public List<Livro> buscarPorAutor(String autor){
        return rep.findByAutorContainingIgnoreCase(autor);
    }

    public Livro atualizar(Long id, Livro livro){
        Livro livroExistente = rep.findById(id).orElse(null);

        if (livroExistente == null) {
            return null;
            }

            livroExistente.setTitulo(livro.getTitulo());
            livroExistente.setAutor(livro.getAutor());
            livroExistente.setCategoria(livro.getCategoria());
            livroExistente.setDescricao(livro.getDescricao());
            livroExistente.setCapa(livro.getCapa());
            livroExistente.setArquivo(livro.getArquivo());
            livroExistente.setFonte(livro.getFonte());

            return rep.save(livroExistente);
        }

    public boolean excluir(Long id){
        if(!rep.existsById(id)) {
            return false;
        }
        rep.deleteById(id);
        return true;
    }
    
    public List<Livro> getFavoritos(){
        return rep.findByFavoritoTrue(); 
    }

}
