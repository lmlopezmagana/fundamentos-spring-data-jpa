package com.example.springdata.herencia;

import com.example.springdata.herencia.model.Usuario;
import com.example.springdata.herencia.repos.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EjemploHerencia {

    private final UsuarioRepository usuarioRepository;

    @PostConstruct
    public void run() {

        Usuario usuario = Usuario.builder()
                .username("luismilopez")
                .password("12345")
                .email("luismi@openwebinars.net")
                .build();

        usuarioRepository.save(usuario);


    }

}
