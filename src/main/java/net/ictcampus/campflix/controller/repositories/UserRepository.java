package net.ictcampus.campflix.controller.repositories;

import net.ictcampus.campflix.model.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.username LIKE CONCAT ('%', :name, '%')")
    Iterable<User> findByName(@Param("name") String name); //geändert auf username

    User findByUsername(@Param("username") String username);

}

