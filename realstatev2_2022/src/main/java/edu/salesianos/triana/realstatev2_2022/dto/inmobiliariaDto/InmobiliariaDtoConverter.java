package edu.salesianos.triana.realstatev2_2022.dto.inmobiliariaDto;


import edu.salesianos.triana.realstatev2_2022.model.Inmobiliaria;
import org.springframework.stereotype.Component;

@Component
public class InmobiliariaDtoConverter {

    public Inmobiliaria createInmobiliariaDtoToInmobiliaria(CreateInmobiliariaDto createInmobiliariaDto){

        return Inmobiliaria.builder()
                .nombre(createInmobiliariaDto.getNombre())
                .email(createInmobiliariaDto.getEmail())
                .telefono(createInmobiliariaDto.getTelefono())
                .build();
    }

    public GetInmobiliariaDto inmobiliariaToGetInmobiliariaDto(Inmobiliaria inmobiliaria){

        return GetInmobiliariaDto
                .builder()
                .id(inmobiliaria.getId())
                .nombre(inmobiliaria.getNombre())
                .telefono(inmobiliaria.getTelefono())
                .email(inmobiliaria.getEmail())
                .build();

    }
}
