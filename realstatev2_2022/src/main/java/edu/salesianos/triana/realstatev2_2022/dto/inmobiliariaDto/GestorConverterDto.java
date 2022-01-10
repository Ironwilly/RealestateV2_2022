package edu.salesianos.triana.realstatev2_2022.dto.inmobiliariaDto;

import edu.salesianos.triana.realstatev2_2022.model.Inmobiliaria;
import edu.salesianos.triana.realstatev2_2022.users.model.User;
import edu.salesianos.triana.realstatev2_2022.users.model.UsersRoles;
import org.springframework.stereotype.Component;

@Component
public class GestorConverterDto {

    public User createGestorInmobiliariaDto(CreateGestorInmobiliariaDto gestor, Inmobiliaria inmobiliaria){
        return User.builder()
                .nombre(gestor.getNombre())
                .apellidos(gestor.getApellidos())
                .direccion(gestor.getDireccion())
                .email(gestor.getEmail())
                .telefono(gestor.getTelefono())
                .avatar(gestor.getAvatar())
                .password(gestor.getPassword())
                .role(UsersRoles.GESTOR)
                .inmobiliaria(inmobiliaria)
                .build();

    }
}
