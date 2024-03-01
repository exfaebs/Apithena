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


    @Autowired // erforderliche Abhängigkeit sollte bereit gestellt werden
    // hier sollte godRepository-Instanz automatisch in den Konstruktor injiziert werden
    public GodService(GodRepository godRepository) {
        this.godRepository = godRepository;
    }


    // Get-methode, um in godrepo ein Gott zu suchen anhand der idnummer
    public God findById(Integer id){
        Optional<God> god = godRepository.findById(id); //Evtl gibt es keinen Eintrag, daher wird es als Optional designiert
        return god.orElseThrow(EntityNotFoundException::new);
    }


    // Get-methode, um in godrepo gott oder götter zu suchen, die einen bestimmten namen haben
    // es kann sein, dass götter die gleichen namen haben, darum eine Array
    public Iterable<God> findByGodName(String name){
        Iterable<God> godIterable = godRepository.findByGodName(name);
        return godIterable;
    }


    // Get-methode, um in godrepo alle götter zu finden, die vorhanden sind
    public Iterable<God> findAll(){
        Iterable<God> godIterable = godRepository.findAll();
        return godIterable;
    }

    // methode für Post-request, neue gott oder vorhandene Gott wird erstellt bzw. geändert
    public void insert(God god){
        godRepository.save(god);
    }



    // methode für Put-Request, nur bestehende gott wird updated
    // es sollte nicht möglich sein, damit neue Götter zu erstellen
    public void update(God god){
            if (godRepository.existsById(god.getId())) {
                godRepository.save(god);
            } else { throw new EntityNotFoundException();}
    }


    //delete a god
    public void delete(God god){
        godRepository.delete(god);
    }



    // methode für Delete-Request, God wird anhand der Idnummer gelöscht
    public void deleteById(Integer id){
        God god = findById(id);
        godRepository.delete(god);
    }



}
