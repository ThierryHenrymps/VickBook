package com.vickbooks.book.controller;

import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import com.vickbooks.book.model.Livro;
import com.vickbooks.book.repository.LivroRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.vickbooks.book.repository.LivroRepository;


@RestController 
@RequestMapping("/api/v1/livros")
@CrossOrigin(origins = "*")
public class DownloadController {

    private final LivroRepository livroRepository;

    private final Path pastaLivros = Paths.get("livros");

    public DownloadController(LivroRepository livroRepository) {
        
        this.livroRepository = livroRepository;

    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadLivro(@PathVariable Long id) {

        try {
            Livro livro = livroRepository.findById(id).orElse(null);

            if( livro == null){
                return ResponseEntity.notFound().build();
            }

            if( livro.getArquivo() == null || livro.getArquivo().isBlank()){
                return ResponseEntity.notFound().build();
            }

            Path caminho = pastaLivros.resolve(livro.getArquivo()).normalize();

            Resource resource = new UrlResource(caminho.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).header(

                            HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + livro.getArquivo() + "\""

                    )
                    .body(resource);

        }catch (Exception e) {
            return ResponseEntity.internalServerError().build();

        }
        
    }
    
}
