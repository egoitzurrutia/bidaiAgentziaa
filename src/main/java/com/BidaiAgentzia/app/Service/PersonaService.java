package com.BidaiAgentzia.app.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BidaiAgentzia.app.Model.Persona;
import com.BidaiAgentzia.app.Repository.PersonaRepository;

@Service
public class PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    // Método para autenticar a una persona y obtener su rol
    public int authenticateAndGetRole(String email, String password) {
        // Buscar a la persona por el email
        Persona persona = personaRepository.findByEmail(email);

        if (persona == null) {
            return -1;  // Si no existe el usuario, retornamos -1 (error)
        }

        // Verificar la contraseña (sin cifrado en este ejemplo)
        if (persona.getContraseña().equals(password)) {
            return persona.getRol();  // Retornamos el rol de la persona
        }

        return -1;  // Si las credenciales no coinciden, retornamos -1 (error)
    }

    // Método para obtener todos los usuarios
    public List<Persona> findAll() {
        return personaRepository.findAll(); // Retorna todos los registros de la tabla Persona
    }
}
