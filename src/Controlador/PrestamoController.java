/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Prestamo;
import Service.PrestamoService;
import java.util.List;

public class PrestamoController {

    private PrestamoService prestamoService;

    public PrestamoController() {
        this.prestamoService = new PrestamoService();
    }

    public String registrarNuevoPrestamo(int idUsuario, String isbnLibro, int diasPrestamo) {
        System.out.println("Controlador: Solicitud para registrar nuevo préstamo.");
        Prestamo prestamoRegistrado = prestamoService.registrarNuevoPrestamo(idUsuario, isbnLibro, diasPrestamo);
        if (prestamoRegistrado != null) {
            return "Préstamo registrado exitosamente. ID: " + prestamoRegistrado.getIdPrestamo() + ". Estado: " + prestamoRegistrado.getEstadoPrestamo();
        } else {
            return "Error al registrar el préstamo. Verifique el usuario, libro y stock disponible.";
        }
    }

    public String registrarDevolucion(int idPrestamo) {
        System.out.println("Controlador: Solicitud para registrar devolución del préstamo ID " + idPrestamo + ".");
        if (prestamoService.registrarDevolucion(idPrestamo)) {
            return "Devolución del préstamo ID " + idPrestamo + " registrada exitosamente.";
        } else {
            return "Error al registrar la devolución del préstamo ID " + idPrestamo + ". Verifique si el préstamo existe y está activo.";
        }
    }
    public List<Prestamo> obtenerTodosLosPrestamos() {
        System.out.println("Controlador: Solicitud para obtener todos los préstamos.");
        return prestamoService.obtenerTodosLosPrestamos();
    }
    public String calcularEstadoPrestamoDetallado(int idPrestamo) {
        System.out.println("Controlador: Solicitud para calcular estado detallado del préstamo ID " + idPrestamo + ".");
        return "Estado detallado: " + prestamoService.calcularEstadoPrestamoDetallado(idPrestamo);
    }

    public List<Prestamo> obtenerPrestamosActivosPorUsuario(int idUsuario) {
        System.out.println("Controlador: Solicitud para obtener préstamos activos del usuario ID " + idUsuario + ".");
        List<Prestamo> prestamos = prestamoService.obtenerPrestamosActivosPorUsuario(idUsuario);
        System.out.println("Se encontraron " + prestamos.size() + " préstamos activos para el usuario ID " + idUsuario + ".");
        return prestamos;
    }

    public String deshacerUltimaAccion() {
        System.out.println("Controlador: Solicitud para deshacer la última acción.");
        return prestamoService.deshacerUltimaAccion();
    }

    public String procesarSiguientePrestamoPendiente() {
        System.out.println("Controlador: Solicitud para procesar el siguiente préstamo pendiente.");
        prestamoService.procesarSiguientePrestamoPendiente();
        return "Procesamiento de siguiente préstamo pendiente iniciado.";
    }
}
