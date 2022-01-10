package edu.salesianos.triana.realstatev2_2022.dto.interesaDto;


import edu.salesianos.triana.realstatev2_2022.model.Interesa;
import edu.salesianos.triana.realstatev2_2022.model.Vivienda;
import edu.salesianos.triana.realstatev2_2022.users.model.User;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;

@Component
public class InteresaDtoConverter {

    public Interesa createInteresaDtoToInteresa(CreateInteresaDto createInteresaDto, User user, Vivienda vivienda){

        return Interesa.builder()
                .interesado(user)
                .vivienda(vivienda)
                .mensaje(createInteresaDto.getMensaje())
                .createDate(LocalDate.now())
                .build();
    }

    public GetInteresaDto interesaToGetInteresaDto(Interesa interesa){

        return GetInteresaDto.builder()
                .interesadoId(interesa.getInteresado().getId())
                .viviendaId((interesa.getVivienda().getId()))
                .mensaje(interesa.getMensaje())
                .createdDate(interesa.getCreateDate())
                .build();
    }


}
