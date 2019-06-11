package edu.utn.PracticaParcial.inteface;

import org.springframework.beans.factory.annotation.Value;

public interface CantJugxE {
    String getNombre();
    Integer getCantidad();

    /*if not using "as cantidad" in query
    @Value("{target.cant_jug}")
    Integer getCantJug();
    */
}
