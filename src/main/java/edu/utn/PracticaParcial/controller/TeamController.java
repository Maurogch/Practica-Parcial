package edu.utn.PracticaParcial.controller;

import edu.utn.PracticaParcial.model.PlayersPerTeamDTO;
import edu.utn.PracticaParcial.repository.PlayersPerTeam;
import edu.utn.PracticaParcial.model.Player;
import edu.utn.PracticaParcial.model.Team;
import edu.utn.PracticaParcial.repository.PlayersPerTeamRepository;
import edu.utn.PracticaParcial.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.util.List;

import static java.util.Objects.nonNull;

@RequestMapping("/team")
@RestController
public class TeamController {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private PlayersPerTeamRepository playersPerTeamRepository;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody @Valid Team team) {
        try {
            if (nonNull(teamRepository.findByName(team.getName())))
                throw new HttpClientErrorException(HttpStatus.CONFLICT, "Team with same name in existance");

            teamRepository.save(team);
        } catch (DataIntegrityViolationException e) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error with data integrity");
        }
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Team>> getAll(){
        List<Team> teams = teamRepository.findAll();

        if(!teams.isEmpty())
            return ResponseEntity.ok(teams);
        else
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(teams);
    }

    //Proof of concept only, already done with query string in player controller
    @GetMapping("/{id}/players")
    public ResponseEntity<List<Player>> getAllPlayersByTeamId(@PathVariable("id") Integer id){
        Team team = teamRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format("Team not found for id: %s", id)));

        if(!team.getPlayers().isEmpty())
            return ResponseEntity.ok(team.getPlayers());
        else
            return ResponseEntity.noContent().build();
    }

    @GetMapping("/testProjection") //Native projection
    public List<PlayersPerTeam> getAllWithCant(){
        return teamRepository.getAllWithPlayerQuantity();
    }

    @GetMapping("/playersQuantity")
    public List<PlayersPerTeamDTO> getAllWithPlayerQuantity(){
        List<PlayersPerTeamDTO> list = playersPerTeamRepository.getTeamsWithPlayerQuantity();
        list.add(new PlayersPerTeamDTO("Team added in API", 99)); // Now we can modify the data before sending it
        return list;
    }
}
