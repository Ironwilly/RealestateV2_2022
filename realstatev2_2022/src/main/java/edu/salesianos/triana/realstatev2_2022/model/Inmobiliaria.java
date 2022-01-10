package edu.salesianos.triana.realstatev2_2022.model;


import edu.salesianos.triana.realstatev2_2022.users.model.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Inmobiliaria {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;

    private String email;
    private String telefono;

    @Builder.Default
    @OneToMany(mappedBy = "inmobiliaria")
    private List<Vivienda> viviendas = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "inmobiliaria", fetch = FetchType.EAGER)
    private List<User> gestores = new ArrayList<>();

    public Inmobiliaria(String nombre, String email, String telefono) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }

    public void addGestor(User gestor) {
        this.getGestores().add(gestor);
        gestor.setInmobiliaria(this);
    }

    public void removeInmoFromViviendas() {
        viviendas.forEach(v ->
                v.setInmobiliaria(null));
    }



    public void removeGestor(User gestor) {
        gestor.setInmobiliaria(null);
        this.getGestores().add(gestor);
    }

}
