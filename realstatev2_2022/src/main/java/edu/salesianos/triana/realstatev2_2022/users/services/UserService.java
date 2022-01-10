package edu.salesianos.triana.realstatev2_2022.users.services;


import edu.salesianos.triana.realstatev2_2022.model.Inmobiliaria;
import edu.salesianos.triana.realstatev2_2022.services.InmobiliariaService;
import edu.salesianos.triana.realstatev2_2022.services.base.BaseService;
import edu.salesianos.triana.realstatev2_2022.users.dto.CreateUserDto;
import edu.salesianos.triana.realstatev2_2022.users.dto.CreateUserGestorDto;
import edu.salesianos.triana.realstatev2_2022.users.dto.GetUserDto;
import edu.salesianos.triana.realstatev2_2022.users.dto.UserDtoConverter;
import edu.salesianos.triana.realstatev2_2022.users.model.User;
import edu.salesianos.triana.realstatev2_2022.users.model.UsersRoles;
import edu.salesianos.triana.realstatev2_2022.users.repositorios.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service("userDetailService")
public class UserService extends BaseService<User, UUID, UserRepository> implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserDtoConverter userDtoConverter;
    private final InmobiliariaService inmobiliariaService;



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        return this.repositorio.findFirstByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(email+ "no se encuentra"));
    }

    public User savePropietario(CreateUserDto createUserDto){

        if (createUserDto.getPassword().contentEquals(createUserDto.getPassword2())) {
            User user = User.builder()
                    .nombre(createUserDto.getNombre())
                    .apellidos(createUserDto.getApellidos())
                    .direccion(createUserDto.getDireccion())
                    .email(createUserDto.getEmail())
                    .telefono(createUserDto.getTelefono())
                    .avatar(createUserDto.getAvatar())
                    .password(passwordEncoder.encode(createUserDto.getPassword()))
                    .role(UsersRoles.PROPIETARIO)
                    .build();

            return save(user);
        }else{
            return null;
        }


    }

    public User saveGestor(CreateUserGestorDto createUserGestorDto){

        if (createUserGestorDto.getPassword().contentEquals(createUserGestorDto.getPassword2())) {


            User user = User.builder()
                    .nombre(createUserGestorDto.getNombre())
                    .apellidos(createUserGestorDto.getApellidos())
                    .direccion(createUserGestorDto.getDireccion())
                    .email(createUserGestorDto.getEmail())
                    .telefono(createUserGestorDto.getTelefono())
                    .avatar(createUserGestorDto.getAvatar())
                    .password(passwordEncoder.encode(createUserGestorDto.getPassword()))
                    .role(UsersRoles.GESTOR)
                    .inmobiliaria(null)
                    .build();

            Optional<Inmobiliaria> inmobiliaria = inmobiliariaService.findById(createUserGestorDto.getInmobiliariaId());

            if(inmobiliaria.isPresent())
                user.addInmobiliaria(inmobiliaria.get());

            return save(user);
        }else{
            return null;
        }



    }

    public List<User> findProps() throws UsernameNotFoundException {
        return this.repositorio.findByRole(UsersRoles.PROPIETARIO);
    }


    public User saveAdmin(CreateUserDto createUserDto){

        if (createUserDto.getPassword().contentEquals(createUserDto.getPassword2())) {
            User user = User.builder()
                    .nombre(createUserDto.getNombre())
                    .apellidos(createUserDto.getApellidos())
                    .direccion(createUserDto.getDireccion())
                    .email(createUserDto.getEmail())
                    .telefono(createUserDto.getTelefono())
                    .avatar(createUserDto.getAvatar())
                    .password(passwordEncoder.encode(createUserDto.getPassword()))
                    .role(UsersRoles.ADMIN)
                    .build();
            return save(user);
        }else{
            return null;
        }

    }

    public List<GetUserDto> listUserToListGetUserDto(List<User> users){
        List<GetUserDto> getUserDtos = new ArrayList<>();
        users.stream().forEach(u -> {
            getUserDtos.add(userDtoConverter.userToGetUserDto(u));
        });
        return getUserDtos;
    }





}
