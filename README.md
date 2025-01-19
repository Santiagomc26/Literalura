# Literalura

## Descripción  

**Literalura** es una aplicación diseñada para integrar datos de libros y autores desde la API de Gutendex, permitiendo su almacenamiento y consulta en una base de datos personalizada. El proyecto está orientado a facilitar la gestión y análisis de información literaria mediante opciones interactivas para el usuario.  

## Funcionalidades  

### Gestión de datos literarios  
- **Libros:**  
  - Registra libros obtenidos desde la API.  
  - Evita duplicados mediante validaciones internas.  
  - Lista libros existentes en la base de datos por idioma.  

- **Autores:**  
  - Muestra información como nombre, fecha de nacimiento y deceso (si aplica).  
  - Permite buscar autores que estaban vivos en un año específico.  

### Estadísticas  
- Filtra libros por idioma según las opciones disponibles.  
- Realiza consultas avanzadas, como el número de libros asociados a un idioma o el promedio de datos obtenidos.  

### Menú interactivo  
El programa se inicia con un menú de opciones que incluye:  
1. Buscar un libro específico.  
2. Consultar la lista de libros almacenados.  
3. Listar autores registrados en la base de datos.  
4. Identificar autores vivos en un año ingresado.  
5. Clasificar libros por idioma.  
6. Salir del programa.  
## Uso

### Inicio:
Al iniciar la aplicación, se presenta un menú con las siguientes opciones para interactuar con la base de datos de libros y autores.

### Opciones del Menú:
1. **Buscar libro:** Permite buscar un libro en la base de datos.
2. **Listar libros registrados:** Muestra todos los libros que están registrados en la base de datos.
3. **Listar autores registrados:** Muestra todos los autores registrados en la base de datos.
4. **Listar autores vivos en determinado año:** Permite ingresar un año para obtener la lista de autores que estaban vivos durante ese año.
5. **Listar libros por idioma:** Permite filtrar los libros por idioma (es, en, fr, pt).
0. **Salir:** Cierra la aplicación.

Al seleccionar una opción, el sistema procesará la solicitud y devolverá los resultados correspondientes.

## Tecnologías Utilizadas  

- **Lenguaje de Programación:** Java 17  
- **Framework:** Maven para la gestión de dependencias y compilación.  
- **Base de Datos:** PostgreSQL  
- **API:** Gutendex para el suministro de datos externos.  
- **Persistencia de Datos:** JPA (Java Persistence API) con Hibernate como implementación para la gestión de la persistencia de datos.


## Requisitos Previos  

Antes de ejecutar el proyecto, asegúrate de contar con lo siguiente:  
1. **Java** (versión 8 o superior).  
2. **Maven** instalado.  
3. Acceso a un sistema de base de datos relacional (por ejemplo, PostgreSQL).  


