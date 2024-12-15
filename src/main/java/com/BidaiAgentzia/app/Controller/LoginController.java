package com.BidaiAgentzia.app.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.BidaiAgentzia.app.Model.Persona;
import com.BidaiAgentzia.app.Repository.PersonaRepository;
import com.BidaiAgentzia.app.Service.PersonaService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private PersonaService personaService;
    @Autowired
    private PersonaRepository personaRepository;

    @GetMapping({"/", "","/login"})
    public String showLoginPage() {
        return "login";  // Muestra la página de login
    }

    @GetMapping("/home")
    public String home() {
        return "home"; 
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email, @RequestParam String password, Model model, HttpSession session) {
        // Llamamos al método authenticateAndGetRole para obtener el rol
        int rol = personaService.authenticateAndGetRole(email, password);

        if (rol != -1) { // Si el rol es diferente de -1, autenticación exitosa
            // Buscar persona por email para obtener el nombre
            Persona persona = personaRepository.findByEmail(email);
            
            // Guardar el nombre de la persona en la sesión
            session.setAttribute("nombre", persona.getNombre());
            session.setAttribute("rol", rol);
            session.setAttribute("userId", persona.getId());  // Guardar el ID de usuario en la sesión

            // Redirigir según el rol
            if (rol == 0) { // Rol de usuario
                return "redirect:/home";  // Página de usuario normal
            } else if (rol == 1) { // Rol de administrador
                return "redirect:/home";  // Página de administración
            } else {
                model.addAttribute("error", "Rol no válido.");
                return "login"; // Vuelve al login si el rol es inválido
            }
        } else {
            model.addAttribute("error", "Correo electrónico o contraseña incorrectos.");
            return "login";  // Si las credenciales son incorrectas, vuelve a la página de login
        } 	
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();  // Elimina todos los atributos de la sesión
        return "redirect:/login";  // Redirige al formulario de login
    }
}
