package edu.salesianos.triana.realstatev2_2022.dto.viviendaDto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetViviendaPropietarioDto {



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

    private String nombrePropietario;

    private String apellidosPropietario;

    private String telefonoPropietario;

    private String emailPropietario;
}
