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
public class Vivienda {


    @Id @GeneratedValue
    private Long id;

    @Lob
    private String titulo;
    @Lob
    private String descripcion;

    private String avatar;

    private String latlng;

    private String direccion;

    private String codigoPostal;

    private String poblacion;

    private String provincia;

    private String tipo;

    private double precio;

    private int numHabitaciones;

    private int numBanios;

    private double metrosCuadrados;

    private boolean tienePiscina;

    private boolean tieneAscensor;

    private boolean tieneGaraje;


    @ManyToOne
    @JoinColumn(name = "propietario_id", foreignKey = @ForeignKey(name = "FK_VIVIENDA_USUARIO"))
    private User propietario;

    @ManyToOne
    @JoinColumn(name = "inmobiliaria_id", foreignKey = @ForeignKey(name = "FK_INMOBILIARIA_USUARIO"))
    private Inmobiliaria inmobiliaria;

    @OneToMany
    @Builder.Default
    private List<Interesa> interesaList = new ArrayList<>();

    public Vivienda(String titulo, String descripcion, String avatar, String latlng, String direccion, String codigoPostal, String poblacion, String provincia, String tipo, double precio, int numHabitaciones, int numBanios, double metrosCuadrados, boolean tienePiscina, boolean tieneAscensor, boolean tieneGaraje) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.avatar = avatar;
        this.latlng = latlng;
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;
        this.poblacion = poblacion;
        this.provincia = provincia;
        this.tipo = tipo;
        this.precio = precio;
        this.numHabitaciones = numHabitaciones;
        this.numBanios = numBanios;
        this.metrosCuadrados = metrosCuadrados;
        this.tienePiscina = tienePiscina;
        this.tieneAscensor = tieneAscensor;
        this.tieneGaraje = tieneGaraje;
        this.propietario = propietario;
        this.inmobiliaria = inmobiliaria;
        this.interesaList = interesaList;
    }

    public void addInmobiliaria(Inmobiliaria inmobili){
        inmobiliaria = inmobili;

        if(inmobili.getViviendas() == null){
            inmobili.setViviendas(new ArrayList<>());
            inmobili.getViviendas().add(this);
        } else {
            inmobili.getViviendas().add(this);
        }

    }

    public void deleteInmobiliaria(){
        if(this.inmobiliaria != null)
            this.inmobiliaria.getViviendas().remove(this);
        this.setInmobiliaria(null);
    }

    public void addPropietario(User u){
        this.propietario = u;
        if(u.getViviendas() == null){
            u.setViviendas(new ArrayList());
            u.getViviendas().add(this);
        }else{
            u.getViviendas().add(this);
        }
    }

    public void deletePropietario(){
        if(this.propietario != null)
            this.propietario.getViviendas().remove(this);
        this.setPropietario(null);
    }
}
