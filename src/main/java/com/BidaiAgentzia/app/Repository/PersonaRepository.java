package com.BidaiAgentzia.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BidaiAgentzia.app.Model.Persona;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
    // Verificar si el correo electrónico ya existe
    boolean existsByEmail(String email);

    // Buscar persona por correo electrónico
    Persona findByEmail(String email);
	Persona findByNombre(String nombrePersona);
}
