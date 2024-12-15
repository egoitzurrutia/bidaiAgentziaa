package com.BidaiAgentzia.app.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Entity
@Getter
@Setter
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
    private String contraseña;
    private int rol; // Si es 0 es user, si es 1 es admin
    private String imagen;

    // Relación ManyToMany con Viaje
    @ManyToMany(mappedBy = "personas", cascade = CascadeType.ALL)
    private Set<Viaje> viajes;

    public void removeViaje(Viaje viaje) {
        // Elimina la relación de la persona con el viaje
        this.viajes.remove(viaje);
        // También puedes eliminar la relación de "persona" del viaje
        viaje.getPersonas().remove(this);
    }
}
