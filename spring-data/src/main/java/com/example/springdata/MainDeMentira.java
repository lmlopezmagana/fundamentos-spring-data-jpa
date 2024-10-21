package com.example.springdata;

import com.example.springdata.model.Categoria;
import com.example.springdata.model.Producto;
import com.example.springdata.repos.CategoriaRepository;
import com.example.springdata.repos.ProductoRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MainDeMentira {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;


    @PostConstruct
    public void run() {

        Producto p = Producto.builder()
                .nombreProducto("Un producto")
                .descripcion("Se trata de un producto de nuestro catálogo")
                .precioVenta(123.45)
                .build();

        productoRepository.save(p);

        productoRepository.findById(1L).ifPresentOrElse(
                System.out::println,
                () -> System.out.println("No existe un producto con ID 1")
        );

        Categoria c = Categoria.builder()
                .nombre("Coches")
                .build();

        categoriaRepository
                .save(c);

        Producto coche = Producto.builder()
                .nombreProducto("Audi RS6")
                .descripcion("Un coche de más de 500 CV")
                .precioVenta(200000)
                .build();

        c.addProducto(coche);

        productoRepository.save(coche);

        categoriaRepository.findById(1L).ifPresentOrElse(
                categoria -> {
                    System.out.println("ID:%d - %s: %s".formatted(
                            categoria.getId(),
                            categoria.getNombre(),
                            categoria.getProductos().stream().map(Producto::getNombreProducto).collect(Collectors.joining(", "))
                    ));

                },
                () -> System.out.println("No existe una categoria con ID 1")
        );


    }

}

