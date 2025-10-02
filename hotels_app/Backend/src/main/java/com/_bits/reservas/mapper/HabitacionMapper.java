package com._bits.reservas.mapper;

import org.springframework.stereotype.Component;

import com._bits.reservas.DTO.HabitacionDTO;
import com._bits.reservas.entity.Habitacion;
import com._bits.reservas.entity.Tipo_habitacion;
import com._bits.reservas.entity.Habitacion.EstadoHabitacion;


@Component
public class HabitacionMapper {

    // Bean de TipoHabitacionMapper para mapear el tipo de habitación
    private final TipoHabitacionMapper tipoHabitacionMapper;
    
    public HabitacionMapper(TipoHabitacionMapper tipoHabitacionMapper) {
        this.tipoHabitacionMapper = tipoHabitacionMapper;
    }

    // Mapea HabitacionDTO a Habitacion, requiere el Tipo_habitacion ya obtenido
    public Habitacion toEntity(HabitacionDTO habitacionDTO, Tipo_habitacion tipo_habitacion) {
        Habitacion habitacion = new Habitacion();
        habitacion.setTipoHabitacion(tipo_habitacion);
        habitacion.setHabitacionId(habitacionDTO.getHabitacionId());  // si es update, ya trae ID
        habitacion.setEstado(EstadoHabitacion.disponible); // Estado por defecto
        habitacion.setNumero(habitacionDTO.getNumero());
        habitacion.setPrecio(habitacionDTO.getPrecio());
        return habitacion;
    }

    // Mapea Habitacion a HabitacionDTO, incluyendo el TipoDTO
    public HabitacionDTO toDTO(Habitacion habitacion) {
        HabitacionDTO dto = new HabitacionDTO();
        dto.setHabitacionId(habitacion.getHabitacionId());
        dto.setEstado(habitacion.getEstado().toString());
        dto.setNumero(habitacion.getNumero());
        dto.setPrecio(habitacion.getPrecio());
        dto.setTipo(tipoHabitacionMapper.toDTO(habitacion.getTipoHabitacion()));
        return dto;
    }
}
