package edu.utn.PracticaParcial.controller;

import edu.utn.PracticaParcial.model.Player;
import edu.utn.PracticaParcial.model.PlayerDTO;
import edu.utn.PracticaParcial.repository.PlayerNative;
import edu.utn.PracticaParcial.repository.PlayerRepository;
import edu.utn.PracticaParcial.repository.TeamRepository;
import edu.utn.PracticaParcial.service.PlayerService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

//Crud with validations and exception handling
//https://www.javaguides.net/2018/09/spring-boot-crud-rest-apis-validation-example.html

@RestController
@RequestMapping("/player")
public class PlayerController {
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    @Qualifier("ModelMapperPlayer")
    private ModelMapper modelMapper;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody @Valid Player player){
        try {
            //Having problems without manually saving team beforehand (integrity, TransientPropertyValueExeption:Not-null property references a transient value)
            if(isNull(player.getTeam().getId()))
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Team id can't be null");
            /*if(!teamRepository.findById(player.getTeam().getId()).isPresent())
                teamRepository.save(player.getTeam());*/

            playerService.addPlayer(player);
            //Validations done with javax validation
        } catch (DataIntegrityViolationException e){
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error with data integrity");
        }
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Player>> getAll(){
        try {
            List<Player> players = playerService.getAll();

            if(!players.isEmpty())
                return ResponseEntity.ok(players);
            else
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(players);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/months")
    public ResponseEntity<List<PlayerNative>> getByMonthsInTeam(){
        CompletableFuture<List<PlayerNative>> players = playerService.getByMonthsInTeam();

        return ResponseEntity.ok().body(players.join());
    }

    //Example= localhost:8080/player/?teamName="Yupanki"
    //Could also (better) be done with team id
    //  Problem: Doesn't let you have two GetMappings empty (same name)
    //  Solution: Use RequestMapping, with different params
    /*@RequestMapping(value = "", method = RequestMethod.GET, params = "teamName")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PlayerDTO>> getByTeamName(@RequestParam String teamName){
        try {
            //invoque custom query
            List<Player> players = playerRepository.getAllByTeamName(teamName);
            List<PlayerDTO> playersDto = players.stream()
                    .map(player -> convertToDto(player))
                    .collect(Collectors.toList());

            if(!players.isEmpty())
                return ResponseEntity.ok(playersDto);
            else
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(playersDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }*/

    /*@GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PlayerDTO getById(@PathVariable("id") Integer id){
        //Returning bad request if pathvariable is not an integer
        Player player = playerRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format("Player not found for id: %s", id)));

        return convertToDto(player);
    }*/

    /*@PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") Integer id){
        Player player = playerRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format("Player not found for id: %s", id)));

        playerRepository.save(player);
    }*/

    /*@DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id){
        Player player = playerRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format("Player not found for id: %s", id)));

        playerRepository.delete(player);
    }*/

    /*private PlayerDTO convertToDto(Player player){
        return modelMapper.map(player, PlayerDTO.class);
    }*/

    //Not necessary for the exam
    /*private Player convertToEntity(PlayerDTO playerDTO) throws ParseException{
        return modelMapper.map(playerDTO, Player.class);
    }*/
}
