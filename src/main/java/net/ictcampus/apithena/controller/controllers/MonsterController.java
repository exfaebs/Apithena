package net.ictcampus.apithena.controller.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import net.ictcampus.apithena.controller.services.MonsterService;
import net.ictcampus.apithena.model.models.Monster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping("/monsters/")


public class MonsterController {

    private final MonsterService monsterService;

    @Autowired
    public MonsterController(MonsterService monsterService) {
        this.monsterService = monsterService;
    }

    @GetMapping(path="{id}")
    @Operation(summary = "Finds a Monster by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Monster with this ID was found",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Monster.class))}),
            @ApiResponse(responseCode = "404", description = "There is no Monster with this ID", content = {@Content}),
            @ApiResponse(responseCode = "403", description = "You are not authorized to do that", content = @Content)
    })
    public Monster findById(@PathVariable Integer id){
        try{
            return monsterService.findById(id);
        } catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Monster not Found");
        }
    }

    @GetMapping
    @Operation(summary = "Show all Monsters or find them with parameter by its name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Monster(s) under this name found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Monster.class))}),
            @ApiResponse(responseCode = "404", description = "No Monster found under this name", content = @Content),
            @ApiResponse(responseCode = "403", description = "You are not allowed to do this, log in", content = @Content)
    })
    public Iterable<Monster> findByName(@RequestParam(required = false) String name) {
        try{
            if (name != null ){
                if (!monsterService.findByName(name).iterator().hasNext()){
                    throw new EntityNotFoundException();
                }

                return monsterService.findByName(name);
            } else {
                return monsterService.findAll();
            }
        } catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Monster not found");
        }
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Creates new Monster")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Monster successfully created",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Monster could not be created", content = @Content),
            @ApiResponse(responseCode = "403", description = "You are not authorized to do that", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request, JSON not complete", content = @Content)
    })
    public void insert(@Valid @RequestBody Monster monster){
        try{

            monsterService.insert(monster);
        } catch(IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        catch(RuntimeException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Could not insert monster");
        }
    }

    @PutMapping(consumes = "application/json")
    @Operation(description = "Updates OR Creates Monster, if not found")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Monster sucessfully updated OR Created",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Monster could not be updated due to runtime error", content = @Content),
            @ApiResponse(responseCode = "404", description = "Monster was not found and could therefore not be updated", content = @Content),
            @ApiResponse(responseCode = "403", description = "You are not authorized to do that", content = @Content),
            @ApiResponse(responseCode = "400", description = "JSON is missing content of Model. Validation failed.", content = @Content)
    })
    public void update(@Valid @RequestBody Monster monster) {
        try{
            monsterService.update(monster);

        } catch(EntityNotFoundException e){

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not update monster: it does not exist");
        }
        catch(RuntimeException e){

            throw new ResponseStatusException(HttpStatus.CONFLICT, "Could not update monster");
        }
    }

    @DeleteMapping(path = {"{id}"})
    @Operation(description = "Deletes Monster by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Monster found and successfully deleted",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Monster could not be deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Monster was not found", content = @Content),
            @ApiResponse(responseCode = "403", description = "You are not authorized to do that", content = @Content)
    })
    public void deleteById(@PathVariable Integer id) {
        try {
            Monster monster = monsterService.findById(id);
            monsterService.delete(monster);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Monster not found");
        }
    }


}
