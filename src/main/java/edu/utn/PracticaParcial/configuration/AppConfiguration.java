package edu.utn.PracticaParcial.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean("ModelMapperPlayer")
    public ModelMapper modelMapper() {

        //TODO: put configurations here
        return new ModelMapper();
    }
}
