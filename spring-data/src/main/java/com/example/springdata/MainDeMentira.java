package com.example.springdata;

import com.example.springdata.model.Producto;
import com.example.springdata.repos.ProductoRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MainDeMentira {

    private final ProductoRepository repo;


    @PostConstruct
    public void run() {

        Producto p = Producto.builder()
                .nombreProducto("Un producto")
                .descripcion("Se trata de un producto de nuestro catálogo")
                .precioVenta(123.45)
                .build();

        repo.save(p);

        repo.findById(1L).ifPresentOrElse(
                System.out::println,
                () -> System.out.println("No existe un producto con ID 1")
        );


    }

}

