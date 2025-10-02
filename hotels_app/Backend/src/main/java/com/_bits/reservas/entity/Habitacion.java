package com._bits.reservas.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "habitacion")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Habitacion {

    public enum EstadoHabitacion {
    disponible,
    ocupada,
    mantenimiento
}
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "habitacion_id")
    private Long habitacionId;

    @Column(name = "numero", nullable = false, unique = true)
    private String numero;

    @Column(name = "precio", nullable = false)
    private BigDecimal precio;

    @Enumerated(EnumType.STRING)  // le digo a Hibernate que en la BD guarde un String
    @Column(name = "estado", nullable = false)
    private EstadoHabitacion estado = EstadoHabitacion.disponible; // valor por defecto

    @ManyToOne
    @JoinColumn(name = "tipo_id", nullable = false)
    private Tipo_habitacion tipoHabitacion;

    @OneToMany(mappedBy = "habitacion")
    private List<Reserva> reservas;
}
