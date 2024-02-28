package net.ictcampus.apithena.model.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class God {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Movie name is required")
    @NotBlank(message = "Movie name can't be empty")
    private String name;

    @NotNull(message = "Movie duration is required")
    @NotBlank(message = "Movie duration can't be empty")
    private Integer duration;

    @ManyToOne
    @JoinColumn(name = "genre_id") //Hibernate wird mitgeteilt, wo die betreffende Zeile in der Datenbank ist.
    private Monster monster;

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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Monster getGenre() {
        return monster;
    }

    public void setGenre(Monster monster) {
        this.monster = monster;
    }
}
