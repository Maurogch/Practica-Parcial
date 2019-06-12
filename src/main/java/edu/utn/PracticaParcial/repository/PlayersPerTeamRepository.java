package edu.utn.PracticaParcial.repository;

import edu.utn.PracticaParcial.model.PlayersPerTeamDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayersPerTeamRepository extends JpaRepository<PlayersPerTeamDTO, String> {
    @Query(value = "select t.name, count(p.id) as quantity " +
            "from teams t " +
            "left join players p " +
            "on t.id = p.id_team " +
            "group by t.name", nativeQuery = true)
    List<PlayersPerTeamDTO> getTeamsWithPlayerQuantity();
}
