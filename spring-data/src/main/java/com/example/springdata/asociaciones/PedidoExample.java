package com.example.springdata.asociaciones;

import com.example.springdata.asociaciones.model.LineaPedido;
import com.example.springdata.asociaciones.model.Pedido;
import com.example.springdata.asociaciones.model.Producto;
import com.example.springdata.asociaciones.repos.PedidoRepository;
import com.example.springdata.consultas.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class PedidoExample {

    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;

    //@PostConstruct
    public void run() {

        // Seleccionamos varios productos al azar para incluirlos en el pedido.
        List<Producto> todos = productoRepository.findAll();
        Collections.shuffle(todos);
        Random random = new Random();
        int cantidad = random.nextInt(1, 5);
        List<Producto> aleatorios = todos.stream().limit(cantidad).toList();

        // Creamos el pedido y añadimos Líneas de Pedido
        Pedido pedido = Pedido.builder()
                .cliente("Luis Miguel López")
                .build();


        aleatorios.forEach(producto -> {
            pedido.addLineaPedido(
                    LineaPedido.builder()
                            .cantidad(random.nextInt(1,5))
                            .producto(producto)
                            .precioVenta(producto.getPrecioVenta())
                            .build()
            );
        });

        System.out.println("=== Almacenando el pedido ===");
        pedidoRepository.save(pedido);

        System.out.println("=== Eliminando el pedido ===");
        pedidoRepository.delete(pedido);






    }

}
