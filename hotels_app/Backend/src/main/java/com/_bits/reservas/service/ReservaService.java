package com._bits.reservas.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com._bits.reservas.DTO.ActualizarEstadoDTO;
import com._bits.reservas.DTO.ActualizarReservaDTO;
import com._bits.reservas.DTO.CheckInOutDTO;
import com._bits.reservas.DTO.RegistroReservaDTO;
import com._bits.reservas.DTO.ReservaDTO;
import com._bits.reservas.entity.Cliente;
import com._bits.reservas.entity.Habitacion;
import com._bits.reservas.entity.Reserva;
import com._bits.reservas.entity.Reserva.EstadoReserva;
import com._bits.reservas.mapper.ReservaMapper;
import com._bits.reservas.repository.ClienteRepository;
import com._bits.reservas.repository.HabitacionRepository;
import com._bits.reservas.repository.ReservaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final ClienteRepository clienteRepository;
    private final HabitacionRepository habitacionRepository;
    private final ReservaMapper reservaMapper;

    // Listar todas las reservas
    List<Reserva> listarTodas() {
        return reservaRepository.findAll();
    }

    // Listas las reservas por el Id del ciente POR LAS DUDAS
    List<Reserva> listarPorClienteId(Long cliente_id) {
        return reservaRepository.findByClienteClienteId(cliente_id);
    }

    // Listas las reservas por el nombre del cliente
    public List<Reserva> listarPorClienteNombreApellido(String nombre, String apellido) {
         Cliente cliente = clienteRepository.findByNombreAndApellido(nombre, apellido)
            .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado: " + nombre + " " + apellido));

        return reservaRepository.findByClienteClienteId(cliente.getClienteId());
    }
    
    // Crear una nueva reserva 
    public ReservaDTO crearReserva(RegistroReservaDTO dto) {
        // Creo un cliente nuevo, no pueden existir emails repetidos
        Cliente cliente = clienteRepository.findByEmail(dto.getCliente().getEmail())
        .orElseGet(() -> {
            Cliente nuevo = new Cliente();
            nuevo.setNombre(dto.getCliente().getNombre());
            nuevo.setApellido(dto.getCliente().getApellido());
            nuevo.setEmail(dto.getCliente().getEmail());
            nuevo.setTelefono(dto.getCliente().getTelefono());
            return clienteRepository.save(nuevo);
        });

        // Obtener el id desde el HabitacionDTO dentro de RegistroReservaDTO
        Long habitacionId = dto.getHabitacion().getHabitacionId();

        Habitacion habitacion = habitacionRepository.findById(habitacionId)
        .orElseThrow(() -> new IllegalArgumentException("Habitación no encontrada: " + habitacionId));

        // 3. Convertir DTO → Entity con el mapper
        Reserva reserva = reservaMapper.toEntity(dto, cliente, habitacion);

        // 4. Guardar en BD
        Reserva reservaGuardada = reservaRepository.save(reserva);

        // 5. Convertir Entity → DTO (para respuesta al frontend)
        return reservaMapper.toDTO(reservaGuardada);
    }


    // Eliminar una reserva por su ID
    public void eliminar(Long reserva_id) {
        reservaRepository.deleteById(reserva_id);
    }

    // Actualizar el estado de una reserva (pendiente, confirmada, cancelada)
    public Reserva actualizarEstadoReserva(ActualizarEstadoDTO dto) {
    // Buscar reserva
    Reserva reserva = reservaRepository.findById(dto.getReservaId())
        .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

    // Actualizar estado (conversión de String a Enum)
    try {
        EstadoReserva estado = EstadoReserva.valueOf(dto.getNuevoEstado().toLowerCase());
        reserva.setEstado(estado);
    } catch (IllegalArgumentException e) {
        throw new RuntimeException("Estado inválido: " + dto.getNuevoEstado());
    }

        // Guardar en BD
        return reservaRepository.save(reserva);
    }

    private CheckInOutDTO toDTO(Reserva reserva){
        CheckInOutDTO dto = new CheckInOutDTO();
        dto.setReservaId(reserva.getReservaId());
        dto.setNombreCliente(reserva.getCliente().getNombre());
        dto.setApellidoCliente(reserva.getCliente().getApellido());
        dto.setNumeroHabitacion(reserva.getHabitacion().getNumero());
        dto.setFechaEntrada(reserva.getFechaInicio());
        dto.setFechaSalida(reserva.getFechaFin());
        return dto;
    }

    public List<CheckInOutDTO> obtenerCheckInHoy() {
    return reservaRepository.findByFechaInicio(LocalDate.now())
                            .stream()
                            .map(this::toDTO)
                            .toList();
    }

    public List<CheckInOutDTO> obtenerCheckOutHoy() {
    return reservaRepository.findByFechaFin(LocalDate.now())
                            .stream()
                            .map(this::toDTO)
                            .toList();
    }

    public ReservaDTO actualizarReserva(ActualizarReservaDTO dto){
        Reserva reserva = reservaRepository.findById(dto.getReservaId())
            .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        
        // Actualizar fechas si se proporcionan
        if (dto.getFechaInicio() != null) {
            reserva.setFechaInicio(dto.getFechaInicio());
        }

        if(dto.getFechaFin() != null){
            reserva.setFechaFin(dto.getFechaFin());
        }

        if(dto.getEstado() != null){
            try {
                EstadoReserva estado = EstadoReserva.valueOf(dto.getEstado().toLowerCase());
                reserva.setEstado(estado);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Estado inválido: " + dto.getEstado());
            }
        }

        // Habitacion
        if(dto.getHabitacionId() != null){
            Habitacion habitacion = habitacionRepository.findById(dto.getHabitacionId())
                .orElseThrow(() -> new IllegalArgumentException("Habitación no encontrada: " + dto.getHabitacionId()));
            reserva.setHabitacion(habitacion);
        }

        // Cliente
        if(dto.getCliente() != null){
            Cliente cliente = reserva.getCliente(); // Obtener el cliente actual de la reserva
            if(dto.getCliente().getNombre() != null){
                cliente.setNombre(dto.getCliente().getNombre());
            }
            if(dto.getCliente().getApellido() != null) {
                cliente.setApellido(dto.getCliente().getApellido());
            }
            if(dto.getCliente().getEmail() != null){
                cliente.setEmail(dto.getCliente().getEmail());
            }
            if(dto.getCliente().getTelefono() != null){
                cliente.setTelefono(dto.getCliente().getTelefono());
            }

            clienteRepository.save(cliente);
        }

        Reserva guardada = reservaRepository.save(reserva);
        return reservaMapper.toDTO(guardada);
    } 


    // Contar reservas pendientes
    public long contarPendientes() {
        return reservaRepository.countByEstado(EstadoReserva.pendiente);
    }

    // Contar reservas confirmadas
    public long contarConfirmadas() {
        return reservaRepository.countByEstado(EstadoReserva.confirmada);
    }
}
