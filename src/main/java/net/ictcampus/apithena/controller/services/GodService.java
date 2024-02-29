package net.ictcampus.apithena.controller.services;

import ch.qos.logback.classic.spi.IThrowableProxy;
import net.ictcampus.apithena.controller.repositories.GodRepository;
import net.ictcampus.apithena.model.models.God;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class GodService {
    private final GodRepository godRepository;

    @Autowired
    public GodService(GodRepository godRepository) {
        this.godRepository = godRepository;
    }

    public God findById(Integer id){
        Optional<God> god = godRepository.findById(id); //Evtl gibt es keinen Eintrag, daher wird es als Optional designiert
        return god.orElseThrow(EntityNotFoundException::new);
    }

    public Iterable<God> findByGodName(String query){
        Iterable<God> godIterable = godRepository.findByGodName(query);
        return godIterable;
    }


    public Iterable<God> findAll(){
        Iterable<God> godIterable = godRepository.findAll();
        return godIterable;
    }

    public void insert(God god){
        godRepository.save(god);
    }


    public void update(God god){

            if (godRepository.existsById(god.getId())) {
                godRepository.save(god);
            } else { throw new EntityNotFoundException();}


    }



/* ----Erste einfache Version ---
    public void update(God god) {

        godRepository.save(god);}

*/

    public void delete(God god){
        godRepository.delete(god);
    }





}
