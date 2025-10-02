package com._bits.reservas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com._bits.reservas.DTO.ActualizarEstadoHabDTO;
import com._bits.reservas.DTO.HabitacionDTO;
import com._bits.reservas.entity.Habitacion;
import com._bits.reservas.service.HabitacionService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping("/habitaciones")
public class HabitacionController {

    private final HabitacionService habitacionService;
    
    // Este endpoint actualiza el estado de una habitación
    @PutMapping("/estado")
    public ResponseEntity<Habitacion> actualizarEstado(@RequestBody ActualizarEstadoHabDTO dto) {
        Habitacion habitacion = habitacionService.actualizarEstadoHabitacion(dto.getHabitacionId(), dto.getNuevoEstado());
        return ResponseEntity.ok(habitacion);
    }

    @PostMapping("/crear")
    public ResponseEntity<HabitacionDTO> crearHabitacion(@RequestBody HabitacionDTO habitacionDTO) {
        HabitacionDTO nuevaHabitacion = habitacionService.crear(habitacionDTO);
        return ResponseEntity.status(201).body(nuevaHabitacion);
    }
    
    @GetMapping("/listar")
    public ResponseEntity<List<HabitacionDTO>> listarHabitaciones() {
        return ResponseEntity.ok(habitacionService.listarTodas());
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<HabitacionDTO>> listarDisponibles() {
        return ResponseEntity.ok(habitacionService.obtenerDisponibles());
    }

}
