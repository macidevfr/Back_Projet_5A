package org.polytech.covidapi;

import JPA.ICentrer;
import entities.Center;
import entities.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CovidApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CovidApiApplication.class, args);
    }
        
}


