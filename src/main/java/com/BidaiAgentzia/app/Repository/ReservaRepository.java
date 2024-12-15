package com.BidaiAgentzia.app.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BidaiAgentzia.app.Model.Persona;
import com.BidaiAgentzia.app.Model.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
	    List<Reserva> findByPersona(Persona persona);
	}


