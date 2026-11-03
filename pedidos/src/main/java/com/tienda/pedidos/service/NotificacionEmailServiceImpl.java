package com.tienda.pedidos.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificacionEmailServiceImpl implements NotificacionService {

    private static final Logger log = LoggerFactory.getLogger(NotificacionEmailServiceImpl.class);

    @Override
    public void enviarConfirmacion(String clienteEmail, double total) {
        // Simulacion de envio. Aporte propio: SLF4J en vez de System.out.println
        log.info("Correo de confirmacion enviado a clienteEmail={}, total={}", clienteEmail, total);
    }
}
