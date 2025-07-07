/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Dao.AutorDAO;
import Modelo.Autor;
import java.util.List;

public class AutorService {

    private AutorDAO autorDAO;

    public AutorService() {
        this.autorDAO = new AutorDAO();
    }

    public boolean registrarAutor(Autor autor) {
        System.out.println("Servicio: Intentando registrar autor " + autor.getNombreCompleto());

        if (autor.getNombreCompleto() == null || autor.getNombreCompleto().trim().isEmpty()) {
            System.out.println("Error: El nombre del autor no puede ser vacío.");
            return false;
        }
        
        List<Autor> autoresExistentes = autorDAO.buscarAutoresPorNombre(autor.getNombreCompleto());
        if (!autoresExistentes.isEmpty()) {
             System.out.println("Error: Ya existe un autor con el nombre " + autor.getNombreCompleto());
             return false;
         }

        return autorDAO.guardarAutor(autor);
    }

    public Autor obtenerAutorPorId(int idAutor) {
        System.out.println("Servicio: Obteniendo autor por ID " + idAutor);
        return autorDAO.obtenerAutorPorId(idAutor);
    }

    public List<Autor> obtenerTodosLosAutores() {
        System.out.println("Servicio: Obteniendo todos los autores");
        return autorDAO.obtenerTodosAutores();
    }

    public List<Autor> buscarAutoresPorNombre(String nombre) {
        System.out.println("Servicio: Buscando autores por nombre que contenga '" + nombre + "'");
        return autorDAO.buscarAutoresPorNombre(nombre);
    }   
    public Autor buscarAutorPorNombre(String nombre) {
    return autorDAO.buscarAutorPorNombre(nombre);
}

    public boolean actualizarAutor(Autor autor) {
        System.out.println("Servicio: Intentando actualizar autor ID " + autor.getIdAutor());
        if (autorDAO.obtenerAutorPorId(autor.getIdAutor()) == null) {
            System.out.println("Error: Autor con ID " + autor.getIdAutor() + " no encontrado para actualizar.");
            return false;
        }
        if (autor.getNombreCompleto() == null || autor.getNombreCompleto().trim().isEmpty()) {
            System.out.println("Error: El nombre del autor no puede ser vacío al actualizar.");
            return false;
        }
        return autorDAO.actualizarAutor(autor);
    }

    public boolean eliminarAutor(int idAutor) {
        System.out.println("Servicio: Intentando eliminar autor ID " + idAutor);
        if (autorDAO.obtenerAutorPorId(idAutor) == null) {
            System.out.println("Error: Autor con ID " + idAutor + " no encontrado para eliminar.");
            return false;
        }
        return autorDAO.eliminarAutor(idAutor);
    }
}
