package com.BidaiAgentzia.app.Model;

import org.hibernate.annotations.Check;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class GuiaTuristico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Check(constraints = "experiencia >= 0")
    private int experiencia; // Experiencia en años

    @OneToOne
    @JoinColumn(name = "viaje_id", nullable = false)
    private Viaje viaje;

}
