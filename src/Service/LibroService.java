
package Service;

import Dao.AutorDAO;
import Dao.EditorialDAO;
import Dao.LibroDAO;
import Modelo.Autor;
import Modelo.Editorial;
import Modelo.Libro;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class LibroService {

    private LibroDAO libroDAO;
    private AutorDAO autorDAO;
    private EditorialDAO editorialDAO;

    public LibroService() {
        this.libroDAO = new LibroDAO();
        this.autorDAO = new AutorDAO();
        this.editorialDAO = new EditorialDAO();
    }

    public boolean registrarLibro(Libro libro) {
        System.out.println("Servicio: Intentando registrar libro " + libro.getTitulo());
        if (libro.getIsbn() == null || libro.getIsbn().isEmpty()) {
            System.out.println("Error: ISBN del libro no puede ser vacio.");
            return false;
        }
        if (libroDAO.obtenerLibroPorIsbn(libro.getIsbn()) != null) {
            System.out.println("Error: Libro con ISBN " + libro.getIsbn() + " ya existe.");
            return false;
        }
        Autor autor = autorDAO.obtenerAutorPorId(libro.getIdAutorPrincipal());
        if (autor == null) {
            System.out.println("Error: Autor con ID " + libro.getIdAutorPrincipal()+ " no encontrado.");
            return false;
        }
        Editorial editorial = editorialDAO.obtenerEditorialPorId(libro.getIdEditorial());
        if (editorial == null) {
            System.out.println("Error: Editorial con ID " + libro.getIdEditorial() + " no encontrada.");
            return false;
        }

        return libroDAO.guardarLibro(libro);
    }

    public boolean actualizarLibro(Libro libro) {
        System.out.println("Servicio: Intentando actualizar libro " + libro.getTitulo());
        if (libro.getIsbn() == null || libro.getIsbn().isEmpty()) {
            System.out.println("Error: ISBN del libro no puede ser vacio.");
            return false;
        }
        return libroDAO.actualizarLibro(libro);
    }

    public boolean eliminarLibro(String isbnLibro) {
        System.out.println("Servicio: Intentando eliminar libro con ISBN " + isbnLibro);
        return libroDAO.eliminarLibro(isbnLibro);
    }

    // BUSQUEDA
    public List<Libro> buscarLibrosPorTitulo(String titulo) {
        System.out.println("Servicio: Buscando libros por titulo que contenga " + titulo);
        List<Libro> todosLosLibros = libroDAO.obtenerTodosLosLibros();
        List<Libro> resultados = new ArrayList<>();
        for (Libro libro : todosLosLibros) {
            if (libro.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                resultados.add(libro);
            }
        }
        return resultados;
    }

    // BUSQUEDA
    public List<Libro> buscarLibrosPorNombreAutor(String nombreAutor) {
        System.out.println("Servicio: Buscando libros por nombre de autor que contenga " + nombreAutor);
        List<Libro> resultados = new ArrayList<>();
        List<Autor> autoresCoincidentes = autorDAO.buscarAutoresPorNombre(nombreAutor);

        if (autoresCoincidentes.isEmpty()) {
            return resultados;
        }

        for (Autor autor : autoresCoincidentes) {
            List<Libro> librosDelAutor = libroDAO.buscarLibrosPorAutor(autor.getIdAutor());
            resultados.addAll(librosDelAutor);
        }
        return resultados;
    }
    public List<Libro> buscarLibrosPorAutor(int idAutor) {
        return libroDAO.obtenerLibrosPorAutor(idAutor); // O el nombre que tengas en tu DAO
    }

    public Libro obtenerLibroPorIsbn(String isbn) {
        System.out.println("Servicio: Obteniendo libro por ISBN " + isbn);
        return libroDAO.obtenerLibroPorIsbn(isbn);
    }

    // ORDENAMIENTO y LISTAS ENLAZADAS 
    public List<Libro> obtenerCatalogoOrdenadoPorTitulo() {
        System.out.println("Servicio: Obteniendo catalogo ordenado por titulo.");
        List<Libro> todosLosLibros = new LinkedList<>(libroDAO.obtenerTodosLosLibros()); // Usando LinkedList

        Collections.sort(todosLosLibros, new Comparator<Libro>() {
            @Override
            public int compare(Libro l1, Libro l2) {
                return l1.getTitulo().compareToIgnoreCase(l2.getTitulo());
            }
        });
        return todosLosLibros;
    }

    // ORDENAMIENTO
    public List<Libro> obtenerLibrosDisponiblesOrdenadosPorAnio() {
        System.out.println("Servicio: Obteniendo libros disponibles ordenados por anio.");
        List<Libro> librosDisponibles = new ArrayList<>();
        List<Libro> todosLosLibros = libroDAO.obtenerTodosLosLibros(); // Puede ser LinkedList internamente

        for (Libro libro : todosLosLibros) {
            if (libro.getStockDisponible() > 0) {
                librosDisponibles.add(libro);
            }
        }
        Collections.sort(librosDisponibles, new Comparator<Libro>() {
            @Override
            public int compare(Libro l1, Libro l2) {
                return Integer.compare(l1.getAnioPublicacion(), l2.getAnioPublicacion());
            }
        });
        return librosDisponibles;
    }

    public static void main(String[] args) {
        LibroService libroService = new LibroService();
        AutorDAO autorDAO = new AutorDAO();
        EditorialDAO editorialDAO = new EditorialDAO();
        
        System.out.println("--- Probando registrar libro ---");
        Libro nuevoLibro = new Libro("9781234567891", "El Principito", 1, 1, 1943, 10, 10);
        if (libroService.registrarLibro(nuevoLibro)) {
            System.out.println("Libro 'El Principito' registrado con exito.");
        } else {
            System.out.println("Fallo al registrar 'El Principito' (ya existe o error).");
        }

        System.out.println("\n--- Probando busqueda por titulo ---");
        List<Libro> librosBuscados = libroService.buscarLibrosPorTitulo("principito");
        if (!librosBuscados.isEmpty()) {
            System.out.println("Resultados de busqueda para 'principito':");
            for (Libro l : librosBuscados) {
                System.out.println("- " + l.getTitulo() + " (ISBN: " + l.getIsbn() + ")");
            }
        } else {
            System.out.println("No se encontraron libros con 'principito'.");
        }

        System.out.println("\n--- Probando catalogo ordenado por titulo ---");
        List<Libro> catalogoOrdenado = libroService.obtenerCatalogoOrdenadoPorTitulo();
        if (!catalogoOrdenado.isEmpty()) {
            System.out.println("Catalogo de libros ordenado por titulo:");
            for (Libro l : catalogoOrdenado) {
                System.out.println("- " + l.getTitulo() + " (Anio: " + l.getAnioPublicacion() + ")");
            }
        } else {
            System.out.println("Catalogo vacio.");
        }

        System.out.println("\n--- Probando libros disponibles ordenados por anio ---");
        List<Libro> librosDisponibles = libroService.obtenerLibrosDisponiblesOrdenadosPorAnio();
        if (!librosDisponibles.isEmpty()) {
            System.out.println("Libros disponibles ordenados por anio:");
            for (Libro l : librosDisponibles) {
                System.out.println("- " + l.getTitulo() + " (Anio: " + l.getAnioPublicacion() + ", Stock: " + l.getStockDisponible() + ")");
            }
        } else {
            System.out.println("No hay libros disponibles.");
        }

        System.out.println("\n--- Probando actualizar stock de libro ---");
        Libro libroParaActualizar = libroService.obtenerLibroPorIsbn("9781234567891");
        if (libroParaActualizar != null) {
            libroParaActualizar.setStockDisponible(libroParaActualizar.getStockDisponible() - 1);
            if (libroService.actualizarLibro(libroParaActualizar)) {
                System.out.println("Stock de 'El Principito' actualizado a " + libroParaActualizar.getStockDisponible());
            } else {
                System.out.println("Fallo al actualizar stock.");
            }
        }

        System.out.println("\n--- Probando eliminar libro ---");
        if (libroService.eliminarLibro("9781234567891")) {
            System.out.println("Libro 'El Principito' eliminado.");
        } else {
            System.out.println("Fallo al eliminar 'El Principito'.");
        }
    }
}
