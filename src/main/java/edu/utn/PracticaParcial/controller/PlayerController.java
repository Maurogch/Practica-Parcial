package edu.utn.PracticaParcial.controller;

import edu.utn.PracticaParcial.model.Player;
import edu.utn.PracticaParcial.model.PlayerDTO;
import edu.utn.PracticaParcial.repository.PlayerRepository;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;


@RestController
@RequestMapping("/player")
public class PlayerController {
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    @Qualifier("ModelMapperPlayer")
    private ModelMapper modelMapper;

    /*Post non DTO
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody @Valid Player player){
        playerRepository.save(player);
    }*/

    @GetMapping("/header")
    public String getUserAgent(@RequestHeader("User-Agent") String userAgent) {
        return userAgent;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody @Valid PlayerDTO playerDTO){
        try {
            Player player = convertToEntity(playerDTO);
            playerRepository.save(player);
        } catch (ParseException e) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error transforming DTO");
        } catch (DataIntegrityViolationException e){
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error with data integrity");
        }
    }

    /*Get non DTO
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<Player> getAll(){
        List<Player> players = playerRepository.findAll();

        if(!players.isEmpty())
            return players;
        else
            throw new HttpClientErrorException(HttpStatus.NO_CONTENT);
    }*/

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<PlayerDTO> getAll(){
        List<Player> players = playerRepository.findAll();

        if(!players.isEmpty())
            return players.stream()
                .map(player -> convertToDto(player))
                .collect(Collectors.toList());
        else
            throw new HttpClientErrorException(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PlayerDTO getById(@PathVariable("id") Integer id){
        Player player = playerRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Player not found"));

        return convertToDto(player);
    }

    @PutMapping("")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void update(PlayerDTO playerDTO){
        try {
            Player player = convertToEntity(playerDTO);
            playerRepository.save(player);
        } catch (ParseException e) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(PlayerDTO playerDTO){
        try {
            Player player = convertToEntity(playerDTO);
            playerRepository.delete(player);
        } catch (ParseException e) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private PlayerDTO convertToDto(Player player){
        modelMapper.addMappings(new PropertyMap<Player, PlayerDTO>() {
            @Override
            protected void configure() {
                map().setPlayerName(source.getName());
                map().setTeamName(source.getTeam().getName());
            }
        });
        return modelMapper.map(player, PlayerDTO.class);
    }

    private Player convertToEntity(PlayerDTO playerDTO) throws ParseException{
        modelMapper.addMappings(new PropertyMap<PlayerDTO, Player>() {
            @Override
            protected void configure() {
                map().setName(source.getPlayerName());
                map().getTeam().setName(source.getTeamName());
            }
        });
        return modelMapper.map(playerDTO, Player.class);
    }
}
