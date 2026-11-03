package com.tienda.pedidos.service;

public class NotificacionEmailService {
    public void enviar(String email, String mensaje) {
        System.out.println("Correo enviado a " + email + ": " + mensaje);
    }
}