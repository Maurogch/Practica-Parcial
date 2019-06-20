package edu.utn.PracticaParcial.configuration;

import edu.utn.PracticaParcial.model.Player;
import edu.utn.PracticaParcial.model.PlayerDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class AppConfiguration {

    @Bean("ModelMapperPlayer")
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addMappings(new PropertyMap<PlayerDTO, Player>() {
            @Override
            protected void configure() {
                map().setName(source.getPlayerName());
                map().getTeam().setName(source.getTeamName());
            }
        });

        modelMapper.addMappings(new PropertyMap<Player, PlayerDTO>() {
            @Override
            protected void configure() {
                map().setPlayerName(source.getName());
                map().setTeamName(source.getTeam().getName());
            }
        });
        return modelMapper;
    }

    @Bean("Executor")
    public ThreadPoolTaskExecutor getThreadPoolTaskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(100);
        executor.initialize();

        return executor;
    }
}
