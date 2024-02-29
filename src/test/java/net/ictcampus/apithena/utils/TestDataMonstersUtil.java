package net.ictcampus.apithena.utils;

import net.ictcampus.apithena.model.models.Monster;


import java.util.ArrayList;
import java.util.List;

public class TestDataMonstersUtil {
    public static List<Monster> getTestMonsters(){
        List<Monster> movies = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            Monster movie = new Monster();
            movie.setId(i);
            movie.setName("Monster"+i);
            movie.setCharacteristic("probably Shagged by Zeus");


            movies.add(movie);
        }

        return movies;
    }
}
