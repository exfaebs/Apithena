package net.ictcampus.apithena.controller.repositories;

import net.ictcampus.apithena.model.models.God;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GodRepository extends CrudRepository<God, Integer> {
    @Query("SELECT m FROM God m Join Monster g WHERE g.name LIKE CONCAT ('%', :name, '%')")
    Iterable<God> findByGenreName(@Param("name") String name);

    @Query("SELECT m FROM God m WHERE m.name LIKE CONCAT ('%', :name, '%')")
    Iterable<God> findByMovieName(@Param("name") String name);


}
