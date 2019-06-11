package edu.utn.PracticaParcial.repository;

import edu.utn.PracticaParcial.inteface.CantJugxE;
import edu.utn.PracticaParcial.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {
    @Query(value = "SELECT * FROM teams WHERE name = ?1", nativeQuery = true)
    Team findByName(String name);

    @Query(value = "SELECT t.name, count(j.id) " +
            "FROM teams t " +
            "INNER JOIN players j " +
            "ON t.id = p.ID_TEAM " +
            "GROUP BY t.name", nativeQuery = true)
    List<CantJugxE> getAllWithPlayerQuantity();
}
