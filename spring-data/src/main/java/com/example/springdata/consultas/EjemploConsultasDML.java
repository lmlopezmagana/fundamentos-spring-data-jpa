package com.example.springdata.consultas;

import com.example.springdata.asociaciones.model.Categoria;
import com.example.springdata.asociaciones.repos.CategoriaRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EjemploConsultasDML {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    @PostConstruct
    public void run() {

        //productoRepository.deleteByPrecioVentaGreaterThan(1000);
        //productoRepository.eliminarPrecioMayorQue(1000);
        productoRepository.eliminarPrecioMayorQueNativa(1000);

        System.out.println("=== 10 productos más caros ===");
        productoRepository.findTop10ByOrderByPrecioVentaDesc()
                        .forEach(p -> System.out.println("%s (%.2f€)".formatted(p.getNombreProducto(), p.getPrecioVenta())));

        System.out.println("=== Incrementar precios un 10% ===");
        int modificados = productoRepository.incrementarPrecio(0.10);
        System.out.println("Cantidad de productos modificados %d".formatted(modificados));

        System.out.println("=== 10 productos más caros ===");
        productoRepository.findTop10ByOrderByPrecioVentaDesc()
                .forEach(p -> System.out.println("%s (%.2f€)".formatted(p.getNombreProducto(), p.getPrecioVenta())));


        // Rebajar un 15% los productos de la categoría con ID 1
        // Usar getReferenceById es más eficiente que findById
        // para esta operación.
        Categoria c = categoriaRepository.getReferenceById(1L);
        productoRepository.ofertaProductosCategoria(c, 0.15);
        System.out.println("=== Productos de la categoría 1L ===");
        productoRepository.productosDeCategoria1()
                .forEach(p -> System.out.println("%s (%.2f€) (Categoría %s)"
                        .formatted(p.getNombreProducto(),
                                p.getPrecioVenta(),
                                p.getCategoria().getNombre()
                        )));







    }

}
