package com.BidaiAgentzia.app.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.BidaiAgentzia.app.Model.Persona;
import com.BidaiAgentzia.app.Repository.PersonaRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PersonaController {

    @Autowired
    private PersonaRepository personaRepo;

    @GetMapping("/usuarios")
    public String listarViajes(Model model) {
        // Obtener todos los viajes desde la base de datos
        List<Persona> personas = personaRepo.findAll();
        model.addAttribute("personas", personas); // Pasar los datos a la vista
        return "usuarios";
    }

    // Método para obtener los detalles de una persona específica
    @GetMapping("/usuarios/{id}")
    public String getUserDetails(@PathVariable Long id, Model model) {
        // Obtener la persona por su id
        Persona persona = personaRepo.findById(id).orElse(null);
        if (persona != null) {
            model.addAttribute("persona", persona); // Pasar la persona al modelo
            return "detalles_persona"; // El nombre del archivo HTML para mostrar los detalles
        }
        // Si no se encuentra la persona, redirigir a la lista de usuarios
        return "redirect:/usuarios";
    }

    // Eliminar un usuario por ID
    @GetMapping("/usuarios/borrar/{id}")
    public String deleteUser(@PathVariable Long id, HttpServletRequest request) {
        // Obtener la sesión actual
        HttpSession session = request.getSession(false);

        // Verificar si la sesión está activa
        if (session != null) {
            Long sessionUserId = (Long) session.getAttribute("userId"); // Obtener el ID del usuario desde la sesión

            // Eliminar la persona por su ID
            personaRepo.deleteById(id);

            // Si la persona eliminada es la misma que está autenticada (basado en la sesión)
            if (id.equals(sessionUserId)) {
                // Invalidar la sesión para cerrar sesión
                session.invalidate();

                // Redirigir al login o cualquier página que desees después de cerrar sesión
                return "redirect:/login"; // O cualquier otra página que desees después de cerrar sesión
            }
        }

        // Si la persona no está autenticada o no es el mismo usuario, redirige a la lista de usuarios
        return "redirect:/usuarios";
    }

    // Redirigir al formulario para actualizar un usuario
    @GetMapping("/usuarios/actualizar/{id}")
    public String updateUserForm(@PathVariable Long id, Model model) {
        Persona persona = personaRepo.findById(id).orElse(null);
        if (persona != null) {
            model.addAttribute("persona", persona); // Pasamos los datos de la persona al formulario
            return "actualizar_persona"; // Página de actualización
        }
        return "redirect:/usuarios"; // Si no se encuentra, redirigir a la lista
    }

    @PostMapping("/usuarios/actualizar")
    public String saveUpdatedUser(@ModelAttribute Persona persona, Model model) {
        if (persona.getId() != null) {
            // Buscar la persona existente por su ID
            Persona personaExistente = personaRepo.findById(persona.getId()).orElse(null);

            if (personaExistente != null) {
                // Si la persona existe, actualizar los campos
                personaExistente.setNombre(persona.getNombre());
                personaExistente.setEmail(persona.getEmail());
                personaExistente.setImagen(persona.getImagen());

                // Guardar los cambios en la base de datos
                personaRepo.save(personaExistente);
            } else {
                model.addAttribute("error", "Persona no encontrada");
                return "redirect:/usuarios"; // o mostrar un mensaje de error
            }
        } else {
            model.addAttribute("error", "ID de persona no válido");
            return "redirect:/usuarios"; // Redirigir al listado de usuarios
        }

        // Redirigir a la lista de usuarios después de la actualización
        return "redirect:/usuarios";
    }
}
