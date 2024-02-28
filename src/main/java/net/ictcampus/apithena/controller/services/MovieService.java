package net.ictcampus.apithena.controller.services;

import net.ictcampus.apithena.controller.repositories.GodRepository;
import net.ictcampus.apithena.model.models.God;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class MovieService {
    private final GodRepository godRepository;

    @Autowired
    public MovieService(GodRepository godRepository) {
        this.godRepository = godRepository;
    }

    public God findById(Integer id){
        Optional<God> movie = godRepository.findById(id); //Evtl gibt es keinen Eintrag, daher wird es als Optional designiert
        return movie.orElseThrow(EntityNotFoundException::new);
    }

    public Iterable<God> findByMovieName(String query){
        Iterable<God> movieIterable = godRepository.findByMovieName(query);
        return movieIterable;
    }
    public Iterable<God> findByGenreName(String query){
        Iterable<God> movieIterable = godRepository.findByGenreName(query);
        return movieIterable;
    }

    public Iterable<God> findAll(){
        Iterable<God> movieIterable = godRepository.findAll();
        return movieIterable;
    }
    public void insert(God god){
        godRepository.save(god);
    }


    public void delete(God god){
        godRepository.delete(god);
    }

    public void update(God god) {
        godRepository.save(god);}
}
