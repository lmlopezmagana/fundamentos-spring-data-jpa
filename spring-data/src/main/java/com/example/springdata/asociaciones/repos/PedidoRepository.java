package com.example.springdata.asociaciones.repos;

import com.example.springdata.asociaciones.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
