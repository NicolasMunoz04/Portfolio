package com._bits.reservas.mapper;

import org.springframework.stereotype.Component;

import com._bits.reservas.DTO.RegistroReservaDTO;
import com._bits.reservas.DTO.ReservaDTO;
import com._bits.reservas.entity.Cliente;
import com._bits.reservas.entity.Habitacion;
import com._bits.reservas.entity.Reserva;
import com._bits.reservas.entity.Reserva.EstadoReserva;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class ReservaMapper {

    private final ClienteMapper clienteMapper;
    private final HabitacionMapper habitacionMapper;

    public ReservaMapper(ClienteMapper clienteMapper, HabitacionMapper habitacionMapper) {
        this.clienteMapper = clienteMapper;
        this.habitacionMapper = habitacionMapper;
    }

    // DTO → Entidad (para guardar en BD)
    public Reserva toEntity(RegistroReservaDTO dto, Cliente cliente, Habitacion habitacion) {
        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);   
        reserva.setHabitacion(habitacion); 
        reserva.setFechaInicio(dto.getFechaInicio());
        reserva.setFechaFin(dto.getFechaFin());
        reserva.setEstado(EstadoReserva.pendiente); // default
        return reserva;
    }

    // Entidad → DTO (para mostrar en frontend)
    public ReservaDTO toDTO(Reserva reserva) {
        ReservaDTO dto = new ReservaDTO();
        dto.setReservaId(reserva.getReservaId());
        dto.setFechaInicio(reserva.getFechaInicio());
        dto.setFechaFin(reserva.getFechaFin());
        dto.setEstado(reserva.getEstado().name());
        dto.setCliente(clienteMapper.toDTO(reserva.getCliente()));
        dto.setHabitacion(habitacionMapper.toDTO(reserva.getHabitacion()));
        return dto;
    }
}
