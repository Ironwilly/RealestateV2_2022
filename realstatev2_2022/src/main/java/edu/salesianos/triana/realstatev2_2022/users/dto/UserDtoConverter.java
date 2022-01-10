package edu.salesianos.triana.realstatev2_2022.users.dto;

import edu.salesianos.triana.realstatev2_2022.users.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter {

    public GetUserDto userToGetUserDto(User user){

        return GetUserDto.builder()
                .id(user.getId())
                .nombre(user.getNombre())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .role(user.getRole())
                .build();
    }

    public GetUserPropietarioDto userToGetUserPropietario(User user){

        return GetUserPropietarioDto.builder()
                .id(user.getId())
                .nombre(user.getNombre())
                .apellidos(user.getApellidos())
                .direccion(user.getDireccion())
                .email(user.getEmail())
                .telefono(user.getTelefono())
                .build();

    }

    public CreateUserGestorDto userToCreateGestorDto(User user){

        return CreateUserGestorDto.builder()
                .nombre(user.getNombre())
                .apellidos(user.getApellidos())
                .direccion(user.getDireccion())
                .email(user.getEmail())
                .telefono(user.getTelefono())
                .avatar(user.getAvatar())
                .password(user.getPassword())
                .password2(user.getPassword())
                .inmobiliariaId(user.getInmobiliaria().getId())
                .build();
    }

    public CreateUserDto userToCreateUser(User user){


        return CreateUserDto.builder()
                .nombre(user.getNombre())
                .apellidos(user.getApellidos())
                .direccion(user.getDireccion())
                .telefono(user.getTelefono())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .password(user.getPassword())
                .password2(user.getPassword())
                .build();
    }


}
