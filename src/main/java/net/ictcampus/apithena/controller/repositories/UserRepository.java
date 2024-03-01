package net.ictcampus.apithena.controller.repositories;

import net.ictcampus.apithena.model.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    /**
     * Finds a list of names, which contain name
     * @param name queried name to be found
     * @return possibly empty Iterable of Users which were found
     */
    @Query("SELECT u FROM User u WHERE u.username LIKE CONCAT ('%', :name, '%')")
    Iterable<User> findByName(@Param("name") String name); //

    /**
    Finds only one user by its username
     */
    User findByUsername(@Param("username") String username);

}

