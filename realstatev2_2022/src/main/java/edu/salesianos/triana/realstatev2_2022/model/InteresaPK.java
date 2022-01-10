package edu.salesianos.triana.realstatev2_2022.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
@AllArgsConstructor
public class InteresaPK implements Serializable {

    private UUID interesado_id;

    private Long vivienda_id;
}
