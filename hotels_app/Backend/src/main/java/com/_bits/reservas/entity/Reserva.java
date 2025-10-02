package com._bits.reservas.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Id;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reserva")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Reserva {
    
    public enum EstadoReserva {
    pendiente,
    confirmada,
    cancelada
}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserva_id")
    private Long reservaId;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Enumerated(EnumType.STRING) // guarda el nombre del enum ("PENDIENTE", etc.)
    @Column(name = "estado", nullable = false)
    private EstadoReserva estado = EstadoReserva.pendiente; // valor por defecto

    @Column(name = "fecha_reserva", nullable = false)
    private LocalDateTime fechaReserva;

    // Relación a Cliente
    @ManyToOne(optional = false) // muchas reservas → un cliente
    @JoinColumn(name = "cliente_id", nullable = false) 
    private Cliente cliente;

    // Relación a Habitacion
    @ManyToOne
    @JoinColumn(name = "habitacion_id", nullable = false)
    private Habitacion habitacion;

    @OneToMany(mappedBy = "reserva")
    private List<Pago> pagos;

}
