package com.example.springdata;

import com.example.springdata.model.Categoria;
import com.example.springdata.model.Producto;
import com.example.springdata.model.ProductoDescripcion;
import com.example.springdata.model.Tag;
import com.example.springdata.repos.CategoriaRepository;
import com.example.springdata.repos.ProductoDescripcionRepository;
import com.example.springdata.repos.ProductoRepository;
import com.example.springdata.repos.TagRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MainDeMentira {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProductoDescripcionRepository productoDescripcionRepository;
    private final TagRepository tagRepository;
    private final CategoriaService categoriaService;


    @PostConstruct
    public void run() {

        Producto p = Producto.builder()
                .nombreProducto("Un producto")
                //.descripcion("Se trata de un producto de nuestro catálogo")
                .precioVenta(123.45)
                .build();

        productoRepository.save(p);


        ProductoDescripcion descripcion = ProductoDescripcion
                .builder()
                .descripcion("Se trata de un producto de nuestro catálogo")
                .marca("marca")
                .modelo("modelo")
                .url("http://")
                .build();

        p.setProductoDescripcion(descripcion);

        productoDescripcionRepository.save(descripcion);

        Tag tag1 = Tag.builder().nombre("Tag 1").build();
        Tag tag2 = Tag.builder().nombre("Tag 2").build();

        tagRepository.saveAll(List.of(tag1, tag2));

        p.addTag(tag1);
        p.addTag(tag2);

        productoRepository.save(p);


        /*productoRepository.findById(1L).ifPresentOrElse(
                System.out::println,
                () -> System.out.println("No existe un producto con ID 1")
        );*/

        Categoria c = Categoria.builder()
                .nombre("Coches")
                .build();

        categoriaRepository
                .save(c);

        Producto coche = Producto.builder()
                .nombreProducto("Audi RS6")
                //.descripcion("Un coche de más de 500 CV")
                .precioVenta(200000)
                .build();

        c.addProducto(coche);

        productoRepository.save(coche);


        System.out.println("N+1 Consultas");
        System.out.println("==============");
        //categoriaRepository.findById(1L).ifPresentOrElse(
        categoriaService.cargarCategoria(1L).ifPresentOrElse(
                categoria -> {
                    System.out.println("ID:%d - %s: %s".formatted(
                            categoria.getId(),
                            categoria.getNombre(),
                            categoria.getProductos().stream().map(Producto::getNombreProducto).collect(Collectors.joining(", "))
                    ));

                },
                () -> System.out.println("No existe una categoria con ID 1")
        );

        System.out.println("JOIN FETCH");
        categoriaRepository.findCategoriaByIdWithProductos(1L).ifPresentOrElse(
                categoria -> {
                    System.out.println("ID:%d - %s: %s".formatted(
                            categoria.getId(),
                            categoria.getNombre(),
                            categoria.getProductos().stream().map(Producto::getNombreProducto).collect(Collectors.joining(", "))
                    ));

                },
                () -> System.out.println("No existe una categoria con ID 1")
        );

        /*productoRepository.findAll()
                .forEach(System.out::println);
        */

    }

}

