package edu.salesianos.triana.realstatev2_2022.users.dto;


import edu.salesianos.triana.realstatev2_2022.users.model.UsersRoles;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class GetUserDto {



    private UUID id;

    private String nombre;

    private String email;

    private String avatar;

    private UsersRoles role;

}
