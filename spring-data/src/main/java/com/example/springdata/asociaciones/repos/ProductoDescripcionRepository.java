package com.example.springdata.asociaciones.repos;

import com.example.springdata.asociaciones.model.ProductoDescripcion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoDescripcionRepository extends JpaRepository<ProductoDescripcion, Long> {
}
