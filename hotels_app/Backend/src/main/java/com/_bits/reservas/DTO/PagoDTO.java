package com._bits.reservas.DTO;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// Esta clase podría usarse para las reservas, pero por ahora no la uso
public class PagoDTO {
    private BigDecimal monto;
    private Date fecha_pago; 
    private Long metodo_pago_id; // FK de metodo_pago
    private String nombre_metodo; // para mostrar el nombre del metodo de pago
    
}
