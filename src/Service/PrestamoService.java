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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;

public class PrestamoService {

    private PrestamoDAO prestamoDAO;
    private LibroDAO libroDAO;
    private UsuarioDAO usuarioDAO;

    private Stack<String> historialAcciones;
    private Queue<Prestamo> colaPrestamosPendientes;


    public PrestamoService() {
        this.prestamoDAO = new PrestamoDAO();
        this.libroDAO = new LibroDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.historialAcciones = new Stack<>();
        this.colaPrestamosPendientes = new LinkedList<>();
    }
    
    public Prestamo registrarNuevoPrestamo(int idUsuario, String isbnLibro, int diasPrestamo) {
        System.out.println("Servicio: Intentando registrar prestamo para usuario " + idUsuario + " libro " + isbnLibro);

        Libro libro = libroDAO.obtenerLibroPorIsbn(isbnLibro);
        if (libro == null) {
            System.out.println("Error: Libro no encontrado con ISBN " + isbnLibro);
            return null; 
        }

        if (libro.getStockDisponible() <= 0) {
            System.out.println("Error: Libro " + libro.getTitulo() + " sin stock disponible.");
            return null; 
        }

        LocalDateTime fechaPrestamo = LocalDateTime.now();
        LocalDateTime fechaDevolucionEsperada = fechaPrestamo.plusDays(diasPrestamo);
        
        Prestamo nuevoPrestamo = new Prestamo(0, idUsuario, isbnLibro, fechaPrestamo, fechaDevolucionEsperada, "ACTIVO");

        boolean prestamoGuardado = prestamoDAO.guardarPrestamo(nuevoPrestamo);
        
        if (prestamoGuardado) {
            boolean stockActualizado = libroDAO.actualizarStock(isbnLibro, libro.getStockDisponible() - 1);
            if (stockActualizado) {
                System.out.println("Prestamo registrado y stock actualizado para " + libro.getTitulo());
                historialAcciones.push("PRESTAMO " + nuevoPrestamo.getIdPrestamo());
                colaPrestamosPendientes.offer(nuevoPrestamo);
                return nuevoPrestamo; 
            } else {
                System.out.println("Error critico: Prestamo guardado pero stock NO actualizado. Debe deshacerse el prestamo.");
                return null; 
            }
        } else {
            System.out.println("Fallo al guardar el prestamo en la BD.");
            return null; 
        }
    }
     public List<Prestamo> obtenerTodosLosPrestamos() {
        System.out.println("Servicio: Obteniendo todos los préstamos.");
        return prestamoDAO.obtenerTodosLosPrestamos();
    }

    public boolean registrarDevolucion(int idPrestamo) {
        System.out.println("Servicio: Intentando registrar devolucion para prestamo ID " + idPrestamo);

        Prestamo prestamo = prestamoDAO.obtenerPrestamoPorId(idPrestamo);
        if (prestamo == null) {
            System.out.println("Error: Prestamo ID " + idPrestamo + " no encontrado.");
            return false;
        }

        if (!prestamo.getEstadoPrestamo().equals("ACTIVO")) {
            System.out.println("Error: El prestamo ID " + idPrestamo + " no esta ACTIVO para ser devuelto.");
            return false;
        }

        prestamo.setFechaDevolucionReal(LocalDateTime.now());
        prestamo.setEstadoPrestamo("DEVUELTO");

        boolean prestamoActualizado = prestamoDAO.actualizarPrestamo(prestamo);

        if (prestamoActualizado) {
            Libro libro = libroDAO.obtenerLibroPorIsbn(prestamo.getIsbnLibro());
            if (libro != null) {
                boolean stockActualizado = libroDAO.actualizarStock(libro.getIsbn(), libro.getStockDisponible() + 1);
                if (stockActualizado) {
                    System.out.println("Devolucion registrada y stock actualizado para libro " + libro.getTitulo());
                    historialAcciones.push("DEVOLUCION " + prestamo.getIdPrestamo());
                    return true;
                } else {
                    System.out.println("Error critico: Devolucion registrada pero stock NO actualizado.");
                    return false;
                }
            } else {
                System.out.println("Error: Libro del prestamo no encontrado para actualizar stock.");
                return false;
            }
        } else {
            System.out.println("Fallo al actualizar el prestamo en la BD.");
            return false;
        }
    }

    public String calcularEstadoPrestamoDetallado(int idPrestamo) {
        Prestamo prestamo = prestamoDAO.obtenerPrestamoPorId(idPrestamo);
        if (prestamo == null) {
            return "No Existe";
        }

        if (prestamo.getEstadoPrestamo().equals("DEVUELTO")) {
            return "Devuelto";
        }

        if (LocalDateTime.now().isAfter(prestamo.getFechaDevolucionEsperada())) {
            return "ATRASADO";
        } else {
            return "Activo y a tiempo";
        }
    }

    public List<Prestamo> obtenerPrestamosActivosPorUsuario(int idUsuario) {
        List<Prestamo> todosPrestamos = prestamoDAO.obtenerPrestamosPorUsuario(idUsuario);
        List<Prestamo> activos = new ArrayList<>();
        for (Prestamo p : todosPrestamos) {
            if (p.getEstadoPrestamo().equals("ACTIVO")) {
                activos.add(p);
            }
        }
        return activos;
    }

    public String deshacerUltimaAccion() {
        if (!historialAcciones.isEmpty()) {
            String ultimaAccion = historialAcciones.pop();
            System.out.println("Deshaciendo ultima accion " + ultimaAccion);
            return "Deshecho: " + ultimaAccion;
        } else {
            System.out.println("Historial de acciones vacio.");
            return "Nada que deshacer.";
        }
    }

    public void procesarSiguientePrestamoPendiente() {
        if (!colaPrestamosPendientes.isEmpty()) {
            Prestamo prestamoAProcesar = colaPrestamosPendientes.poll();
            System.out.println("Procesando prestamo pendiente de la cola ID " + prestamoAProcesar.getIdPrestamo() + " para libro " + prestamoAProcesar.getIsbnLibro());
        } else {
            System.out.println("Cola de prestamos pendientes vacia.");
        }
    }

    
}
