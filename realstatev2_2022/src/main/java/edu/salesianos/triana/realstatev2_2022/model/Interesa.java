package edu.salesianos.triana.realstatev2_2022.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.salesianos.triana.realstatev2_2022.users.model.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class Interesa implements Serializable {

    @CreatedDate
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate createDate = LocalDate.now();

    @Lob
    private String mensaje;

    @EmbeddedId
    @Builder.Default
    private  InteresaPK id = new InteresaPK();

    @ManyToOne
    @JoinColumn(name = "vivienda_id")
    @MapsId("vivienda_id")
    private Vivienda vivienda;

    @ManyToOne
    @JoinColumn(name = "interesado_id")
    @MapsId("interesado_id")
    private User interesado;


    //

    public void addVivienda(Vivienda nuevaVivienda){
        this.vivienda = nuevaVivienda;
        nuevaVivienda.getInteresaList().add(this);
    }

    public void addInteresado(User nuevoInteresado){
        this.interesado=nuevoInteresado;
        nuevoInteresado.getInteresa().add(this);
    }




}
