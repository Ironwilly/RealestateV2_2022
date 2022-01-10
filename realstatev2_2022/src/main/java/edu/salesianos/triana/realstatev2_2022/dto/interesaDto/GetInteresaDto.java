package edu.salesianos.triana.realstatev2_2022.dto.interesaDto;


import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetInteresaDto {

    private UUID interesadoId;
    private Long viviendaId;
    private String mensaje;
    private LocalDate createdDate;
}
