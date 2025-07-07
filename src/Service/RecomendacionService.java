/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Dao.LibroDAO;
import Dao.PrestamoDAO;
import Dao.UsuarioDAO;
import Modelo.Libro;
import Modelo.Prestamo;
import Modelo.Usuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class RecomendacionService {

    private PrestamoDAO prestamoDAO;
    private LibroDAO libroDAO;

    public RecomendacionService() {
        this.prestamoDAO = new PrestamoDAO();
        this.libroDAO = new LibroDAO();
    }

    private Map<String, Set<String>> construirGrafoCoPrestamo() {
        Map<String, Set<String>> grafo = new HashMap<>();
        List<Prestamo> todosLosPrestamos = prestamoDAO.obtenerTodosLosPrestamos();

        Map<Integer, List<String>> librosPorUsuario = new HashMap<>();
        for (Prestamo prestamo : todosLosPrestamos) {
            if (prestamo.getEstadoPrestamo().equals("ACTIVO") || prestamo.getEstadoPrestamo().equals("DEVUELTO")) {
                librosPorUsuario
                    .computeIfAbsent(prestamo.getIdUsuario(), k -> new ArrayList<>())
                    .add(prestamo.getIsbnLibro());
            }
        }

        for (List<String> isbnsDelUsuario : librosPorUsuario.values()) {
            if (isbnsDelUsuario.size() > 1) { // Solo si el usuario presto mas de un libro
                for (int i = 0; i < isbnsDelUsuario.size(); i++) {
                    String libro1 = isbnsDelUsuario.get(i);
                    grafo.computeIfAbsent(libro1, k -> new HashSet<>());
                    for (int j = 0; j < isbnsDelUsuario.size(); j++) {
                        String libro2 = isbnsDelUsuario.get(j);
                        if (!libro1.equals(libro2)) {
                            grafo.get(libro1).add(libro2);
                        }
                    }
                }
            }
        }
        System.out.println("Servicio: Grafo de co prestamo construido con " + grafo.size() + " nodos.");
        return grafo;
    }
     public List<Libro> obtenerRecomendacionesGenerales() {
        List<Libro> todosLosLibros = libroDAO.obtenerCatalogoOrdenadoPorTitulo(); // Llama al DAO
        if (todosLosLibros == null) {
            return new ArrayList<>();
        }
        return todosLosLibros.stream().limit(5).collect(Collectors.toList());
    }

    public List<Libro> obtenerRecomendacionesParaUsuario(int idUsuario) {
        return obtenerRecomendacionesGenerales();
    }

    public List<Libro> recomendarLibrosPorLibro(String isbnLibroOrigen, int cantidadMaxima) {
        System.out.println("Servicio: Recomendando libros basados en " + isbnLibroOrigen);
        Map<String, Set<String>> grafoCoPrestamo = construirGrafoCoPrestamo();
        Set<String> isbnsRecomendados = new HashSet<>();

        Set<String> vecinos = grafoCoPrestamo.get(isbnLibroOrigen);
        if (vecinos != null) {
            for (String vecinoIsbn : vecinos) {
                if (!vecinoIsbn.equals(isbnLibroOrigen)) {
                    isbnsRecomendados.add(vecinoIsbn);
                    if (isbnsRecomendados.size() >= cantidadMaxima) {
                        break;
                    }
                }
            }
        }

        List<Libro> recomendaciones = new ArrayList<>();
        for (String isbn : isbnsRecomendados) {
            Libro libro = libroDAO.obtenerLibroPorIsbn(isbn);
            if (libro != null) {
                recomendaciones.add(libro);
            }
            if (recomendaciones.size() >= cantidadMaxima) {
                break;
            }
        }
        System.out.println("Servicio: " + recomendaciones.size() + " recomendaciones encontradas.");
        return recomendaciones;
    }

    public List<Libro> recomendarLibrosPorUsuario(int idUsuario, int cantidadMaxima) {
        System.out.println("Servicio: Recomendando libros para usuario ID " + idUsuario);
        Set<Libro> recomendacionesFinales = new HashSet<>();
        
        List<Prestamo> prestamosDelUsuario = prestamoDAO.obtenerPrestamosPorUsuario(idUsuario);
        if (prestamosDelUsuario.isEmpty()) {
            System.out.println("Usuario no tiene prestamos registrados para recomendar.");
            return new ArrayList<>();
        }

        for (Prestamo p : prestamosDelUsuario) {
            List<Libro> recomendacionesDeEsteLibro = recomendarLibrosPorLibro(p.getIsbnLibro(), cantidadMaxima);
            for (Libro l : recomendacionesDeEsteLibro) {
                boolean yaPresto = false;
                for (Prestamo p2 : prestamosDelUsuario) {
                    if (p2.getIsbnLibro().equals(l.getIsbn())) {
                        yaPresto = true;
                        break;
                    }
                }
                if (!yaPresto) {
                    recomendacionesFinales.add(l);
                }
            }
            if (recomendacionesFinales.size() >= cantidadMaxima) {
                break;
            }
        }
        
        System.out.println("Servicio: " + recomendacionesFinales.size() + " recomendaciones para el usuario.");
        return new ArrayList<>(recomendacionesFinales);
    }

    public static void main(String[] args) {
        RecomendacionService recomendacionService = new RecomendacionService();
        LibroDAO libroDAO = new LibroDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        PrestamoService prestamoService = new PrestamoService(); 


        System.out.println("--- Preparando datos de prueba para recomendaciones ---");

        // Usuarios de prueba
        Usuario user1 = new Usuario(0, "Ana Garcia", "ana.g@example.com");
        Usuario user2 = new Usuario(0, "Luis Perez", "luis.p@example.com");
        Usuario user3 = new Usuario(0, "Marta Diaz", "marta.d@example.com");
        
        usuarioDAO.guardarUsuario(user1); 
        usuarioDAO.guardarUsuario(user2); 
        usuarioDAO.guardarUsuario(user3); 

        Libro libroA = new Libro("9781000000001", "Crimen y Castigo", 1, 1, 1866, 5, 5);
        libroDAO.guardarLibro(libroA);
        Libro libroB = new Libro("9781000000002", "Don Quijote", 1, 1, 1605, 5, 5);
        libroDAO.guardarLibro(libroB);
        Libro libroC = new Libro("9781000000003", "Cien Anos de Soledad", 1, 1, 1967, 5, 5);
        libroDAO.guardarLibro(libroC);
        Libro libroD = new Libro("9781000000004", "El Gran Gatsby", 1, 1, 1925, 5, 5);
        libroDAO.guardarLibro(libroD);
        Libro libroE = new Libro("9781000000005", "1984", 1, 1, 1949, 5, 5);
        libroDAO.guardarLibro(libroE);

        System.out.println("Registrando prestamos para construir el grafo...");
        prestamoService.registrarNuevoPrestamo(user1.getIdUsuario(), libroA.getIsbn(), 7); // Ana presta A
        prestamoService.registrarNuevoPrestamo(user1.getIdUsuario(), libroB.getIsbn(), 7); // Ana presta B
        
        prestamoService.registrarNuevoPrestamo(user2.getIdUsuario(), libroB.getIsbn(), 7); // Luis presta B
        prestamoService.registrarNuevoPrestamo(user2.getIdUsuario(), libroC.getIsbn(), 7); // Luis presta C

        prestamoService.registrarNuevoPrestamo(user3.getIdUsuario(), libroA.getIsbn(), 7); // Marta presta A
        prestamoService.registrarNuevoPrestamo(user3.getIdUsuario(), libroD.getIsbn(), 7); // Marta presta D

        prestamoService.registrarNuevoPrestamo(user1.getIdUsuario(), libroE.getIsbn(), 7); // Ana presta E
        

        System.out.println("\n--- Probando recomendacion por libro (Crimen y Castigo) ---");
        List<Libro> recomendacionesPorLibro = recomendacionService.recomendarLibrosPorLibro(libroA.getIsbn(), 2);
        if (!recomendacionesPorLibro.isEmpty()) {
            System.out.println("Si te gusto " + libroA.getTitulo() + " quiza te guste:");
            for (Libro l : recomendacionesPorLibro) {
                System.out.println("- " + l.getTitulo());
            }
        } else {
            System.out.println("No hay recomendaciones para " + libroA.getTitulo());
        }

        System.out.println("\n--- Probando recomendacion por usuario (Ana, ID " + user1.getIdUsuario() + ") ---");
        List<Libro> recomendacionesParaAna = recomendacionService.recomendarLibrosPorUsuario(user1.getIdUsuario(), 3);
        if (!recomendacionesParaAna.isEmpty()) {
            System.out.println("Recomendaciones para Ana:");
            for (Libro l : recomendacionesParaAna) {
                System.out.println("- " + l.getTitulo());
            }
        } else {
            System.out.println("No hay recomendaciones para Ana.");
        }
    }
}
