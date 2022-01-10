package edu.salesianos.triana.realstatev2_2022.dto.viviendaDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateViviendaDto {


    private String titulo;

    private String descripcion;

    private String avatar;

    private String latlng;

    private String direccion;

    private String poblacion;

    private String provincia;

    private String tipo;

    private double precio;

    private int numHabitaciones;

    private double metrosCuadrados;

    private int numBanios;

    private boolean tienePiscina;

    private boolean tieneAscensor;

    private boolean tieneGaraje;
}
