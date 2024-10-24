package com.example.springdata.consultas;

import com.example.springdata.asociaciones.model.Producto;
import com.example.springdata.asociaciones.model.Tag;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EjemploConsultasJPQL {

    private final ProductoRepository productoRepository;

    //@PostConstruct
    public void run() {

        System.out.println("=== Obtener todos los productos ===");
        productoRepository.obtenerTodos()
                .forEach(p -> System.out.println("%s (%.2f€)".formatted(p.getNombreProducto(), p.getPrecioVenta())));

        System.out.println("=== Obtener el precio de venta más caro ===");
        System.out.println(productoRepository.precioMasCaro());

        System.out.println("=== Obtener la información más básica de los productos ===");
        productoRepository.informacionBasica()
                .forEach(dto -> System.out.println("%d: %s".formatted(dto.id(), dto.nombre())));

        System.out.println("=== Productos de la categoría 1 ===");
        productoRepository.productosDeCategoria1()
                .forEach(p -> System.out.println("%s (%.2f€) (Categoría %s)"
                        .formatted(p.getNombreProducto(),
                                p.getPrecioVenta(),
                                p.getCategoria().getNombre()
                                )));


        Producto producto = Producto.builder()
                .nombreProducto("Producto sin categoria")
                .precioVenta(12.34)
                .build();

        productoRepository.save(producto);

        System.out.println("=== Productos con su categoría ===");
        productoRepository.productosConCategoriaSiTienen()
                .forEach(p -> System.out.println("%s (%.2f€) (Categoría %s)"
                        .formatted(p.getNombreProducto(),
                                p.getPrecioVenta(),
                                p.getCategoria() != null ? p.getCategoria().getNombre() : "Sin categoría"
                        )));

        System.out.println("=== Obtener el precio de venta más caro de la categoría Electrónica ===");
        System.out.println(productoRepository.precioMasCaroDeCategoria("Electrónica"));

        System.out.println("=== Obtener los productos con algunos tags ===");
        productoRepository.productosConTags(List.of("IA","Divertido"))
                .forEach(p -> System.out.println("%s (%.2f€) (%s)".formatted(
                        p.getNombreProducto(),
                        p.getPrecioVenta(),
                        p.getTags().stream().map(Tag::getNombre).collect(Collectors.joining(","))
                )));

        System.out.println("=== Productos que contienen Inalámbrico con precio venta [20,50]€ ===");
        productoRepository.productosPorNombreYRangoPrecio("inalámbrico", 20, 50)
                .forEach(p -> System.out.println("%s (%.2f€) (Categoría: %s)".formatted(
                        p.getNombreProducto(),
                        p.getPrecioVenta(),
                        p.getCategoria() != null ? p.getCategoria().getNombre() : "Sin categoría"
                )));



    }

}
