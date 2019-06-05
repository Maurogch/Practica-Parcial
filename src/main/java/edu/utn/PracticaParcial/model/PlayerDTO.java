package edu.utn.PracticaParcial.model;

//https://www.baeldung.com/entity-to-and-from-dto-for-a-java-spring-application

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO {
    //private Integer id;
    @NotEmpty //Works in strings, checks not null and not empty
    private String playerName;
    @NotEmpty
    private String teamName;
    private Integer playerAge;


}
