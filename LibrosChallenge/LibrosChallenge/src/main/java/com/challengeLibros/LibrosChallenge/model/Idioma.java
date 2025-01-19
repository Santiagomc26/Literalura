package com.challengeLibros.LibrosChallenge.model;

public enum Idioma {
    SPANISH("es", "Español"),
    ENGLISH("en", "Inglés"),
    FRENCH("fr", "Francés"),
    PORTUGUESE("pt", "Portugués");

    private final String code;
    private final String name;


    Idioma(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static Idioma fromString(String code) {
        for (Idioma language : Idioma.values()) {
            if (language.code.equalsIgnoreCase(code)) {
                return language;
            }
        }
        throw new IllegalArgumentException("No se encontró el idioma para el código: " + code);
    }

    public static Idioma fromName(String name) {
        for (Idioma language : Idioma.values()) {
            if (language.name.equalsIgnoreCase(name)) {
                return language;
            }
        }
        throw new IllegalArgumentException("No se encontró el idioma para el nombre: " + name);
    }
}
