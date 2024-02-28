package net.ictcampus.campflix.controller.controllers;

import net.ictcampus.campflix.controller.services.MovieService;
import net.ictcampus.campflix.controller.services.UserService;
import net.ictcampus.campflix.model.models.Genre;
import net.ictcampus.campflix.model.models.Movie;
import net.ictcampus.campflix.model.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping("/movies/")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping(path = "{id}")
    public Movie findById(@PathVariable Integer id) {
        try {
            return movieService.findById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
        }
    }

    @GetMapping
    public Iterable<Movie> findByNameORGenreName(@RequestParam(required = false) String name,
                                                 @RequestParam(required = false) String genre) {
        try {
            if (name != null) {
                return movieService.findByMovieName(name);
            } else if (genre != null) {
                return movieService.findByGenreName(genre);
            } else {
                return movieService.findAll();
            }
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
        }
    }

    @PostMapping(consumes = "application/json")
    public void insert(@Valid @RequestBody Movie movie) {
        try {

            movieService.insert(movie);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Could not insert movie");
        }
    }

    @DeleteMapping(path = {"{id}"})
    public void deleteById(@PathVariable Integer id) {
        try {
            Movie movie = movieService.findById(id);
            movieService.delete(movie);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    @PutMapping(consumes = "application/json")
    public void update(@Valid @RequestBody Movie movie) { //todo check if working
        try {
            movieService.update(movie);
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
