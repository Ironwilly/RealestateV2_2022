package edu.salesianos.triana.realstatev2_2022.dto.inmobiliariaDto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class CreateGestorInmobiliariaDto {


    private String nombre;

    private String apellidos;

    private String direccion;

    private String email;

    private String telefono;

    private String avatar;

    private String password;

    private String password2;


}
