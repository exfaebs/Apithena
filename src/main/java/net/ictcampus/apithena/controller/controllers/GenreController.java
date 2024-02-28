package net.ictcampus.apithena.controller.controllers;

import net.ictcampus.apithena.controller.services.GenreService;
import net.ictcampus.apithena.model.models.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/genres/")
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping(path="{id}")
    public Genre findById(@PathVariable Integer id){
        try{
            return genreService.findById(id);
        } catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre not Found");
        }
    }

    @GetMapping
    public Iterable<Genre> findByName(@RequestParam(required = false) String name) {
        try{
            if (name != null ){
                return genreService.findByName(name);
            } else {
                return genreService.findAll();
            }
        } catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    @PostMapping(consumes = "application/json")
    public void insert(@RequestBody Genre genre){
        try{
            System.out.println(genre.getName()); //todo remove after debug
            genreService.insert(genre);
        } catch(RuntimeException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Could not insert Genre");
        }
    }

    @PutMapping(consumes = "application/json")
    public void update(@RequestBody Genre genre) {
        try{
            genreService.update(genre);
        } catch(RuntimeException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Could not update");
        }
    }


}
