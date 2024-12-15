package com.BidaiAgentzia.app.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.BidaiAgentzia.app.Model.Persona;
import com.BidaiAgentzia.app.Model.Reserva;
import com.BidaiAgentzia.app.Model.Viaje;
import com.BidaiAgentzia.app.Repository.PersonaRepository;
import com.BidaiAgentzia.app.Repository.ReservaRepository;
import com.BidaiAgentzia.app.Repository.ViajeRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class ViajeController {

    @Autowired
    private ViajeRepository viajeRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @GetMapping("/reservar")
    public String mostrarFormularioReserva(@RequestParam String destino, Model model) {
        List<Viaje> viajes = viajeRepository.findAll();
        model.addAttribute("viajes", viajes);
        model.addAttribute("reserva", new Reserva());
        model.addAttribute("destinoSeleccionado", destino);  // Pasamos el destino al formulario
        model.addAttribute("fechaHoy", LocalDate.now());  // Pasa la fecha de hoy al modelo
        return "reserva";
    }

    @PostMapping("/reservar")
    public String realizarReserva(@ModelAttribute Reserva reserva, @RequestParam String destino, HttpSession session, Model model) {
        // Obtener la fecha seleccionada
        LocalDate fechaSeleccionada = reserva.getFecha() != null ? LocalDate.parse(reserva.getFecha()) : LocalDate.now();

        // Validar si la fecha es válida (no puede ser en el pasado)
        if (fechaSeleccionada.isBefore(LocalDate.now())) {
            model.addAttribute("error", "La fecha seleccionada no puede ser en el pasado.");
            return "reserva";  // Volver al formulario de reserva con el mensaje de error
        }

        // Obtener el nombre de la persona desde la sesión
        String nombrePersona = (String) session.getAttribute("nombre");
        Persona persona = personaRepository.findByNombre(nombrePersona);

        if (persona != null) {
            reserva.setPersona(persona);  // Asignar la persona a la reserva
        }

        // Obtener el viaje correspondiente al destino
        Viaje viajeSeleccionado = viajeRepository.findByDestino(destino);

        if (viajeSeleccionado != null) {
            reserva.setViaje(viajeSeleccionado);  // Asignar el viaje a la reserva
        } else {
            // Si no se encuentra el viaje, redirige a la página de error o muestra un mensaje
            return "error";  // O también puedes redirigir a un formulario de error
        }

        // Guardar la reserva en la base de datos
        reservaRepository.save(reserva);

        // Redirigir a la lista de viajes o donde corresponda
        return "redirect:/viajes";
    }


    // Lista todos los viajes disponibles
    @GetMapping("/viajes")
    public String listarViajes(Model model) {
        List<Viaje> viajes = viajeRepository.findAll();
        model.addAttribute("viajes", viajes);
        return "viajes";
    }
}
