package com.challengeLibros.LibrosChallenge.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true, nullable = false)
    private String titulo;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<DatosAutor> autor;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> idiomas;
    private Integer numeroDeDescargas;

    public Libro(){}
    public Libro(DatosLibros datosLibros) {

        this.titulo = datosLibros.titulo();
        this.autor = datosLibros.autor();
        this.idiomas = datosLibros.idioma();
        this.numeroDeDescargas = datosLibros.numeroDeDescargas();
    }

    @Override
    public String toString() {
        return "Libro{" +
                "Id=" + Id +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", idiomas='" + idiomas + '\'' +
                ", numeroDeDescargas=" + numeroDeDescargas +
                '}';
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<DatosAutor> getAutor() {
        return autor;
    }

    public void setAutor(List<DatosAutor> autor) {
        this.autor = autor;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Integer numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }
}
