package net.ictcampus.campflix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication() //rausgelöscht aus Klammer exclude = {DataSourceAutoConfiguration.class}
public class CampflixApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampflixApplication.class, args);
    }

}
