package net.ictcampus.campflix.controller.repositories;

import net.ictcampus.campflix.model.models.Movie;
import net.ictcampus.campflix.model.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Integer> {
    @Query("SELECT m FROM Movie m Join Genre g WHERE g.name LIKE CONCAT ('%', :name, '%')")
    Iterable<Movie> findByGenreName(@Param("name") String name);

    @Query("SELECT m FROM Movie m WHERE m.name LIKE CONCAT ('%', :name, '%')")
    Iterable<Movie> findByMovieName(@Param("name") String name);


}
