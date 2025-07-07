/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Dao.EditorialDAO;
import Modelo.Editorial;
import java.util.List;

public class EditorialService {

    private EditorialDAO editorialDAO;

    public EditorialService() {
        this.editorialDAO = new EditorialDAO();
    }

    public boolean registrarEditorial(Editorial editorial) {
        System.out.println("Servicio: Intentando registrar editorial " + editorial.getNombreEditorial());
        if (editorial.getNombreEditorial() == null || editorial.getNombreEditorial().trim().isEmpty()) {
            System.out.println("Error: El nombre de la editorial no puede ser vacío.");
            return false;
        }
        if (editorialDAO.obtenerTodasEditoriales().stream().anyMatch(e -> e.getNombreEditorial().equalsIgnoreCase(editorial.getNombreEditorial()))) {
            System.out.println("Error: Ya existe una editorial con el nombre '" + editorial.getNombreEditorial() + "'.");
             return false;
        }
        return editorialDAO.guardarEditorial(editorial);
    }

    public Editorial obtenerEditorialPorId(int idEditorial) {
        System.out.println("Servicio: Obteniendo editorial por ID " + idEditorial);
        return editorialDAO.obtenerEditorialPorId(idEditorial);
    }

    public List<Editorial> obtenerTodasEditoriales() {
        System.out.println("Servicio: Obteniendo todas las editoriales");
        return editorialDAO.obtenerTodasEditoriales();
    }

    public boolean actualizarEditorial(Editorial editorial) {
        System.out.println("Servicio: Intentando actualizar editorial ID " + editorial.getIdEditorial());
        if (editorialDAO.obtenerEditorialPorId(editorial.getIdEditorial()) == null) {
            System.out.println("Error: Editorial con ID " + editorial.getIdEditorial() + " no encontrada para actualizar.");
            return false;
        }
        if (editorial.getNombreEditorial() == null || editorial.getNombreEditorial().trim().isEmpty()) {
            System.out.println("Error: El nombre de la editorial no puede ser vacío al actualizar.");
            return false;
        }
        return editorialDAO.actualizarEditorial(editorial);
    }

    public boolean eliminarEditorial(int idEditorial) {
        System.out.println("Servicio: Intentando eliminar editorial ID " + idEditorial);
        if (editorialDAO.obtenerEditorialPorId(idEditorial) == null) {
            System.out.println("Error: Editorial con ID " + idEditorial + " no encontrada para eliminar.");
            return false;
        }
        return editorialDAO.eliminarEditorial(idEditorial);
    }
}
