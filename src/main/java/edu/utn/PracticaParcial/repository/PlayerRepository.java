package edu.utn.PracticaParcial.repository;

import edu.utn.PracticaParcial.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {
    @Query(value = "SELECT * FROM Player p INNER JOIN Team t ON p.id_equipo = t.id WHERE t.name = ?1", nativeQuery = true)
    List<Player> getAllByTeamName (String teamName);
}
