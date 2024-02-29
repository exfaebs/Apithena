package net.ictcampus.apithena.controller.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import net.ictcampus.apithena.controller.services.UserService;
import net.ictcampus.apithena.model.models.Monster;
import net.ictcampus.apithena.model.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping("/users") //nimmt alle /users request an
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "{id}")
    @Operation(summary = "Finds a User by ID e.g. /15")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the User under this ID",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Monster.class))}),
            @ApiResponse(responseCode = "404", description = "There is no User with this ID", content = {@Content}),
            @ApiResponse(responseCode = "403", description = "You are not authorized to do that", content = @Content)
    })
    public User findById(@PathVariable Integer id) { //wichtig dass es Integer ist und nicht int
        try{
            return userService.findById(id);
        } catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }
    @GetMapping
    @Operation(summary = "Show all users (empty) or find by name with parameter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users(s) under this name found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Monster.class))}),
            @ApiResponse(responseCode = "404", description = "No User found under this name", content = @Content),
            @ApiResponse(responseCode = "403", description = "You are not allowed to do this, log in", content = @Content)
    })
    public Iterable<User> findByName(@RequestParam(required = false) String name) {

        try{
            if (name != null){
                return userService.findByName(name);

            } else{
                return userService.findAll();
            }

        }

        catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }


    @PostMapping(path="/sign-up", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Signs up new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User successfully created",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "User could not be created", content = @Content),
            @ApiResponse(responseCode = "403", description = "You are not authorized to do that", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request, JSON not complete", content = @Content)
    })
    public void signUp(@Valid @RequestBody User user){
        try{
            userService.signUp(user);
        } catch(RuntimeException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Could not register");
        }
    }

    @DeleteMapping(path = "{id}")
    @Operation(summary = "Deletes User by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found and successfully deleted",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "User could not be deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "User was not found and therefore not deleted", content = @Content),
            @ApiResponse(responseCode = "403", description = "You are not authorized to do that", content = @Content)
    })
    public void deleteById(@PathVariable Integer id){
        try{
            User user = userService.findById(id);
            userService.delete(user);
        } catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

    }

    @PutMapping(consumes = "application/json")
    @Operation(summary = "Update a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was updated sucessfully",
            content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflict: User could not be updated",
            content = @Content),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not updated, because not found", content = @Content)
    })
    public void update(@Valid @RequestBody User user) {
        try{
            userService.update(user);
        } catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        catch(RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Could not update");
        }

    }
}
