package net.ictcampus.apithena.controller.services;

import net.ictcampus.apithena.controller.repositories.MovieRepository;
import net.ictcampus.apithena.model.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
