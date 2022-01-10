package edu.salesianos.triana.realstatev2_2022.users.repositorios;

import edu.salesianos.triana.realstatev2_2022.users.model.User;
import edu.salesianos.triana.realstatev2_2022.users.model.UsersRoles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findByRole (UsersRoles usersRoles);

    Optional<User> findFirstByEmail(String email);

    Optional<User> findById(UUID id);
}
