package com.example.springdata.consultas;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EjemploConsultasJPQL {

    private final ProductoRepository productoRepository;

    @PostConstruct
    public void run() {

        System.out.println("=== Obtener todos los productos ===");
        productoRepository.obtenerTodos()
                .forEach(p -> System.out.println("%s (%.2f€)".formatted(p.getNombreProducto(), p.getPrecioVenta())));

        System.out.println("=== Obtener el precio de venta más caro ===");
        System.out.println(productoRepository.precioMasCaro());

        System.out.println("=== Obtener la información más básica de los productos ===");
        productoRepository.informacionBasica()
                .forEach(dto -> System.out.println("%d: %s".formatted(dto.id(), dto.nombre())));


    }

}
