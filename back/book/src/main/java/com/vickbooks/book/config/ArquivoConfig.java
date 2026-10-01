package com.vickbooks.book.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ArquivoConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path caminhoReal = Paths.get(System.getProperty("user.home"), "Documents", "VickBooks", "back", "book", "livros", "capas");

        registry.addResourceHandler("/capas/**")
                .addResourceLocations(caminhoReal.toUri().toString());
    }
}