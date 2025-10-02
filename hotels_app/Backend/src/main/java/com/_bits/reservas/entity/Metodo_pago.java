package com._bits.reservas.entity;

import java.util.List;

import jakarta.persistence.Id;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "metodo_pago")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Metodo_pago {

    public enum NombreMetodo{
        TARJETA,
        EFECTIVO,
        TRANSFERENCIA
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "metodo_id")
    private Long metodoId;

    @Enumerated(EnumType.STRING)
    @Column(name = "nombre_metodo", nullable = false, unique = true)
    private NombreMetodo nombremetodo;

    @OneToMany(mappedBy = "metodoPago")
    private List<Pago> pagos;

}
