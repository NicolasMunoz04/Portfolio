package com._bits.reservas.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com._bits.reservas.DTO.HabitacionDTO;
import com._bits.reservas.entity.Habitacion;
import com._bits.reservas.entity.Tipo_habitacion;
import com._bits.reservas.mapper.HabitacionMapper;
import com._bits.reservas.repository.HabitacionRepository;
import com._bits.reservas.repository.Tipo_habitacionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class HabitacionService {

    // Beans
    private final HabitacionRepository habitacionRepository;
    private final HabitacionMapper habitacionMapper;
    private final Tipo_habitacionRepository tipoHabitacionRepository;

    // Crear una habitacion nueva junto con su tipo de habitacion
    public HabitacionDTO crear(HabitacionDTO habitacionDTO) {
        // Validaciones
        if (habitacionDTO.getTipo() == null) {
        throw new IllegalArgumentException("El tipo de habitación es obligatorio");
        }
        
        if (habitacionDTO.getNumero() == null || habitacionDTO.getNumero().isBlank()) {
            throw new IllegalArgumentException("El número de habitación es obligatorio");
        }

        if (habitacionRepository.existsByNumero(habitacionDTO.getNumero())) {
            throw new IllegalArgumentException("El número de habitación ya existe");
        }

        if (habitacionDTO.getPrecio() == null || habitacionDTO.getPrecio().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio de la habitación debe ser un valor positivo");
        }   

        // Buscar tipo existente o crear nuevo
        Tipo_habitacion tipo;
        if (habitacionDTO.getTipo().getTipoId() != null) {
            // Caso: usar un tipo ya existente
            tipo = tipoHabitacionRepository.findById(habitacionDTO.getTipo().getTipoId())
            .orElseThrow(() -> new IllegalArgumentException("El tipo de habitación no existe con id: " + habitacionDTO.getTipo().getTipoId()));
        } else {
            // Caso: crear un nuevo tipo
            tipo = new Tipo_habitacion();
            tipo.setNombre(habitacionDTO.getTipo().getNombre());
            tipo.setDescripcion(habitacionDTO.getTipo().getDescripcion());
            tipoHabitacionRepository.save(tipo);
        }
        // Conversiones
        Habitacion habitacion = habitacionMapper.toEntity(habitacionDTO, tipo);

        Habitacion habitacionGuardada = habitacionRepository.save(habitacion);

        return habitacionMapper.toDTO(habitacionGuardada);
    }

    // Lista todas las habitaciones existentes, junto con su tipo de habitacion
    public List<HabitacionDTO> listarTodas() {
        List<Habitacion> habitaciones = habitacionRepository.findAll();
        return habitaciones.stream()
                           .map(habitacionMapper::toDTO)
                           .toList();
    }

    // Listas las habitaciones por su estado actual (disponible, ocupada, mantenimiento)
    public List<Habitacion> listarPorEstado(Habitacion.EstadoHabitacion estado) {
        return habitacionRepository.findByEstado(estado);
    }

    // Actualizar habitacion, este método es un poco inservible ahora
    public Habitacion actualizar(Habitacion habitacion) {
        return habitacionRepository.save(habitacion);
    }

    // Eliminar una habitacion por su ID
    public void eliminar(Long habitacion_id) {
        habitacionRepository.deleteById(habitacion_id);
    }

    // Contar las habitaciones disponibles
    public List<HabitacionDTO> obtenerDisponibles() {
        List<Habitacion> disponibles = habitacionRepository.findByEstado(Habitacion.EstadoHabitacion.disponible);
        return disponibles.stream()
                      .map(habitacionMapper::toDTO)
                      .toList();
    }



    // Actualizar estado de una habitación
    @Transactional
    public Habitacion actualizarEstadoHabitacion(Long habitacionId, String nuevoEstado) {
    Habitacion habitacion = habitacionRepository.findById(habitacionId)
        .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));

    try {
        Habitacion.EstadoHabitacion estado = Habitacion.EstadoHabitacion.valueOf(nuevoEstado.toLowerCase());
        habitacion.setEstado(estado);
    } catch (IllegalArgumentException e) {
        throw new RuntimeException("Estado inválido: " + nuevoEstado);
    }

    return habitacionRepository.save(habitacion);
    }
}
