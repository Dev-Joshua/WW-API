package com.apirest.wwapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.apirest.wwapi.controller.ConexionDB;

@SpringBootApplication
public class WwapiApplication {

	@Autowired
	private ConexionDB conexionBD;

	public static void main(String[] args) {
		SpringApplication.run(WwapiApplication.class, args);
		System.out.println("Started....");
	}

	@Bean
    public CommandLineRunner demo() {
        return (args) -> {
            conexionBD.connect();
        };
    }
}
