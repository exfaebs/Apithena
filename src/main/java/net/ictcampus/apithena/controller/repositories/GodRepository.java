package net.ictcampus.apithena.controller.repositories;

import net.ictcampus.apithena.model.models.God;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GodRepository extends CrudRepository<God, Integer> {


    // eigene Query, um gott nach namen abzufragen,
    @Query("SELECT m FROM God m WHERE m.name LIKE CONCAT ('%', :name, '%')")
    Iterable<God> findByGodName(@Param("name") String name);




}
