package com._bits.reservas.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "mensaje")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Mensaje {
    
    public enum remitente {
        CLIENTE,
        CHATBOT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mensaje_id")
    private Long mensajeId;

    @Column(name = "contenido", nullable = false)
    private String contenido;

    @Column(name = "datos", columnDefinition = "jsonb")
    private String datos;

    @Column(name = "fecha_envio", nullable = false)
    private LocalDateTime fechaEnvio;

    @ManyToOne
    @JoinColumn(name = "conversacion_id", nullable = false)
    private Conversacion conversacion;


}
