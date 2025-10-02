package com._bits.reservas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com._bits.reservas.DTO.ActualizarEstadoDTO;
import com._bits.reservas.DTO.ActualizarReservaDTO;
import com._bits.reservas.DTO.CheckInOutDTO;
import com._bits.reservas.DTO.RegistroReservaDTO;
import com._bits.reservas.DTO.ReservaDTO;
import com._bits.reservas.entity.Reserva;
import com._bits.reservas.mapper.ReservaMapper;
// import com._bits.reservas.service.ClienteService;
// import com._bits.reservas.service.HabitacionService;
import com._bits.reservas.service.ReservaService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/reservas")
public class ReservaController {
    private final ReservaService reservaService;
    // private final ClienteService clienteService;
    // private final HabitacionService habitacionService;
    private final ReservaMapper reservaMapper;

    @PostMapping("/registro")
    public ResponseEntity<ReservaDTO> registrarReserva(@RequestBody RegistroReservaDTO registroReservaDTO) {
        ReservaDTO dto = reservaService.crearReserva(registroReservaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/estado")
    public ResponseEntity<Reserva> actualizarEstado(@RequestBody ActualizarEstadoDTO dto) {
        Reserva reservaActualizada = reservaService.actualizarEstadoReserva(dto);
        return ResponseEntity.ok(reservaActualizada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaDTO> actualizarReserva(
        @PathVariable Long id,
        @RequestBody ActualizarReservaDTO dto) {

        dto.setReservaId(id); // aseguro que use el ID de la URL
        ReservaDTO actualizada = reservaService.actualizarReserva(dto);
        return ResponseEntity.ok(actualizada);
    }


    @GetMapping("/cliente/{nombre}/{apellido}")
    public ResponseEntity<List<ReservaDTO>> obtenerReservasPorCliente(@PathVariable String nombre, @PathVariable String apellido) {
        List<Reserva> reservas = reservaService.listarPorClienteNombreApellido(nombre, apellido);
        List<ReservaDTO> reservasDTO = reservas.stream()
                                           .map(reservaMapper::toDTO)
                                           .toList();
        return ResponseEntity.ok(reservasDTO);
    }

    @GetMapping("/checkin")
    public ResponseEntity<List<CheckInOutDTO>> getCheckInHoy() {
        List<CheckInOutDTO> checkIns = reservaService.obtenerCheckInHoy();
        return ResponseEntity.ok(checkIns);
    }

    @GetMapping("/checkout")
    public ResponseEntity<List<CheckInOutDTO>> getCheckOutHoy() {
        List<CheckInOutDTO> checkOuts = reservaService.obtenerCheckOutHoy();
        return ResponseEntity.ok(checkOuts);
    }
    
}
