package com.example.springdata.repos;

import com.example.springdata.model.ProductoDescripcion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoDescripcionRepository extends JpaRepository<ProductoDescripcion, Long> {
}
