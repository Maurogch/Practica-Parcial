package edu.utn.PracticaParcial.controller;

import edu.utn.PracticaParcial.model.Player;
import edu.utn.PracticaParcial.repository.PlayerRepository;
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
import java.util.List;


@RestController
@RequestMapping("/player")
public class PlayerController {
    @Autowired
    private PlayerRepository playerRepository;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody @Valid Player player){
        playerRepository.save(player);
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<Player> getAll(){
        List<Player> players = playerRepository.findAll();

        if(!players.isEmpty())
            return players;
        else
            throw new HttpClientErrorException(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public Player getById(@PathVariable("id") Integer id){
        return playerRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Player not found"));
    }


}
