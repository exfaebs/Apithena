package net.ictcampus.apithena.controller.controllers;

import net.ictcampus.apithena.controller.services.GenreService;
import net.ictcampus.apithena.model.models.Monster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/genres/")
public class MonsterController {

    private final GenreService genreService;

    @Autowired
    public MonsterController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping(path="{id}")
    public Monster findById(@PathVariable Integer id){
        try{
            return genreService.findById(id);
        } catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre not Found");
        }
    }

    @GetMapping
    public Iterable<Monster> findByName(@RequestParam(required = false) String name) {
        try{
            if (name != null ){
                return genreService.findByName(name);
            } else {
                return genreService.findAll();
            }
        } catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    @PostMapping(consumes = "application/json")
    public void insert(@RequestBody Monster monster){
        try{
            System.out.println(monster.getName()); //todo remove after debug
            genreService.insert(monster);
        } catch(RuntimeException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Could not insert Genre");
        }
    }

    @PutMapping(consumes = "application/json")
    public void update(@RequestBody Monster monster) {
        try{
            genreService.update(monster);
        } catch(RuntimeException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Could not update");
        }
    }


}
