package com.example.springdata.herencia.repos;

import com.example.springdata.herencia.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
