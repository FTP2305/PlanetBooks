/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Dao.CategoriaDAO;
import Dao.LibroCategoriaDAO;
import Dao.LibroDAO;
import Modelo.Categoria;
import Modelo.Libro;
import java.util.ArrayList;
import java.util.List;

public class LibroCategoriaService {

    private LibroCategoriaDAO libroCategoriaDAO;
    private LibroDAO libroDAO;
    private CategoriaDAO categoriaDAO;

    public LibroCategoriaService() {
        this.libroCategoriaDAO = new LibroCategoriaDAO();
        this.libroDAO = new LibroDAO();
        this.categoriaDAO = new CategoriaDAO();
    }

    public boolean asociarLibroConCategoria(String isbnLibro, int idCategoria) {
        System.out.println("Servicio: Intentando asociar libro '" + isbnLibro + "' con categoría ID " + idCategoria);

        if (libroDAO.obtenerLibroPorIsbn(isbnLibro) == null) {
            System.out.println("Error: Libro con ISBN '" + isbnLibro + "' no encontrado.");
            return false;
        }

        if (categoriaDAO.obtenerCategoriaPorId(idCategoria) == null) {
            System.out.println("Error: Categoría con ID " + idCategoria + " no encontrada.");
            return false;
        }

        List<String> librosEnCategoria = libroCategoriaDAO.obtenerLibrosEnCategoria(idCategoria);
        if (librosEnCategoria.contains(isbnLibro)) {
            System.out.println("Advertencia: El libro '" + isbnLibro + "' ya está asociado con la categoría ID " + idCategoria + ".");
            return true; 
        }

        return libroCategoriaDAO.asociarLibroCategoria(isbnLibro, idCategoria);
    }

    
    public boolean desasociarLibroDeCategoria(String isbnLibro, int idCategoria) {
        System.out.println("Servicio: Intentando desasociar libro '" + isbnLibro + "' de categoría ID " + idCategoria);
        return libroCategoriaDAO.desasociarLibroCategoria(isbnLibro, idCategoria);
    }

   
    public List<Categoria> obtenerCategoriasDeLibro(String isbnLibro) {
        System.out.println("Servicio: Obteniendo categorías para el libro con ISBN '" + isbnLibro + "'");
        List<Integer> idsCategorias = libroCategoriaDAO.obtenerCategoriasDeLibro(isbnLibro);
        List<Categoria> categorias = new ArrayList<>();
        for (Integer id : idsCategorias) {
            Categoria categoria = categoriaDAO.obtenerCategoriaPorId(id);
            if (categoria != null) {
                categorias.add(categoria);
            }
        }
        return categorias;
    }

    public List<Libro> obtenerLibrosEnCategoria(int idCategoria) {
        System.out.println("Servicio: Obteniendo libros en categoría ID " + idCategoria);
        List<String> isbns = libroCategoriaDAO.obtenerLibrosEnCategoria(idCategoria);
        List<Libro> libros = new ArrayList<>();
        for (String isbn : isbns) {
            Libro libro = libroDAO.obtenerLibroPorIsbn(isbn);
            if (libro != null) {
                libros.add(libro);
            }
        }
        return libros;
    }
}
