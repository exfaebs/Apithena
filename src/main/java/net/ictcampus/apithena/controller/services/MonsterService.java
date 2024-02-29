package net.ictcampus.apithena.controller.services;

import net.ictcampus.apithena.controller.repositories.MonsterRepository;
import net.ictcampus.apithena.model.models.Monster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class MonsterService {

    private final MonsterRepository monsterRepository;
    @Autowired
    public MonsterService(MonsterRepository monsterRepository) {
        this.monsterRepository = monsterRepository;
    }

    public Monster findById(Integer id){
        Optional<Monster> genre = monsterRepository.findById(id);
        return genre.orElseThrow(EntityNotFoundException::new); //Entity Genre exists or Error is thrown
    }
    public Iterable<Monster> findByName(String query){
        return monsterRepository.findMonsterByName(query);
    }

    public Iterable<Monster> findAll(){
        return monsterRepository.findAll();
    }

    public void insert(Monster monster){
        monsterRepository.save(monster);
    }

    public void delete(Monster monster) {monsterRepository.delete(monster);}

    public void update(Monster monster) {
        if (monsterRepository.existsById(monster.getId())){
            monsterRepository.save(monster);
        } else{
            throw new EntityNotFoundException();
        }

    }

}
