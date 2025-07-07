/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Categoria;
import Modelo.Libro;
import Service.LibroCategoriaService;
import java.util.List;

/**
 *
 * @author felix
 */
public class LibroCategoriaController {

    private LibroCategoriaService libroCategoriaService;

    public LibroCategoriaController() {
        this.libroCategoriaService = new LibroCategoriaService();
    }

    public String asociarLibroConCategoria(String isbnLibro, int idCategoria) {
        System.out.println("Controlador: Solicitud para asociar libro '" + isbnLibro + "' con categoría ID " + idCategoria + ".");
        if (libroCategoriaService.asociarLibroConCategoria(isbnLibro, idCategoria)) {
            return "Asociación entre libro '" + isbnLibro + "' y categoría ID " + idCategoria + " exitosa.";
        } else {
            return "Error al asociar libro '" + isbnLibro + "' con categoría ID " + idCategoria + ". Verifique los IDs o si ya existe la asociación.";
        }
    }

    public String desasociarLibroDeCategoria(String isbnLibro, int idCategoria) {
        System.out.println("Controlador: Solicitud para desasociar libro '" + isbnLibro + "' de categoría ID " + idCategoria + ".");
        if (libroCategoriaService.desasociarLibroDeCategoria(isbnLibro, idCategoria)) {
            return "Desasociación entre libro '" + isbnLibro + "' y categoría ID " + idCategoria + " exitosa.";
        } else {
            return "Error al desasociar libro '" + isbnLibro + "' de categoría ID " + idCategoria + ". La asociación no existe.";
        }
    }

    public List<Categoria> obtenerCategoriasDeLibro(String isbnLibro) {
        System.out.println("Controlador: Solicitud para obtener categorías del libro con ISBN '" + isbnLibro + "'.");
        List<Categoria> categorias = libroCategoriaService.obtenerCategoriasDeLibro(isbnLibro);
        System.out.println("Se encontraron " + categorias.size() + " categorías para el libro '" + isbnLibro + "'.");
        return categorias;
    }

    public List<Libro> obtenerLibrosEnCategoria(int idCategoria) {
        System.out.println("Controlador: Solicitud para obtener libros en categoría ID " + idCategoria + ".");
        List<Libro> libros = libroCategoriaService.obtenerLibrosEnCategoria(idCategoria);
        System.out.println("Se encontraron " + libros.size() + " libros en la categoría ID " + idCategoria + ".");
        return libros;
    }
}
