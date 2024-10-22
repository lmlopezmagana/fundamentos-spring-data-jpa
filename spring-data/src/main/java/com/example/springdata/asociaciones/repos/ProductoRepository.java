package com.example.springdata.asociaciones.repos;

import com.example.springdata.asociaciones.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
