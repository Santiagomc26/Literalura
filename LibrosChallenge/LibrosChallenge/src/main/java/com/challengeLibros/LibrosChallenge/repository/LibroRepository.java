package com.challengeLibros.LibrosChallenge.repository;

import com.challengeLibros.LibrosChallenge.model.Libro;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface LibroRepository extends CrudRepository<Libro, Long> {

    // Verifica si existe un libro por título
    @Query("SELECT COUNT(l) > 0 FROM Libro l WHERE UPPER(l.titulo) LIKE UPPER(CONCAT('%', :titulo, '%'))")
    boolean existsByTitulo(@Param("titulo") String titulo);

    // Obtiene todos los libros junto con sus autores
    @Query("SELECT l FROM Libro l")
    List<Libro> obtenerAutoresDeLibros();

    // Obtiene autores vivos durante un año específico
    @Query("SELECT DISTINCT l FROM Libro l JOIN l.autor a WHERE a.fechaDeNacimiento <= :anio AND (a.fechaDeMuerte IS NULL OR a.fechaDeMuerte >= :anio)")
    List<Libro> obtenerAutoresVivosDurante(@Param("anio") int anio);

    //Obtiene libros por un idioma específico
    @Query("SELECT l FROM Libro l WHERE :idioma MEMBER OF l.idiomas")
    List<Libro> obtenerLibrosPorIdioma(@Param("idioma") String idioma);


}

