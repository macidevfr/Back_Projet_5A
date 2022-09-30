package com.example.projet5a;

import Entities.Center;
import Entities.City;
import JPA.ICenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;
@EnableJpaRepositories
@EntityScan("Entities")
@SpringBootApplication
public class Projet5AApplication {

    public static void main(String[] args) {
        SpringApplication.run(Projet5AApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(ICenter icenter){
        return args -> {
            City city = new City("Nancy",54500L);
            icenter.save(new Center("CHU",city));
        };
    }
}
