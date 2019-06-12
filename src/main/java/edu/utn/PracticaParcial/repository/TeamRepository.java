package edu.utn.PracticaParcial.repository;

import edu.utn.PracticaParcial.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {
    @Query(value = "SELECT * FROM teams WHERE name = ?1", nativeQuery = true)
    Team findByName(String name);

    @Query(value = "SELECT t.name, count(p.id) as quantity " +
            "FROM teams t " +
            "LEFT JOIN players p " +
            "ON t.id = p.id_team " +
            "GROUP BY t.name", nativeQuery = true)
    List<PlayersPerTeam> getAllWithPlayerQuantity();
}
