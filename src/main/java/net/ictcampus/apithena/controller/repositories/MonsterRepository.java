package net.ictcampus.apithena.controller.repositories;

import net.ictcampus.apithena.model.models.Monster;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MonsterRepository extends CrudRepository<Monster, Integer> {

    Iterable<Monster> findMonsterByName(@Param("name") String name);
}
