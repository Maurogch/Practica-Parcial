package edu.utn.PracticaParcial.model;

//https://www.baeldung.com/entity-to-and-from-dto-for-a-java-spring-application

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO {
    private Integer id;
    private String playerName;
    private String teamName;
    private Integer playerAge;


}
