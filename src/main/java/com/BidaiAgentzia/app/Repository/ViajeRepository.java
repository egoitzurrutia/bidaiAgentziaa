package com.BidaiAgentzia.app.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.BidaiAgentzia.app.Model.Viaje;

public interface ViajeRepository extends JpaRepository<Viaje, Long> {
    Viaje findByDestino(String destino);

}

