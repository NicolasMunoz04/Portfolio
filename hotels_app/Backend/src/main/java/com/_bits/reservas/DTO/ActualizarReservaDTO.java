package com._bits.reservas.DTO;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActualizarReservaDTO {
    private Long reservaId;
    private LocalDate FechaInicio;
    private LocalDate FechaFin;
    private String estado;

    // Datos de habitacion
    private Long habitacionId;

    // Datos de cliente
    private ClienteDTO cliente;
}
