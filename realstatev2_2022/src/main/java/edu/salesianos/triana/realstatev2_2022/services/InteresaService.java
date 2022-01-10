package edu.salesianos.triana.realstatev2_2022.services;

import edu.salesianos.triana.realstatev2_2022.dto.viviendaDto.GetViviendaDto;
import edu.salesianos.triana.realstatev2_2022.dto.viviendaDto.ViviendaDtoConverter;
import edu.salesianos.triana.realstatev2_2022.model.Interesa;
import edu.salesianos.triana.realstatev2_2022.model.Vivienda;
import edu.salesianos.triana.realstatev2_2022.repositorios.InteresaRepository;
import edu.salesianos.triana.realstatev2_2022.services.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class InteresaService extends BaseService<Interesa,Long, InteresaRepository> {

    private final ViviendaDtoConverter viviendaDtoConverter;

    public List<Vivienda> top10Vivienda(){
        return repositorio.top10ViviendasInteresa();
    }

    public List<GetViviendaDto> topViviendaDto(){
        List<GetViviendaDto> listadoViviendas = new ArrayList<>();

        top10Vivienda().stream().forEach(vivienda -> {
            listadoViviendas.add(viviendaDtoConverter.viviendaToGetViviendaDto(vivienda));

        });

        return listadoViviendas;
    }
}
