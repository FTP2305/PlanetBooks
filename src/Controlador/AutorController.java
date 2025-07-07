/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Autor;
import Service.AutorService;
import java.util.List;

public class AutorController {

    private AutorService autorService;

    public AutorController() {
        this.autorService = new AutorService();
    }

    public String crearAutor(Autor autor) {
        System.out.println("Controlador: Solicitud para crear autor.");
        if (autorService.registrarAutor(autor)) {
            return "Autor '" + autor.getNombreCompleto() + "' registrado exitosamente.";
        } else {
            return "Error al registrar autor '" + autor.getNombreCompleto() + "'. Verifique los datos.";
        }
    }

    public Autor obtenerAutorPorId(int idAutor) {
        System.out.println("Controlador: Solicitud para obtener autor con ID " + idAutor + ".");
        Autor autor = autorService.obtenerAutorPorId(idAutor);
        if (autor != null) {
            System.out.println("Autor encontrado: " + autor.getNombreCompleto());
            return autor;
        } else {
            System.out.println("Autor con ID " + idAutor + " no encontrado.");
            return null;
        }
    }

    public List<Autor> obtenerTodosLosAutores() {
        System.out.println("Controlador: Solicitud para obtener todos los autores.");
        List<Autor> autores = autorService.obtenerTodosLosAutores();
        System.out.println("Se encontraron " + autores.size() + " autores.");
        return autores;
    }   
    public Autor buscarAutorPorNombre(String nombre) {
    return autorService.buscarAutorPorNombre(nombre);
    }

    public List<Autor> buscarAutoresPorNombre(String nombre) {
        System.out.println("Controlador: Solicitud para buscar autores por nombre que contenga '" + nombre + "'.");
        List<Autor> autores = autorService.buscarAutoresPorNombre(nombre);
        System.out.println("Se encontraron " + autores.size() + " autores que coinciden.");
        return autores;
    }

    public String actualizarAutor(Autor autor) {
        System.out.println("Controlador: Solicitud para actualizar autor ID " + autor.getIdAutor() + ".");
        if (autorService.actualizarAutor(autor)) {
            return "Autor ID " + autor.getIdAutor() + " actualizado exitosamente.";
        } else {
            return "Error al actualizar autor ID " + autor.getIdAutor() + ". Verifique los datos o si el autor existe.";
        }
    }

    public String eliminarAutor(int idAutor) {
        System.out.println("Controlador: Solicitud para eliminar autor ID " + idAutor + ".");
        if (autorService.eliminarAutor(idAutor)) {
            return "Autor ID " + idAutor + " eliminado exitosamente.";
        } else {
            return "Error al eliminar autor ID " + idAutor + ". Podría tener libros asociados o no existe.";
        }
    }
}
