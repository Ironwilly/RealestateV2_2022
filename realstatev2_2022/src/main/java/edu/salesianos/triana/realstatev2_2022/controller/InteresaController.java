package edu.salesianos.triana.realstatev2_2022.controller;


import edu.salesianos.triana.realstatev2_2022.dto.interesaDto.CreateInteresaDto;
import edu.salesianos.triana.realstatev2_2022.dto.interesaDto.GetInteresaDto;
import edu.salesianos.triana.realstatev2_2022.dto.interesaDto.InteresaDtoConverter;
import edu.salesianos.triana.realstatev2_2022.dto.viviendaDto.GetViviendaDto;
import edu.salesianos.triana.realstatev2_2022.model.Inmobiliaria;
import edu.salesianos.triana.realstatev2_2022.model.Interesa;
import edu.salesianos.triana.realstatev2_2022.model.Vivienda;
import edu.salesianos.triana.realstatev2_2022.services.InteresaService;
import edu.salesianos.triana.realstatev2_2022.services.VivivendaService;
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
public class InteresaController {

    private final VivivendaService vivivendaService;
    private final UserDtoConverter userDtoConverter;
    private final UserService userService;
    private final InteresaDtoConverter interesaDtoConverter;
    private final InteresaService interesaService;


    @Operation(summary = "Se añade interés por una vivienda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Interés ok",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha añadido",
                    content = @Content),
    })
    @PostMapping("vivienda/{id}/meinteresa")
    public ResponseEntity<GetInteresaDto> createInteresa (@RequestBody CreateInteresaDto interesaDto, @PathVariable Long id, @AuthenticationPrincipal User userLogged){

        Optional<Vivienda> vivienda=vivivendaService.findById(id);
        if(vivienda.isPresent()) {
            if (userLogged.getRole().equals(UsersRoles.PROPIETARIO)) {
                Interesa interesa = interesaDtoConverter.createInteresaDtoToInteresa(interesaDto, userLogged, vivienda.get());

                interesa.addInteresado(userLogged);
                interesa.addVivienda(vivienda.get());

                interesaService.save(interesa);
                userLogged.getInteresa().add(interesa);
                userService.save(userLogged);
                return ResponseEntity
                        .status(HttpStatus.CREATED).body(interesaDtoConverter.interesaToGetInteresaDto(interesa));
            }
            else{
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Borra el interés de un interesado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha borrado el interés",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))})
    })

    @DeleteMapping("vivienda/{id}/meinteresa")
    public ResponseEntity<?> deleteInteresa(@PathVariable Long id,@AuthenticationPrincipal User user){

        Optional<Vivienda> vivienda = vivivendaService.findById(id);

        if(vivienda.isPresent()){

            List<Interesa> listaIntereses = vivienda.get().getInteresaList();
            for (Interesa inte : listaIntereses) {
                if (inte.getInteresado().getId().equals(user.getId())) {
                    interesaService.delete(inte);
                }
            }

        }

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtiene una datos de todos los interesados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado ",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se han encontrado ",
                    content = @Content),
    })

    @GetMapping("/interesado/")
    public ResponseEntity<List<GetUserDto>> getInteresados(){

        List<User> interesados = userService.findProps();

        return ResponseEntity.ok().body(userService.listUserToListGetUserDto(interesados));
    }

    @Operation(summary = "Obtiene una datos de un interesado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado ",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se han encontrado ",
                    content = @Content),
    })

    @GetMapping("/interesado/{id}")
    public ResponseEntity<GetUserDto> findOne(@PathVariable UUID id, @AuthenticationPrincipal User user){

        Optional<User> user2 = userService.findById(id);

        if(user2.isPresent()){
            if(user.getId().equals(user2.get().getId()) || user.getRole().equals(UsersRoles.ADMIN)){
                return ResponseEntity.ok(userDtoConverter.userToGetUserDto(user2.get()));
            }
            else
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Obtiene las 10 viviendas con más interesados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado ",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se han encontrado",
                    content = @Content),
    })

    @GetMapping("/vivienda/top10")
    public ResponseEntity<List<GetViviendaDto>> viviendasConMasInteresas(){
        return ResponseEntity.ok(interesaService.topViviendaDto());
    }



}
