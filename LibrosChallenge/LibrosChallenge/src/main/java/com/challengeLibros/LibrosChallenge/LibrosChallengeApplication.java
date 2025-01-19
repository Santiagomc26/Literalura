package com.challengeLibros.LibrosChallenge;

import com.challengeLibros.LibrosChallenge.principal.Principal;
import com.challengeLibros.LibrosChallenge.repository.LibroRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class LibrosChallengeApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository libroRepository;

	public static void main(String[] args) {
		SpringApplication.run(LibrosChallengeApplication.class, args);
	}

	@Override
	public void run(String... args) {
		// Pasamos el repositorio al constructor de Principal
		Principal principal = new Principal(libroRepository);
		principal.moestrarMenu();
	}
}
