package net.ictcampus.apithena.model.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Monster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Genre name is required")
    @NotBlank(message = "Genre name can't be blank")
    private String name;

    @OneToMany //Wir haben eine 1:m Beziehung Genre:Movie
    @JsonBackReference //Verhindert endlos-loop
    private Set<God> gods = new HashSet<>();

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

    public Set<God> getMovies() {
        return gods;
    }

    public void setMovies(Set<God> gods) {
        this.gods = gods;
    }
}
