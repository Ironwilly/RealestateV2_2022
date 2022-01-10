package edu.salesianos.triana.realstatev2_2022.services;


import edu.salesianos.triana.realstatev2_2022.dto.viviendaDto.GetViviendaDto;
import edu.salesianos.triana.realstatev2_2022.dto.viviendaDto.ViviendaDtoConverter;
import edu.salesianos.triana.realstatev2_2022.model.Vivienda;
import edu.salesianos.triana.realstatev2_2022.repositorios.ViviendaRepository;
import edu.salesianos.triana.realstatev2_2022.services.base.BaseService;
import edu.salesianos.triana.realstatev2_2022.users.model.User;
import edu.salesianos.triana.realstatev2_2022.users.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class VivivendaService extends BaseService<Vivienda,Long, ViviendaRepository> {

    private final ViviendaDtoConverter viviendaDtoConverter;
    private final UserService userService;

    public List<GetViviendaDto> getViviendaDtoListPropietario(User user){

        List<GetViviendaDto> listadoGetViviendas = new ArrayList<>();
        Optional<User> propietario = userService.findById(user.getId());
        if(propietario.isPresent()){
            List<Vivienda> viviendasPropietarioListado = propietario.get().getViviendas();
            listadoGetViviendas = viviendasPropietarioListado.stream().map(viviendaDtoConverter::viviendaToGetViviendaDto).collect(Collectors.toList());

        }

        return listadoGetViviendas;
    }
}
