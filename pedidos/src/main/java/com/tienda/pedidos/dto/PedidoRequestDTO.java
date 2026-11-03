package com.tienda.pedidos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PedidoRequestDTO {

    @NotBlank(message = "El email del cliente es obligatorio")
    private String clienteEmail;

    @NotBlank(message = "El tipo de cliente es obligatorio")
    private String tipoCliente;

    // Aporte propio: @NotNull evita un NPE cuando el subtotal no viene en el JSON
    @NotNull(message = "El subtotal es obligatorio")
    @Positive(message = "El subtotal debe ser mayor a cero")
    private Double subtotal;

    public String getClienteEmail() { return clienteEmail; }
    public void setClienteEmail(String clienteEmail) { this.clienteEmail = clienteEmail; }
    public String getTipoCliente() { return tipoCliente; }
    public void setTipoCliente(String tipoCliente) { this.tipoCliente = tipoCliente; }
    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }
}