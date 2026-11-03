package com.tienda.pedidos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tienda.pedidos.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteEmail(String clienteEmail);
}