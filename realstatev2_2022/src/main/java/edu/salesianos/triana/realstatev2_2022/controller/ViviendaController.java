package edu.salesianos.triana.realstatev2_2022.controller;


import edu.salesianos.triana.realstatev2_2022.dto.viviendaDto.CreateViviendaDto;
import edu.salesianos.triana.realstatev2_2022.dto.viviendaDto.GetViviendaDto;
import edu.salesianos.triana.realstatev2_2022.dto.viviendaDto.GetViviendaPropietarioDto;
import edu.salesianos.triana.realstatev2_2022.dto.viviendaDto.ViviendaDtoConverter;
import edu.salesianos.triana.realstatev2_2022.model.Inmobiliaria;
import edu.salesianos.triana.realstatev2_2022.model.Vivienda;
import edu.salesianos.triana.realstatev2_2022.services.InmobiliariaService;
import edu.salesianos.triana.realstatev2_2022.services.VivivendaService;
import edu.salesianos.triana.realstatev2_2022.users.model.User;
import edu.salesianos.triana.realstatev2_2022.users.model.UsersRoles;
import edu.salesianos.triana.realstatev2_2022.users.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vivienda")
public class ViviendaController {

    private final VivivendaService vivivendaService;
    private final ViviendaDtoConverter viviendaDtoConverter;
    private final InmobiliariaService inmobiliariaService;




    @Operation(summary = "Crea una vivienda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha creado la vivienda",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha guardado la vivienda",
                    content = @Content),
    })

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody CreateViviendaDto createViviendaDto, @AuthenticationPrincipal User viviendaPropietario){

        if (viviendaPropietario.getRole().equals(UsersRoles.PROPIETARIO)) {

            Vivienda vivienda= viviendaDtoConverter.createViviendaDtoToVivienda(createViviendaDto);
            vivienda.addPropietario(viviendaPropietario);
            vivivendaService.save(vivienda);
            return ResponseEntity.ok(viviendaDtoConverter.viviendaToGetViviendaDto(vivienda));
        }
        else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }



    }

    @Operation(summary = "Obtiene todos las viviendas creadas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado las viviendas",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado las viviendas",
                    content = @Content),
    })

    @GetMapping("/")
    public ResponseEntity<List<GetViviendaDto>> findAll(){

        List<GetViviendaDto> listDevolver = new ArrayList<>();

        vivivendaService.findAll().stream().forEach(v->{
            listDevolver.add(viviendaDtoConverter.viviendaToGetViviendaDto(v));
        });

        return ResponseEntity.ok(listDevolver);

    }

    @Operation(summary = "Obtiene los datos de una vivienda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado la vivienda",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado la vivienda",
                    content = @Content),
    })

    @GetMapping("/{id}")
    public ResponseEntity<GetViviendaPropietarioDto> findOne(@PathVariable Long id){

        Optional<Vivienda> vivienda = vivivendaService.findById(id);

        if(vivienda.isPresent()){
            return ResponseEntity.ok(viviendaDtoConverter.viviendaToGetViviendaPropietarioDto(vivienda.get(),vivienda.get().getPropietario()));
        }
        else{
            return ResponseEntity.notFound().build();
        }


    }

    @Operation(summary = "Edita una vivienda anteriormente creada, buscando por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha editado la vivienda",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha editado la vivienda",
                    content = @Content),
    })

    @PutMapping("/{id}")
    public ResponseEntity<GetViviendaDto> edit(@PathVariable Long id ,@RequestBody CreateViviendaDto viviendaEdit, @AuthenticationPrincipal User user){

        Optional<Vivienda> viviendaAEditar = vivivendaService.findById(id);
        if(user.getRole().equals(UsersRoles.ADMIN) || viviendaAEditar.get().getPropietario().getId().equals(user.getId())) {
            if (viviendaAEditar.isPresent()) {

                viviendaAEditar.map(v -> {
                    v.setTitulo(viviendaEdit.getTitulo());
                    v.setDescripcion(viviendaEdit.getDescripcion());
                    v.setAvatar(viviendaEdit.getAvatar());
                    v.setLatlng(viviendaEdit.getLatlng());
                    v.setDireccion(viviendaEdit.getDireccion());
                    v.setPoblacion(viviendaEdit.getPoblacion());
                    v.setProvincia(viviendaEdit.getProvincia());
                    v.setTipo(viviendaEdit.getTipo());
                    v.setPrecio(viviendaEdit.getPrecio());
                    v.setNumHabitaciones(viviendaEdit.getNumHabitaciones());
                    v.setMetrosCuadrados(viviendaEdit.getMetrosCuadrados());
                    v.setNumBanios(viviendaEdit.getNumBanios());
                    v.setTienePiscina(viviendaEdit.isTienePiscina());
                    v.setTieneAscensor(viviendaEdit.isTieneAscensor());
                    v.setTieneGaraje(viviendaEdit.isTieneGaraje());
                    return v;
                });
                vivivendaService.save(viviendaAEditar.get());

                return ResponseEntity.ok(viviendaDtoConverter.viviendaToGetViviendaDto(viviendaAEditar.get()));

            } else {
                return ResponseEntity.notFound().build();
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }


    }

    @Operation(summary = "Borra una vivienda por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha borrado la vivienda",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))})
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @AuthenticationPrincipal User user){
        Optional<Vivienda> viviendaDelete = vivivendaService.findById(id);
        if(viviendaDelete.isPresent()) {
            if (viviendaDelete.get().getPropietario().getId().equals(user.getId()) || user.getRole().equals(UsersRoles.ADMIN)) {
                viviendaDelete.get().deleteInmobiliaria();
                viviendaDelete.get().deleteInmobiliaria();
                vivivendaService.delete(viviendaDelete.get());
            }
        }
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{idViv}/inmobiliaria/{idInmo}")
    public ResponseEntity<GetViviendaDto> gestionarVivienda(@PathVariable Long idViv,@PathVariable Long idInmo, @AuthenticationPrincipal User user){

        Optional<Vivienda> vivienda = vivivendaService.findById(idViv);
        Optional<Inmobiliaria> inmo = inmobiliariaService.findById(idInmo);
        if(vivienda.isPresent() && inmo.isPresent()) {
            if (user.getRole().equals(UsersRoles.ADMIN) || vivienda.get().getPropietario().getId().equals(user.getId())){
                vivienda.get().addInmobiliaria(inmo.get());
                vivivendaService.save(vivienda.get());
                return ResponseEntity.ok(viviendaDtoConverter.viviendaToGetViviendaDto(vivienda.get()));
            }
            else{
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

        }
        else{
            return ResponseEntity.notFound().build();
        }


    }

    @Operation(summary = "Borra la gestión de una vivienda por parte de la inmobiliaria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha borrado ok",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))})
    })

    @DeleteMapping("/{idViv}/inmobiliaria/")
    public ResponseEntity<?> eliminarGestionVivienda(@PathVariable Long idViv, @AuthenticationPrincipal User user) {

        Optional<Vivienda> vivienda = vivivendaService.findById(idViv);
        if(vivienda.isPresent()) {
            if (user.getRole().equals(UsersRoles.ADMIN) || vivienda.get().getPropietario().getId().equals(user.getId())){
                vivienda.get().deleteInmobiliaria();
                vivivendaService.save(vivienda.get());
                return ResponseEntity.noContent().build();

            }
        }

        return ResponseEntity.noContent().build();

    }


}
