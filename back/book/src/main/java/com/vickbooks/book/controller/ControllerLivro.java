package com.vickbooks.book.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vickbooks.book.dto.LivroDTO;
import com.vickbooks.book.model.Livro;
import com.vickbooks.book.service.LivroService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController 
@RequestMapping("/api/v1/livros")
public class ControllerLivro {

    @Autowired 
    private LivroService service;

    @GetMapping 
    public ResponseEntity<List<Livro>> get() {
        return ResponseEntity.ok(service.getLivros());

    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> getLivro(@PathVariable Long id){
        Livro livro = service.getById(id);
        
        if(livro == null){
            return ResponseEntity.notFound().build();
        }return ResponseEntity.ok(livro);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Livro>> buscarPorTitulo(@RequestParam String titulo){
        List<Livro> livro = service.buscarPorTitulo(titulo);
 
        if(livro == null){
            return ResponseEntity.notFound().build();
        }return ResponseEntity.ok(livro);
    }

    @GetMapping("/buscar/autor")
    public ResponseEntity<List<Livro>> buscarPorAutor(@RequestParam String autor){
        List<Livro> livro = service.buscarPorAutor(autor);

        if(livro == null){
            return ResponseEntity.notFound().build();
        }return ResponseEntity.ok(livro);
    }

    @GetMapping("/buscar/categoria")
    public ResponseEntity<List<Livro>> buscarPorCategoria(@RequestParam String categoria){
        List<Livro> livro = service.buscarPorCategoria(categoria);

        if(livro == null){
            return ResponseEntity.notFound().build();
        }return ResponseEntity.ok(livro);
    }
    
    @GetMapping("/favoritos")
    public ResponseEntity<List<Livro>> getFavoritos(){
        return ResponseEntity.ok(service.getFavoritos());
    }
    

    @PostMapping
    public ResponseEntity<Livro> salvar(@Valid @RequestBody Livro livro){
        Livro livroSalvo = service.salvar(livro);
        if(livroSalvo == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(201).body(livroSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livro> atualizar(@PathVariable Long id,@Valid  @RequestBody Livro livro) {
        Livro livroAtualizado = service.atualizar(id, livro);

        if(livroAtualizado == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(livroAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id){

        if(!service.excluir(id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }




    
}
