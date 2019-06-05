package edu.utn.PracticaParcial.repository;

import edu.utn.PracticaParcial.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {
    @Query(value = "SELECT * FROM Team WHERE name = ?1", nativeQuery = true)
    Team findByName(String name);
}
