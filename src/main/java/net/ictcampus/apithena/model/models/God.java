package net.ictcampus.apithena.model.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class God {

    // mit dieser annotation wird bestimmt, dass diese Variable mit Primary Key in Datenbank tabelle God gemappt wird
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    // darf nicht null oder leer sein
    @NotNull(message = "God name is required")
    @NotBlank(message = "God name can't be empty")
    private String name;



    // darf nicht null oder leer sein
    @NotNull(message = "Jurisdiction is required")
    @NotBlank(message = "Jurisdiction cannot be empty")
    private String jurisdiction;



    //---------- GET & SET ----------------------------------------------------

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(String jurisdiction) {
        this.jurisdiction = jurisdiction;
    }
}
