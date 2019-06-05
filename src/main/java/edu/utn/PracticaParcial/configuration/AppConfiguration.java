package edu.utn.PracticaParcial.configuration;

import edu.utn.PracticaParcial.model.Player;
import edu.utn.PracticaParcial.model.PlayerDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
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
}
