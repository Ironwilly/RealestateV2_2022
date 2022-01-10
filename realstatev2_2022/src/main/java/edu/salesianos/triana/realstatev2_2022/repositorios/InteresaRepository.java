package edu.salesianos.triana.realstatev2_2022.repositorios;

import edu.salesianos.triana.realstatev2_2022.model.Interesa;
import edu.salesianos.triana.realstatev2_2022.model.Vivienda;
import edu.salesianos.triana.realstatev2_2022.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InteresaRepository extends JpaRepository<Interesa,Long> {

@Query(value = """
        
        SELECT * FROM User user
        WHERE user.id IN (SELECT interesado_id FROM Interesa interesa
        GROUP BY interesado_id);
        """, nativeQuery = true)
    List<User> findInteresado();

@Query(value = """
        SELECT * FROM Vivienda vivienda
        WHERE vivienda.id IN (SELECT vivienda.id
            FROM Vivienda vivienda JOIN Interesa interesa 
            ON vivienda.id=interesa.vivienda_id
            GROUP BY vivienda.id
            ORDER BY COUNT(*) DESC
            LIMIT 10);
        """,nativeQuery = true)
    List<Vivienda> top10ViviendasInteresa();

}
