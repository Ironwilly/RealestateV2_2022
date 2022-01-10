package edu.salesianos.triana.realstatev2_2022.dto.viviendaDto;


import edu.salesianos.triana.realstatev2_2022.model.Vivienda;
import edu.salesianos.triana.realstatev2_2022.users.model.User;
import org.springframework.stereotype.Component;

@Component
public class ViviendaDtoConverter {

    public Vivienda createViviendaDtoToVivienda(CreateViviendaDto createViviendaDto){

        return Vivienda.builder()
                .titulo(createViviendaDto.getTitulo())
                .descripcion(createViviendaDto.getDescripcion())
                .avatar(createViviendaDto.getAvatar())
                .latlng(createViviendaDto.getLatlng())
                .direccion(createViviendaDto.getDireccion())
                .poblacion(createViviendaDto.getPoblacion())
                .provincia(createViviendaDto.getProvincia())
                .tipo(createViviendaDto.getTipo())
                .precio(createViviendaDto.getPrecio())
                .numHabitaciones(createViviendaDto.getNumHabitaciones())
                .numBanios(createViviendaDto.getNumBanios())
                .tienePiscina(createViviendaDto.isTienePiscina())
                .tieneAscensor(createViviendaDto.isTieneAscensor())
                .tieneGaraje(createViviendaDto.isTieneGaraje())
                .build();
    }

    public GetViviendaDto viviendaToGetViviendaDto(Vivienda vivienda){

        return GetViviendaDto.builder()
                .id(vivienda.getId())
                .titulo(vivienda.getTitulo())
                .descripcion(vivienda.getDescripcion())
                .avatar(vivienda.getAvatar())
                .latlng(vivienda.getLatlng())
                .direccion(vivienda.getDireccion())
                .poblacion(vivienda.getPoblacion())
                .provincia(vivienda.getProvincia())
                .tipo(vivienda.getTipo())
                .precio(vivienda.getPrecio())
                .numHabitaciones(vivienda.getNumHabitaciones())
                .metrosCuadrados(vivienda.getMetrosCuadrados())
                .numBanios(vivienda.getNumBanios())
                .tienePiscina(vivienda.isTienePiscina())
                .tieneAscensor(vivienda.isTieneAscensor())
                .tieneGaraje(vivienda.isTieneGaraje())
                .build();



    }

    public GetViviendaPropietarioDto viviendaToGetViviendaPropietarioDto(Vivienda vivienda, User user){

        return GetViviendaPropietarioDto.builder()

                .titulo(vivienda.getTitulo())
                .descripcion(vivienda.getDescripcion())
                .avatar(vivienda.getAvatar())
                .latlng(vivienda.getLatlng())
                .direccion(vivienda.getDireccion())
                .poblacion(vivienda.getPoblacion())
                .provincia(vivienda.getProvincia())
                .tipo(vivienda.getTipo())
                .precio(vivienda.getPrecio())
                .numHabitaciones(vivienda.getNumHabitaciones())
                .metrosCuadrados(vivienda.getMetrosCuadrados())
                .numBanios(vivienda.getNumBanios())
                .tienePiscina(vivienda.isTienePiscina())
                .tieneAscensor(vivienda.isTieneAscensor())
                .tieneGaraje(vivienda.isTieneGaraje())
                .build();


    }


}

