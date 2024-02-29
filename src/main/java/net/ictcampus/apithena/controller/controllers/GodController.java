package net.ictcampus.apithena.controller.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import net.ictcampus.apithena.controller.services.GodService;
import net.ictcampus.apithena.model.models.God;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping("/gods/")
public class GodController {

    private final GodService godService;

    @Autowired
    public GodController(GodService godService) {
        this.godService = godService;
    }




    @GetMapping(path = "{id}")
    @Operation(summary = "Find God with his ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "God was found", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden for God's sake", content = @Content),
            @ApiResponse(responseCode = "404", description = "God cannot be found", content = @Content)
    })
    public God findById(@PathVariable Integer id) {
        try {
            return godService.findById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "God not found");
        }
    }

    @GetMapping
    @Operation(summary = "Find all Gods")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "God was found", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden for God's sake", content = @Content),
            @ApiResponse(responseCode = "404", description = "God cannot be found", content = @Content)
    })
    public Iterable<God> findByName(@RequestParam(required = false) String name){
        try {
            if (name != null) {
                if(!godService.findByGodName(name).iterator().hasNext()){
                    throw new EntityNotFoundException();
                }
                return godService.findByGodName(name);

            } else {
                return godService.findAll();
            }
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "God not found");
        }
    }

    @PostMapping(consumes = "application/json")
    @Operation(summary = "Create a new God")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "A new God was born",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = God.class))}),

            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = God.class))}),
            @ApiResponse(responseCode = "409", description = "Could not create God", content = @Content)
    })
    @ResponseStatus(HttpStatus.CREATED)
    public void insert(@Valid @RequestBody God god) {
        try {

            godService.insert(god);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Could not insert God");
        }
    }



    @PutMapping(consumes = "application/json")
    @Operation(summary = "Update or Create a new God")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "God was updated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = God.class))}),

            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = God.class))}),

            @ApiResponse(responseCode = "403", description = "Forbidden for God's sake", content = @Content),
            @ApiResponse(responseCode = "409", description = "Could not update god", content = @Content)
    })

    public void update(@Valid @RequestBody God god) {   // to check if working

        try {
            godService.update(god);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Could not update God");
        }
    }

    @DeleteMapping(path = {"{id}"})
    @Operation(summary = "Kill a God")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "God was killed",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "God not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "403", description = "Forbidden for God's sake", content = @Content)
    })
    public void deleteById(@PathVariable Integer id) {
        try {
            God god = godService.findById(id);
            godService.delete(god);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "God not found");
        }
    }


//    public Iterable<Movie> findMovieByMovieName(@RequestParam String name) {
//        try{
//            return movieService.findByMovieName(name);
//        } catch(EntityNotFoundException e){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
//        }
//    }
//
//    public Iterable<Movie> findMovieByGenreName(@RequestParam String genre) {
//        try{
//            return movieService.findByGenreName(genre);
//        } catch(EntityNotFoundException e){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
//        }
//    }

}
