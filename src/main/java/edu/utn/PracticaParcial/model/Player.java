package edu.utn.PracticaParcial.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
//validation should ask for validation dependency, currently not in pom, so it must be a subdependency of another

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Player {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    @Min(value = 18, message = "La edad debe ser mayor a 18")
    @Max(value = 50, message = "La edad no debe ser mayor a 50")
    private Integer age;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_equipo", referencedColumnName = "id")
    @JsonBackReference
    //@NotNull(message = "Player submited without team")
    @NotEmpty(message = "Player submited without team") //Not null or empty
    private Team team;
}
