package net.ictcampus.apithena.controller.controllers;

import net.ictcampus.apithena.controller.services.MonsterService;
import net.ictcampus.apithena.model.models.Monster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/genres/")
public class MonsterController {

    private final MonsterService monsterService;

    @Autowired
    public MonsterController(MonsterService monsterService) {
        this.monsterService = monsterService;
    }

    @GetMapping(path="{id}")
    public Monster findById(@PathVariable Integer id){
        try{
            return monsterService.findById(id);
        } catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre not Found");
        }
    }

    @GetMapping
    public Iterable<Monster> findByName(@RequestParam(required = false) String name) {
        try{
            if (name != null ){
                return monsterService.findByName(name);
            } else {
                return monsterService.findAll();
            }
        } catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    @PostMapping(consumes = "application/json")
    public void insert(@RequestBody Monster monster){
        try{
            System.out.println(monster.getName()); //todo remove after debug
            monsterService.insert(monster);
        } catch(RuntimeException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Could not insert Genre");
        }
    }

    @PutMapping(consumes = "application/json")
    public void update(@RequestBody Monster monster) {
        try{
            monsterService.update(monster);
        } catch(RuntimeException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Could not update");
        }
    }


}
