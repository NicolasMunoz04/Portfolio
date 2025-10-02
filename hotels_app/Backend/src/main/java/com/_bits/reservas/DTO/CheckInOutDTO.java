package com._bits.reservas.DTO;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckInOutDTO {
    private Long reservaId;
    private String nombreCliente;
    private String apellidoCliente;
    private String numeroHabitacion;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
}
