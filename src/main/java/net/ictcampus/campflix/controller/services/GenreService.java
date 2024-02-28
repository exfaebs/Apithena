package net.ictcampus.campflix.controller.services;

import net.ictcampus.campflix.controller.repositories.GenreRepository;
import net.ictcampus.campflix.model.models.Genre;
import net.ictcampus.campflix.model.models.Movie;
import net.ictcampus.campflix.model.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class GenreService {

    private final GenreRepository genreRepository;
    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Genre findById(Integer id){
        Optional<Genre> genre = genreRepository.findById(id);
        return genre.orElseThrow(EntityNotFoundException::new); //Entity Genre exists or Error is thrown
    }
    public Iterable<Genre> findByName(String query){
        Iterable<Genre> genreIterable = genreRepository.findByGenreName(query);
        return genreIterable;
    }

    public Iterable<Genre> findAll(){
        Iterable<Genre> genreIterable = genreRepository.findAll();
        return genreIterable;
    }

    public void insert(Genre genre){
        genreRepository.save(genre);
    }

    public void update(Genre genre) {genreRepository.save(genre);}

}
