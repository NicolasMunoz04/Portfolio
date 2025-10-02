package com._bits.reservas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com._bits.reservas.entity.Mensaje;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long>{
    
}
