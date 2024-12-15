package com.BidaiAgentzia.app.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.BidaiAgentzia.app.Model.Persona;
import com.BidaiAgentzia.app.Model.Reserva;
import com.BidaiAgentzia.app.Repository.PersonaRepository;
import com.BidaiAgentzia.app.Repository.ReservaRepository;

import jakarta.servlet.http.HttpSession;


@Controller
public class ReservaController {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @GetMapping("/mis-reservas")
    public String verMisReservas(Model model, HttpSession session) {
        // Obtener el nombre de la persona desde la sesión
        String nombrePersona = (String) session.getAttribute("nombre");

        // Buscar la persona en la base de datos
        Persona persona = personaRepository.findByNombre(nombrePersona);

        if (persona != null) {
            // Obtener las reservas de la persona logueada
            List<Reserva> reservas = reservaRepository.findByPersona(persona);

            // Pasar las reservas al modelo para mostrarlas en la vista
            model.addAttribute("reservas", reservas);
        }

        return "mis-reservas"; // Nombre de la vista que se va a mostrar
    }
}
