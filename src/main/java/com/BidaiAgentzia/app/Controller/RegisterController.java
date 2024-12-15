package com.BidaiAgentzia.app.Controller;

import com.BidaiAgentzia.app.Model.Persona;
import com.BidaiAgentzia.app.Repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class RegisterController {

    @Autowired
    private PersonaRepository personaRepository;
    
    // Ruta para el formulario de registro
    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";  // Tu vista de registro (Thymeleaf)
    }

    // Ruta para manejar el registro
    @PostMapping("/register")
    public String registerUser(@RequestParam String nombre, 
                               @RequestParam String email, 
                               @RequestParam String contraseña, 
                               @RequestParam("imagen") MultipartFile imagen,
                               Model model) throws IOException {
        
        // Verificar si el correo electrónico ya existe
        if (personaRepository.existsByEmail(email)) {
            model.addAttribute("error", "Este correo electrónico ya está registrado. Intenta con otro.");
            return "register";  // Regresar al formulario con el mensaje de error
        }
        
        // Ruta para guardar la imagen en el directorio 'static/images'
        String directoryPath = System.getProperty("user.dir") + "/src/main/resources/static/images/";
        Path uploadPath = Paths.get(directoryPath);

        // Verificar si el directorio existe, y si no, crear
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Guardar el archivo en la ruta deseada
        if (!imagen.isEmpty()) {
            String fileName = imagen.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);

            // Guardar el archivo en el sistema de archivos
            imagen.transferTo(filePath.toFile());
        }
     
        // Crear la persona y guardarla en la base de datos
        Persona persona = new Persona();
        persona.setNombre(nombre);
        persona.setEmail(email);
        persona.setContraseña(contraseña);  // Recuerda que este es un ejemplo sin encriptación
        persona.setImagen("/images/" + imagen.getOriginalFilename()); // Ruta relativa de la imagen

        // Guardar la persona en la base de datos
        personaRepository.save(persona);

        model.addAttribute("success", "Registro exitoso!");

        // Redirigir o mostrar mensaje de éxito
        return "login"; // O redirigir a la vista de login
    }
}
