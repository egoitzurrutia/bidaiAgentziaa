package com.BidaiAgentzia.app.Model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class InformacionAdicional {

    private String recomendaciones;

    private String restricciones;

}
