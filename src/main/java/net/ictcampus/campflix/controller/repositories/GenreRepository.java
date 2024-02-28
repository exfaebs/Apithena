package net.ictcampus.campflix.controller.repositories;

import net.ictcampus.campflix.model.models.Genre;
import net.ictcampus.campflix.model.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends CrudRepository<Genre, Integer> {
    @Query("SELECT g FROM Genre g WHERE g.name LIKE CONCAT ('%', :name, '%')")
    Iterable<Genre> findByGenreName(@Param("name") String name);
}
