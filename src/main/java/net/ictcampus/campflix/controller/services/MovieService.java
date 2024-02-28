package net.ictcampus.campflix.controller.services;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import net.ictcampus.campflix.controller.repositories.MovieRepository;
import net.ictcampus.campflix.model.models.Genre;
import net.ictcampus.campflix.model.models.Movie;
import net.ictcampus.campflix.model.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie findById(Integer id){
        Optional<Movie> movie = movieRepository.findById(id); //Evtl gibt es keinen Eintrag, daher wird es als Optional designiert
        return movie.orElseThrow(EntityNotFoundException::new);
    }

    public Iterable<Movie> findByMovieName(String query){
        Iterable<Movie> movieIterable = movieRepository.findByMovieName(query);
        return movieIterable;
    }
    public Iterable<Movie> findByGenreName(String query){
        Iterable<Movie> movieIterable = movieRepository.findByGenreName(query);
        return movieIterable;
    }

    public Iterable<Movie> findAll(){
        Iterable<Movie> movieIterable = movieRepository.findAll();
        return movieIterable;
    }
    public void insert(Movie movie){
        movieRepository.save(movie);
    }


    public void delete(Movie movie){
        movieRepository.delete(movie);
    }

    public void update(Movie movie) {movieRepository.save(movie);}
}
