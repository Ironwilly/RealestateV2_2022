package edu.salesianos.triana.realstatev2_2022.users.dto;


import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GetUserPropietarioDto {



    private UUID id;

    private String nombre;

    private String apellidos;

    private String direccion;

    private String email;

    private String telefono;
}
