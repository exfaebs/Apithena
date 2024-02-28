package net.ictcampus.apithena.controller.controllers;

import net.ictcampus.apithena.controller.services.GodService;
import net.ictcampus.apithena.model.models.God;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping("/movies/")
public class GodController {

    private final GodService godService;

    @Autowired
    public GodController(GodService godService) {
        this.godService = godService;
    }

    @GetMapping(path = "{id}")
    public God findById(@PathVariable Integer id) {
        try {
            return godService.findById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
        }
    }

    @GetMapping
    public Iterable<God> findByNameORGenreName(@RequestParam(required = false) String name,
                                               @RequestParam(required = false) String genre) {
        try {
            if (name != null) {
                return godService.findByMovieName(name);
            } else if (genre != null) {
                return godService.findByGenreName(genre);
            } else {
                return godService.findAll();
            }
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
        }
    }

    @PostMapping(consumes = "application/json")
    public void insert(@Valid @RequestBody God god) {
        try {

            godService.insert(god);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Could not insert movie");
        }
    }

    @DeleteMapping(path = {"{id}"})
    public void deleteById(@PathVariable Integer id) {
        try {
            God god = godService.findById(id);
            godService.delete(god);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    @PutMapping(consumes = "application/json")
    public void update(@Valid @RequestBody God god) { //todo check if working
        try {
            godService.update(god);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Could not update");
        }
    }


//    public Iterable<Movie> findMovieByMovieName(@RequestParam String name) {
//        try{
//            return movieService.findByMovieName(name);
//        } catch(EntityNotFoundException e){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
//        }
//    }
//
//    public Iterable<Movie> findMovieByGenreName(@RequestParam String genre) {
//        try{
//            return movieService.findByGenreName(genre);
//        } catch(EntityNotFoundException e){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
//        }
//    }

}
