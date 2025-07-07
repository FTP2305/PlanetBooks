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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CategoriaService {

    private CategoriaDAO categoriaDAO;
    private LibroCategoriaDAO libroCategoriaDAO;
    private LibroDAO libroDAO;

    public CategoriaService() {
        this.categoriaDAO = new CategoriaDAO();
        this.libroCategoriaDAO = new LibroCategoriaDAO();
        this.libroDAO = new LibroDAO();
    }

    public boolean registrarCategoria(Categoria categoria) {
        System.out.println("Servicio: Intentando registrar categoria " + categoria.getNombreCategoria());
        return categoriaDAO.guardarCategoria(categoria);
    }

    public boolean actualizarCategoria(Categoria categoria) {
        System.out.println("Servicio: Intentando actualizar categoria " + categoria.getNombreCategoria());
        return categoriaDAO.actualizarCategoria(categoria);
    }

    public boolean eliminarCategoria(int idCategoria) {
        System.out.println("Servicio: Intentando eliminar categoria ID " + idCategoria);
        return categoriaDAO.eliminarCategoria(idCategoria);
    }

    public List<Categoria> obtenerTodasCategorias() {
        System.out.println("Servicio: Obteniendo todas las categorias");
        return categoriaDAO.obtenerTodasCategorias();
    }

    public Set<Libro> buscarLibrosPorCategoriaConSubcategorias(int idCategoriaPrincipal) {
        System.out.println("Servicio: Buscando libros para categoria ID " + idCategoriaPrincipal + " y subcategorias");
        Set<Libro> librosEncontrados = new HashSet<>();

        Categoria categoriaRaiz = categoriaDAO.obtenerCategoriaPorId(idCategoriaPrincipal);
        if (categoriaRaiz == null) {
            System.out.println("Categoria principal con ID " + idCategoriaPrincipal + " no encontrada.");
            return librosEncontrados;
        }
        buscarLibrosRecursivo(idCategoriaPrincipal, librosEncontrados);

        return librosEncontrados;
    }

    private void buscarLibrosRecursivo(int idCategoriaActual, Set<Libro> librosAcumulados) {
        List<String> isbnsEnEstaCategoria = libroCategoriaDAO.obtenerLibrosEnCategoria(idCategoriaActual);
        for (String isbn : isbnsEnEstaCategoria) {
            Libro libro = libroDAO.obtenerLibroPorIsbn(isbn);
            if (libro != null) {
                librosAcumulados.add(libro); 
            }
        }

        List<Categoria> todasCategorias = categoriaDAO.obtenerTodasCategorias();
        for (Categoria subCategoria : todasCategorias) {
            if (subCategoria.getIdCategoriaPadre() != null && subCategoria.getIdCategoriaPadre().equals(idCategoriaActual)) {
                buscarLibrosRecursivo(subCategoria.getIdCategoria(), librosAcumulados);
            }
        }
    }


    public static void main(String[] args) {
        CategoriaService categoriaService = new CategoriaService();

        System.out.println("--- Probando registrar categorias ---");
        Categoria ficcion = new Categoria(0, "Ficcion", null);
        categoriaService.registrarCategoria(ficcion); 
        
        Categoria cienciaFiccion = new Categoria(0, "Ciencia Ficcion", ficcion.getIdCategoria());
        categoriaService.registrarCategoria(cienciaFiccion); 
        
        Categoria fantasia = new Categoria(0, "Fantasia", ficcion.getIdCategoria());
        categoriaService.registrarCategoria(fantasia); 

        Categoria terror = new Categoria(0, "Terror", null);
        categoriaService.registrarCategoria(terror); 


        LibroDAO libroDAO = new LibroDAO();
        LibroCategoriaDAO libroCategoriaDAO = new LibroCategoriaDAO();

        Libro libroSciFi = new Libro("9789999999991", "Dune", 1, 1, 1965, 5, 5);
        libroDAO.guardarLibro(libroSciFi);
        Libro libroFantasia = new Libro("9789999999992", "El Senor de los Anillos", 1, 1, 1954, 7, 7);
        libroDAO.guardarLibro(libroFantasia);
        Libro libroTerror = new Libro("9789999999993", "It", 1, 1, 1986, 3, 3);
        libroDAO.guardarLibro(libroTerror);
        libroCategoriaDAO.agregarLibroACategoria(libroSciFi.getIsbn(), ficcion.getIdCategoria());
        libroCategoriaDAO.agregarLibroACategoria(libroSciFi.getIsbn(), cienciaFiccion.getIdCategoria());
        libroCategoriaDAO.agregarLibroACategoria(libroFantasia.getIsbn(), ficcion.getIdCategoria());
        libroCategoriaDAO.agregarLibroACategoria(libroFantasia.getIsbn(), fantasia.getIdCategoria());
        libroCategoriaDAO.agregarLibroACategoria(libroTerror.getIsbn(), terror.getIdCategoria());


        System.out.println("\n--- Probando busqueda de libros por categoria y subcategorias (Ficcion) ---");
        int idFiccion = -1;
        List<Categoria> todas = categoriaService.obtenerTodasCategorias();
        for (Categoria cat : todas) {
            if (cat.getNombreCategoria().equals("Ficcion")) {
                idFiccion = cat.getIdCategoria();
                break;
            }
        }

        if (idFiccion != -1) {
            Set<Libro> librosEnFiccionYSub = categoriaService.buscarLibrosPorCategoriaConSubcategorias(idFiccion);
            if (!librosEnFiccionYSub.isEmpty()) {
                System.out.println("Libros en Ficcion y sus subcategorias:");
                for (Libro l : librosEnFiccionYSub) {
                    System.out.println("- " + l.getTitulo() + " (ISBN: " + l.getIsbn() + ")");
                }
            } else {
                System.out.println("No se encontraron libros en Ficcion y sus subcategorias.");
            }
        } else {
            System.out.println("No se pudo encontrar la categoria Ficcion. Asegurese de que exista.");
        }

        System.out.println("\n--- Probando busqueda de libros por categoria sin subcategorias (Terror) ---");
        int idTerror = -1;
        for (Categoria cat : todas) {
            if (cat.getNombreCategoria().equals("Terror")) {
                idTerror = cat.getIdCategoria();
                break;
            }
        }

        if (idTerror != -1) {
            Set<Libro> librosEnTerror = categoriaService.buscarLibrosPorCategoriaConSubcategorias(idTerror);
            if (!librosEnTerror.isEmpty()) {
                System.out.println("Libros en Terror:");
                for (Libro l : librosEnTerror) {
                    System.out.println("- " + l.getTitulo() + " (ISBN: " + l.getIsbn() + ")");
                }
            } else {
                System.out.println("No se encontraron libros en Terror.");
            }
        }
        else {
            System.out.println("No se pudo encontrar la categoria Terror. Asegurese de que exista.");
        }

    }
}
