package com.BidaiAgentzia.app.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Viaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String destino;

    private int duracion; // Duración en días

    @OneToOne(mappedBy = "viaje", cascade = CascadeType.ALL, orphanRemoval = true)
    private GuiaTuristico guiaTuristico;

    @OneToMany(mappedBy = "viaje", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservas;

    // Relación ManyToMany con Persona
    @ManyToMany
    @JoinTable(
            name = "viaje_cliente",
            joinColumns = @JoinColumn(name = "viaje_id"),
            inverseJoinColumns = @JoinColumn(name = "persona_id")
    )
    private Set<Persona> personas;

    @Embedded
    private InformacionAdicional infoAdicional;

    @ElementCollection
    @CollectionTable(name = "viaje_comentarios", joinColumns = @JoinColumn(name = "viaje_id"))
    @Column(name = "comentario")
    private List<String> comentarios;
}
