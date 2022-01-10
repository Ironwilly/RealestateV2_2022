package edu.salesianos.triana.realstatev2_2022.users.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CreateUserDto {

    private String nombre;

    private String apellidos;

    private String direccion;

    private String email;

    private String telefono;

    private String avatar;

    private String password;

    private String password2;
}
