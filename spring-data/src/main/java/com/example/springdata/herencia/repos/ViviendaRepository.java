package com.example.springdata.herencia.repos;

import com.example.springdata.herencia.model.Vivienda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViviendaRepository extends JpaRepository<Vivienda, Long> {
}
