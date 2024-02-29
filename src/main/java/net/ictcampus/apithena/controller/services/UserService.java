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



    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    public User findById(Integer id){
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(EntityNotFoundException::new); //Wenn es kein User-Objekt gibt, soll es diesen Fehler ausgeben
    }

    public Iterable<User> findByName(String query){
        return userRepository.findByName(query);
    }

    public Iterable<User> findAll(){
        return userRepository.findAll();
    }


    public void signUp(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword())); //encrypts the password, before saving
        userRepository.save(user);
    }

    public void delete(User user){
        userRepository.delete(user);
    }

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
