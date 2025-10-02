package com._bits.reservas.DTO;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HabitacionDTO {
    // Datos de tipo_habitacion
    private TipoDTO tipo;
    // Datos de habitacion
    private Long habitacionId;
    private String estado;
    private String numero;
    private BigDecimal precio;   

}
