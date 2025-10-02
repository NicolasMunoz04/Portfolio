package com._bits.reservas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com._bits.reservas.entity.Metodo_pago;

@Repository
public interface Metodo_pagoRepository extends JpaRepository<Metodo_pago, Long>  {
    
}
