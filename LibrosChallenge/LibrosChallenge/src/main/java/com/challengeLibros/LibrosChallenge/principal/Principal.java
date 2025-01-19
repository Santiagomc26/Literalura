package com.challengeLibros.LibrosChallenge.principal;

import com.challengeLibros.LibrosChallenge.model.*;
import com.challengeLibros.LibrosChallenge.repository.LibroRepository;
import com.challengeLibros.LibrosChallenge.service.ConsumoAPI;
import com.challengeLibros.LibrosChallenge.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final ConvierteDatos conversor = new ConvierteDatos();
    private final Scanner teclado = new Scanner(System.in);
    private final LibroRepository repositorio;

    public Principal(LibroRepository repositorio) {
        this.repositorio = repositorio;
    }


    public void moestrarMenu() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n************MENU***********");
            System.out.println("1 - Buscar libro");
            System.out.println("2 - Listar libros registrados");
            System.out.println("3 - Listar autores registrados");
            System.out.println("4 - Listar autores vivos en determinado año");
            System.out.println("5 - Listar libros por idioma");
            System.out.println("0 - Salir");
            System.out.println("******************************");
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1 -> buscarLibroWeb();
                case 2 -> mostrarLibros();
                case 3 -> mostrarAutoresDeLosLibros();
                case 4 -> mostrarAutoresVivos();
                case 5 -> mostrarLibrosPorIdioma();
                case 0 -> System.out.println("Cerrando la aplicación...");
                default -> System.out.println("Opción inválida");
            }

        }
    }

    private void buscarLibroWeb() {
        System.out.println("Ingrese el nombre del libro que desea buscar:");
        String tituloLibro = teclado.nextLine();

        if (repositorio.existsByTitulo(tituloLibro)) {
            System.out.println("El libro ya está registrado en la base de datos.");
            return;
        }

        String json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        Datos datos = conversor.obtenerDatos(json, Datos.class);

        if (datos.resultados().isEmpty()) {
            System.out.println("No se encontraron resultados para el libro ingresado.");
            return;
        }

        DatosLibros datosLibros = datos.resultados().get(0);
        Libro nuevoLibro = new Libro(datosLibros);
        repositorio.save(nuevoLibro);

        System.out.println("Libro guardado: " + nuevoLibro);
        mostrarLibroFormateado(nuevoLibro);
    }
    private void mostrarLibroFormateado(Libro libro) {
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("                                     LIBRO");
        System.out.println("Titulo: " + libro.getTitulo());
        System.out.println("Autor: " + libro.getAutor().stream().map(DatosAutor::nombre).collect(Collectors.joining(", ")));
        System.out.println("Idioma: " + libro.getIdiomas());
        System.out.println("Numero de descargas: " + libro.getNumeroDeDescargas());
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println();
    }



    private void mostrarLibros() {
        List<Libro> libros = (List<Libro>) repositorio.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            for (Libro libro : libros) {
                System.out.println("--------------------------------------------------------------------------------"); // Línea superior
                System.out.println("                                 LIBRO");
                System.out.println("Titulo: " + libro.getTitulo());
                System.out.println("Autor: " + libro.getAutor().stream().map(DatosAutor::nombre).collect(Collectors.joining(", ")));
                System.out.println("Idioma: " + libro.getIdiomas());
                System.out.println("Numero de descargas: " + libro.getNumeroDeDescargas());
                System.out.println("--------------------------------------------------------------------------------"); // Línea inferior
                System.out.println(); // Espacio entre libros
            }
        }
    }


    private void mostrarAutoresDeLosLibros() {
        List<Libro> libros = (List<Libro>) repositorio.findAll();

        if (libros == null || libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        Map<String, List<String>> autoresConLibros = new HashMap<>();
        Map<String, String[]> infoAutores = new HashMap<>(); // Map para la info del autor

        for (Libro libro : libros) {
            String autor = libro.getAutor().stream().map(DatosAutor::nombre).collect(Collectors.joining(", "));
            String nacimiento = libro.getAutor().stream().map(DatosAutor::fechaDeNacimiento).collect(Collectors.joining(", "));
            String fallecimiento = libro.getAutor().stream().map(DatosAutor::fechaDeMuerte).collect(Collectors.joining(", "));
            String titulo = libro.getTitulo();

            if (!autoresConLibros.containsKey(autor)) {
                autoresConLibros.put(autor, new java.util.ArrayList<>());
                infoAutores.put(autor, new String[]{nacimiento, fallecimiento}); // Guardar info
            }
            autoresConLibros.get(autor).add(titulo);
        }

        if (autoresConLibros.isEmpty()) {
            System.out.println("No hay autores registrados.");
            return;
        }

        for (Map.Entry<String, List<String>> entry : autoresConLibros.entrySet()) {
            String autor = entry.getKey();
            String[] info = infoAutores.get(autor);

            System.out.println("--------------------------------------------------------------------------------");
            System.out.println("Autor: " + autor);

            if (info != null && info.length == 2) { // Comprobar si hay info
                System.out.println("Fecha de Nacimiento: " + info[0]);
                System.out.println("Fecha de Defuncion: " + info[1]);
            } else {
                System.out.println("Información del autor incompleta.");
            }

            System.out.print("Libros: [");
            List<String> titulos = entry.getValue();
            for (int i = 0; i < titulos.size(); i++) {
                System.out.print(titulos.get(i));
                if (i < titulos.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");
            System.out.println("--------------------------------------------------------------------------------");
            System.out.println();
        }
    }

    private void mostrarAutoresVivos() {
        System.out.println("Ingrese el año para buscar autores que estaban vivos:");
        int anio = teclado.nextInt();
        teclado.nextLine();

        List<Libro> libros2 = repositorio.obtenerAutoresVivosDurante(anio);

        if (libros2 == null || libros2.isEmpty()) {
            System.out.println("No se encontraron autores vivos durante el año " + anio + ".");
            return;
        }

        Set<String> autoresVistos = new HashSet<>(); // Conjunto para rastrear autores ya mostrados

        System.out.println("Autores vivos durante el año " + anio + ":");

        for (Libro libro : libros2) {
            for (DatosAutor autor : libro.getAutor()) {
                String nombreAutor = autor.nombre();

                if (!autoresVistos.contains(nombreAutor)) { // Comprobar si el autor ya se mostró
                    autoresVistos.add(nombreAutor); // Agregar el autor al conjunto de autores vistos

                    System.out.println("--------------------------------------------------------------------------------");
                    System.out.println("Autor: " + nombreAutor);
                    System.out.println("Fecha de nacimiento: " + autor.fechaDeNacimiento());
                    System.out.println("Fecha de fallecimiento: " + (autor.fechaDeMuerte() == null ? "N/A" : autor.fechaDeMuerte()));

                    System.out.print("Libros: [");
                    //Recorrer los libros del autor actual
                    List<String> librosDelAutor = new java.util.ArrayList<>();
                    for(Libro libroAux : libros2){
                        for(DatosAutor autorAux : libroAux.getAutor()){
                            if(autorAux.nombre().equals(nombreAutor)){
                                librosDelAutor.add(libroAux.getTitulo());
                            }
                        }
                    }
                    for (int i = 0; i < librosDelAutor.size(); i++) {
                        System.out.print(librosDelAutor.get(i));
                        if (i < librosDelAutor.size() - 1) {
                            System.out.print(", ");
                        }
                    }
                    System.out.println("]");
                    System.out.println("--------------------------------------------------------------------------------");

                }
            }
        }
    }



    private void mostrarLibrosPorIdioma() {
        System.out.println("*******SELECCIONE El IDIOMA*********");

        // Mostrar el menú con los idiomas disponibles
        Idioma[] idiomas = Idioma.values();
        for (int i = 0; i < idiomas.length; i++) {
            System.out.printf("%d - %s\n", i + 1, idiomas[i].getName());
        }

        // Pedir al usuario que seleccione un idioma
        int opcion;
        do {
            System.out.print("Ingrese el número del idioma deseado: ");
            opcion = teclado.nextInt();
            teclado.nextLine(); // Limpiar el buffer
        } while (opcion < 1 || opcion > idiomas.length);

        // Obtener el idioma seleccionado
        Idioma idiomaSeleccionado = idiomas[opcion - 1];
        System.out.println("Has seleccionado: " + idiomaSeleccionado.getName());

        // Buscar libros en el idioma seleccionado
        List<Libro> librosPorIdioma = repositorio.obtenerLibrosPorIdioma(idiomaSeleccionado.getCode());

        if (librosPorIdioma == null || librosPorIdioma.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma seleccionado.");
            return;
        }

        System.out.println("Libros encontrados en " + idiomaSeleccionado.getName() + ":");

        for (Libro libro : librosPorIdioma) {
            System.out.println("--------------------------------------------------------------------------------");
            System.out.println("                             LIBROS EN"+libro.getIdiomas());
            System.out.println("Título: " + libro.getTitulo());

            String autores = libro.getAutor().stream().map(DatosAutor::nombre).collect(Collectors.joining(", "));
            System.out.println("Autor: " + autores);

            System.out.println("Idioma: " + libro.getIdiomas());
            System.out.println("Número de descargas: " + libro.getNumeroDeDescargas());
            System.out.println("--------------------------------------------------------------------------------");
            System.out.println();
        }
    }


}