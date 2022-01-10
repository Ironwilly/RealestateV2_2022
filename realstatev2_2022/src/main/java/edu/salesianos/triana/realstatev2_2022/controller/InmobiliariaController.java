package edu.salesianos.triana.realstatev2_2022.controller;


import edu.salesianos.triana.realstatev2_2022.dto.inmobiliariaDto.*;
import edu.salesianos.triana.realstatev2_2022.model.Inmobiliaria;
import edu.salesianos.triana.realstatev2_2022.services.InmobiliariaService;
import edu.salesianos.triana.realstatev2_2022.users.dto.GetUserDto;
import edu.salesianos.triana.realstatev2_2022.users.dto.UserDtoConverter;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inmobiliaria")
@CrossOrigin
public class InmobiliariaController {

    private final UserDtoConverter userDtoConverter;
    private final UserService userService;
    private final InmobiliariaService inmobiliariaService;
    private final InmobiliariaDtoConverter inmobiliariaDtoConverter;
    private GestorConverterDto gestorConverterDto;


    @Operation(summary = "Crea una inmobiliaria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha creado la inmobiliaria",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha guardado la inmobiliaria",
                    content = @Content),
    })

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody CreateInmobiliariaDto createInmobiliariaDto, @AuthenticationPrincipal User user){

        if(user.getRole().equals(UsersRoles.ADMIN)){
            Inmobiliaria inmobiliaria=inmobiliariaDtoConverter.createInmobiliariaDtoToInmobiliaria(createInmobiliariaDto);
            inmobiliariaService.save(inmobiliaria);
            return ResponseEntity.ok(inmobiliariaDtoConverter.inmobiliariaToGetInmobiliariaDto(inmobiliaria));
        }
        else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Operation(summary = "Obtiene todas las inmobiliarias creadas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado las inmobiliarias",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado las inmobiliarias",
                    content = @Content),
    })

    @GetMapping("/")
    public ResponseEntity<List<GetInmobiliariaDto>> findAll(){

        List<GetInmobiliariaDto> listInmobiliaria = inmobiliariaService.listInmobiliariaToListGetInmobiliariaDto(inmobiliariaService.findAll());

        return ResponseEntity.ok(listInmobiliaria);
    }

    @Operation(summary = "Obtiene los gestores de una inmobiliaria creada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la inmobiliaria",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se han encontrado la inmobiliaria",
                    content = @Content),
    })

    @GetMapping("/{id}/gestor")
    public ResponseEntity<List<GetUserDto>> listarGestoresInmobiliaria(@PathVariable Long id, @AuthenticationPrincipal User user){

        Optional<Inmobiliaria> inmobiliaria = inmobiliariaService.findById(id);

        if(inmobiliaria.isPresent()) {
            if (inmobiliariaService.gestorTieneInmobiliaria(user, inmobiliaria.get()) || user.getRole().equals(UsersRoles.ADMIN)) {

                List<GetUserDto> listaGestores = userService.listUserToListGetUserDto(inmobiliaria.get().getGestores());

                return ResponseEntity.ok(listaGestores);

            }
            else{
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

            }
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }


    @Operation(summary = "Añade nuevo gestor a la inmobiliaria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha añadido correctamente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha añadido",
                    content = @Content),
    })
    @PostMapping("/{id}/gestor")
    public ResponseEntity<GetUserDto> registeGestor(@PathVariable Long id, @RequestBody CreateGestorInmobiliariaDto createGestorInmobiliariaDto, @AuthenticationPrincipal User user){
        Optional<Inmobiliaria> inmobiliaria = inmobiliariaService.findById(id);

        if(inmobiliaria.isPresent()){
            if(user.getRole().equals(UsersRoles.ADMIN) || inmobiliariaService.gestorTieneInmobiliaria(user,inmobiliaria.get())){
                User user1=gestorConverterDto.createGestorInmobiliariaDto(createGestorInmobiliariaDto,inmobiliaria.get());
                userService.save(user1);
                return ResponseEntity.status(HttpStatus.CREATED).body(userDtoConverter.userToGetUserDto(user1));
            }else{
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }
        else{
            return ResponseEntity.notFound().build();

        }

    }

    @Operation(summary = "Borra un usuario gestor de una inmobiliaria creada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha borrado correctamente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))})
    })

    @DeleteMapping("/gestor/{id}")
    public ResponseEntity<?> removeGestor(@PathVariable UUID id, @AuthenticationPrincipal User userLogged){

        Optional<User> gestor = userService.findById(id);
        Inmobiliaria inmobiliaria = userLogged.getInmobiliaria();

        if(gestor.isPresent()){
            if(userLogged.getRole().equals(UsersRoles.ADMIN) || inmobiliariaService.gestorTieneInmobiliaria(gestor.get(),inmobiliaria)){
                userService.deleteById(id);
            }
        }

        return ResponseEntity.noContent().build();

    }

    @Operation(summary = "Borra una inmobiliaria creada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha borrado la inmobiliaria",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))})
    })


    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeInmo(@PathVariable Long id, @AuthenticationPrincipal User userLogged){
        Optional<Inmobiliaria> inmobiliaria = inmobiliariaService.findById(id);
        if(inmobiliaria.isPresent()){
            if(userLogged.getRole().equals(UsersRoles.ADMIN)) {
                inmobiliaria.get().removeInmoFromViviendas();
                inmobiliariaService.deleteById(id);
            }
        }
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtiene una inmobiliaria creada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la inmobiliaria",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se han encontrado la inmobiliaria",
                    content = @Content),
    })

    @GetMapping("/{id}")
    public ResponseEntity<GetInmobiliariaDto> findOne(@PathVariable Long id, @AuthenticationPrincipal User user){
        Optional<Inmobiliaria> inmo = inmobiliariaService.findById(id);
        if(inmo.isPresent())
            return ResponseEntity.ok(inmobiliariaDtoConverter.inmobiliariaToGetInmobiliariaDto(inmo.get()));
        else
            return ResponseEntity.notFound().build();
    }

}
