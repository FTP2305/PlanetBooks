/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Libro;
import Service.LibroService;
import java.util.List;

public class LibroController {

    private LibroService libroService;

    public LibroController() {
        this.libroService = new LibroService();
    }

    public String crearLibro(Libro libro) {
        System.out.println("Controlador: Solicitud para crear libro.");
        if (libroService.registrarLibro(libro)) {
            return "Libro '" + libro.getTitulo() + "' registrado exitosamente.";
        } else {
            return "Error al registrar libro '" + libro.getTitulo() + "'. Verifique los datos (ISBN, Autor, Editorial).";
        }
    }

    public Libro obtenerLibroPorIsbn(String isbn) {
        System.out.println("Controlador: Solicitud para obtener libro con ISBN " + isbn + ".");
        Libro libro = libroService.obtenerLibroPorIsbn(isbn);
        if (libro != null) {
            System.out.println("Libro encontrado: " + libro.getTitulo());
            return libro;
        } else {
            System.out.println("Libro con ISBN " + isbn + " no encontrado.");
            return null;
        }
    }

    public List<Libro> buscarLibrosPorTitulo(String titulo) {
        System.out.println("Controlador: Solicitud para buscar libros por título que contenga '" + titulo + "'.");
        List<Libro> libros = libroService.buscarLibrosPorTitulo(titulo);
        System.out.println("Se encontraron " + libros.size() + " libros que coinciden por título.");
        return libros;
    }

    public List<Libro> buscarLibrosPorNombreAutor(String nombreAutor) {
        System.out.println("Controlador: Solicitud para buscar libros por nombre de autor que contenga '" + nombreAutor + "'.");
        List<Libro> libros = libroService.buscarLibrosPorNombreAutor(nombreAutor);
        System.out.println("Se encontraron " + libros.size() + " libros que coinciden por autor.");
        return libros;
    }
     public List<Libro> buscarLibrosPorAutor(int idAutor) { 
        return libroService.buscarLibrosPorAutor(idAutor);
    }

    public String actualizarLibro(Libro libro) {
        System.out.println("Controlador: Solicitud para actualizar libro ISBN " + libro.getIsbn() + ".");
        if (libroService.actualizarLibro(libro)) {
            return "Libro ISBN " + libro.getIsbn() + " actualizado exitosamente.";
        } else {
            return "Error al actualizar libro ISBN " + libro.getIsbn() + ". Verifique los datos o si el libro existe.";
        }
    }

    public String eliminarLibro(String isbnLibro) {
        System.out.println("Controlador: Solicitud para eliminar libro con ISBN " + isbnLibro + ".");
        if (libroService.eliminarLibro(isbnLibro)) {
            return "Libro con ISBN " + isbnLibro + " eliminado exitosamente.";
        } else {
            return "Error al eliminar libro con ISBN " + isbnLibro + ". Podría tener préstamos asociados o no existe.";
        }
    }

    public List<Libro> obtenerCatalogoOrdenadoPorTitulo() {
        System.out.println("Controlador: Solicitud para obtener catálogo de libros ordenado por título.");
        List<Libro> libros = libroService.obtenerCatalogoOrdenadoPorTitulo();
        System.out.println("Se encontraron " + libros.size() + " libros en el catálogo.");
        return libros;
    }

    public List<Libro> obtenerLibrosDisponiblesOrdenadosPorAnio() {
        System.out.println("Controlador: Solicitud para obtener libros disponibles ordenados por año.");
        List<Libro> libros = libroService.obtenerLibrosDisponiblesOrdenadosPorAnio();
        System.out.println("Se encontraron " + libros.size() + " libros disponibles.");
        return libros;
    }
}
