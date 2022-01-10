package edu.salesianos.triana.realstatev2_2022.dto.inmobiliariaDto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetInmobiliariaDto {

    private Long id;

    private String nombre;

    private String email;

    private String telefono;


}
