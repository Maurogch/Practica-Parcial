package edu.utn.PracticaParcial.repository;

import edu.utn.PracticaParcial.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {
    @Query(value = "SELECT * FROM players p INNER JOIN teams t ON p.idTeam = t.id WHERE t.name = ?1", nativeQuery = true)
    List<Player> getAllByTeamName (String teamName);

    @Query(value = "select name, age, timestampdiff(month,registered_date,curdate()) as MonthsInTeam " +
            "from players " +
            "where age > 20", nativeQuery = true)
    List<PlayerNative> getAllWithMonthsInTeam();


}
