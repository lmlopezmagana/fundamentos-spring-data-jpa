package com.example.springdata.consultas;

import com.example.springdata.asociaciones.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findTop10ByOrderByPrecioVentaDesc();

    long countByCategoriaNombre(String nombre);

    Optional<Producto> findFirstByCategoriaNombreOrderByPrecioVentaAsc(String nombre);

    List<Producto> findByNombreProductoContainsIgnoreCase(String nombre);

    List<Producto> findByPrecioVentaBetween(double startPrecioVenta, double endPrecioVenta);

    List<Producto> findByNombreProductoContainsIgnoreCaseAndPrecioVentaBetween(
            String nombre, double startPrecioVenta, double endPrecioVenta
    );

}