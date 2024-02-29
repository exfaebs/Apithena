package net.ictcampus.apithena.utils;


import net.ictcampus.apithena.model.models.God;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


public class TestDataUtil {

    public static List<God> getTestGods(){
        List<God> gods = new ArrayList<>();


        for (int i=1; i<=3; i++ ){
            God god = new God();
            god.setId(i);
            god.setName("God" + i);
            god.setJurisdiction("Setting Sun");
            gods.add(god);

        }
        return gods;
    }
}
