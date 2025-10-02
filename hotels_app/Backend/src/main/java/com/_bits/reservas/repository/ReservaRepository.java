package com._bits.reservas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com._bits.reservas.entity.Reserva;
import com._bits.reservas.entity.Reserva.EstadoReserva;
import java.time.LocalDate;


@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByClienteClienteId(Long clienteId);
    List<Reserva> findByFechaInicio(LocalDate fechaInicio);
    List<Reserva> findByFechaFin(LocalDate fechaFin);
    List<Reserva> findByCliente_Nombre(String nombre);
    // Contar reservas por estado
    long countByEstado(EstadoReserva estado);
}
