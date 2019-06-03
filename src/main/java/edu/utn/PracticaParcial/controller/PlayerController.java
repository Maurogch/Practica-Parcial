package edu.utn.PracticaParcial.controller;

import edu.utn.PracticaParcial.model.Player;
import edu.utn.PracticaParcial.model.PlayerDTO;
import edu.utn.PracticaParcial.repository.PlayerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    private ModelMapper modelMapper;

    /*Post non DTO
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody @Valid Player player){
        playerRepository.save(player);
    }*/

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody @Valid PlayerDTO playerDTO){
        try {
            Player player = convertToEntity(playerDTO);
            playerRepository.save(player);
        } catch (ParseException e) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
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
    public PlayerDTO getById(@PathVariable("id") Integer id){
        Player player = playerRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Player not found"));

        return convertToDto(player);
    }

    private PlayerDTO convertToDto(Player player){
        return modelMapper.map(player, PlayerDTO.class);
    }

    private Player convertToEntity(PlayerDTO playerDTO) throws ParseException{
        return modelMapper.map(playerDTO, Player.class);
    }
}
