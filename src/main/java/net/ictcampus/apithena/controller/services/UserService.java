package net.ictcampus.apithena.controller.services;

import net.ictcampus.apithena.controller.repositories.UserRepository;
import net.ictcampus.apithena.model.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;



    @Autowired //used for automatic dependency injection.
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    public User findById(Integer id){
        Optional<User> user = userRepository.findById(id); //Optional Flag may or may not contain element
        return user.orElseThrow(EntityNotFoundException::new); //When there is no user found .orElse... throws Exception
    }

    public Iterable<User> findByName(String name){
        return userRepository.findByName(name);
    }

    public Iterable<User> findAll(){
        return userRepository.findAll();
    }

    /**
     * Creates User in Database
     * @param user User-Object to be created. The Password will be bcrypt hashed before saving into database
     */
    public void signUp(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }


    public void delete(User user){ //simple crud-delete
        userRepository.delete(user);
    }

    /**
     *Updates informations about an existing user. Logic prohibits construction of new user, if missing. (Error Handling
     * in Controller)
     * @param user Userobject to be updated
     */
    public void update(User user) {
        if (userRepository.existsById(user.getId())){
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword())); //encrypts password, before saving
            userRepository.save(user);
        }
        else {
            throw new EntityNotFoundException();
        }

    }




}
