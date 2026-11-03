package com.tienda.pedidos.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tienda.pedidos.entity.Pedido;
import com.tienda.pedidos.service.NotificacionEmailService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping
    public Pedido crearPedido(@RequestBody Pedido pedido) {
        if (pedido.getClienteEmail() == null || pedido.getClienteEmail().isBlank()) {
            throw new RuntimeException("El email del cliente es obligatorio");
        }
        if (pedido.getSubtotal() == null || pedido.getSubtotal() <= 0) {
            throw new RuntimeException("El subtotal debe ser mayor a cero");
        }

        double descuento;
        if ("VIP".equalsIgnoreCase(pedido.getTipoCliente())) {
            descuento = pedido.getSubtotal() * 0.15;
        } else if ("MAYORISTA".equalsIgnoreCase(pedido.getTipoCliente())) {
            descuento = pedido.getSubtotal() * 0.25;
        } else {
            descuento = 0.0;
        }
        double baseConDescuento = pedido.getSubtotal() - descuento;
        double iva = baseConDescuento * 0.19;
        double total = baseConDescuento + iva;

        pedido.setDescuento(descuento);
        pedido.setIva(iva);
        pedido.setTotal(total);
        pedido.setEstado("CREADO");
        pedido.setFechaCreacion(LocalDateTime.now());
        pedido.setNotasInternas("Pedido creado automaticamente, revisar stock antes de despachar");

        String sql = "INSERT INTO pedido (cliente_email, tipo_cliente, subtotal, descuento, iva, total, "
                + "estado, notas_internas, fecha_creacion) VALUES ('" + pedido.getClienteEmail() + "', '"
                + pedido.getTipoCliente() + "', " + pedido.getSubtotal() + ", " + descuento + ", " + iva
                + ", " + total + ", 'CREADO', '" + pedido.getNotasInternas() + "', NOW())";
        jdbcTemplate.update(sql);

        NotificacionEmailService notificador = new NotificacionEmailService();
        notificador.enviar(pedido.getClienteEmail(), "Tu pedido fue creado. Total: " + total);

        System.out.println("Pedido creado para " + pedido.getClienteEmail());

        return pedido;
    }

    @GetMapping("/{id}")
    public Pedido obtenerPedido(@PathVariable Long id) {
        try {
            String sql = "SELECT * FROM pedido WHERE id = " + id;
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Pedido.class));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}
