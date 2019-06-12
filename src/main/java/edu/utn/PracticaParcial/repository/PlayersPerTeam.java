package edu.utn.PracticaParcial.repository;

import org.springframework.beans.factory.annotation.Value;

public interface PlayersPerTeam {
    String getName();
    Integer getQuantity();

    /*if not using "as cantidad" in query
    @Value("{target.cant_jug}")
    Integer getCantJug();
    */
}
