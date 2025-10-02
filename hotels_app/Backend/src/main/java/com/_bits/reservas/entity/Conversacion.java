package com._bits.reservas.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Id;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "conversacion")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Conversacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conversacion_id")
    private Long conversacionId;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fecha_inicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDateTime fecha_fin;

    @OneToMany(mappedBy = "conversacion")
    private List<Mensaje> mensajes;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}
