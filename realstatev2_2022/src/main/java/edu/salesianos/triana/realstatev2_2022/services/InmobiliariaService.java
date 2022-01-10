package edu.salesianos.triana.realstatev2_2022.services;


import edu.salesianos.triana.realstatev2_2022.dto.inmobiliariaDto.GetInmobiliariaDto;
import edu.salesianos.triana.realstatev2_2022.dto.inmobiliariaDto.InmobiliariaDtoConverter;
import edu.salesianos.triana.realstatev2_2022.model.Inmobiliaria;
import edu.salesianos.triana.realstatev2_2022.repositorios.InmobiliariaRepository;
import edu.salesianos.triana.realstatev2_2022.services.base.BaseService;
import edu.salesianos.triana.realstatev2_2022.users.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class InmobiliariaService extends BaseService<Inmobiliaria,Long, InmobiliariaRepository> {


    private final InmobiliariaDtoConverter inmobiliariaDtoConverter;

    public boolean gestorTieneInmobiliaria(User g, Inmobiliaria inmobiliaria) {

        boolean tiene = false;

        for (User gestor : inmobiliaria.getGestores()) {
            if (gestor.getId().equals(gestor.getId()))
                tiene = true;
        }

        return tiene;
    }

    public List<GetInmobiliariaDto> listInmobiliariaToListGetInmobiliariaDto(List<Inmobiliaria> inmobiliarias) {

        List<GetInmobiliariaDto> getInmobiliariaDtoList = new ArrayList<>();

        inmobiliarias.stream().forEach(i -> {
            getInmobiliariaDtoList.add(inmobiliariaDtoConverter.inmobiliariaToGetInmobiliariaDto(i));


        });
        return getInmobiliariaDtoList;
    }

}