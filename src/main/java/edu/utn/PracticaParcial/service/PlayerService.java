package edu.utn.PracticaParcial.service;

import edu.utn.PracticaParcial.model.Player;
import edu.utn.PracticaParcial.repository.PlayerNative;
import edu.utn.PracticaParcial.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    public void addPlayer(Player player){
        playerRepository.save(player);
    }

    public List<Player> getAll(){
        return playerRepository.findAll();
    }

    @Async("Executor")
    public CompletableFuture<List<PlayerNative>> getByMonthsInTeam(){
        return CompletableFuture.completedFuture(playerRepository.getAllWithMonthsInTeam());
    }
}
