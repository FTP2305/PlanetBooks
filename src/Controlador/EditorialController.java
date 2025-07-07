/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Editorial;
import Service.EditorialService;
import java.util.List;

public class EditorialController {

    private EditorialService editorialService;

    public EditorialController() {
        this.editorialService = new EditorialService();
    }

    public String crearEditorial(Editorial editorial) {
        System.out.println("Controlador: Solicitud para crear editorial.");
        if (editorialService.registrarEditorial(editorial)) {
            return "Editorial '" + editorial.getNombreEditorial() + "' registrada exitosamente.";
        } else {
            return "Error al registrar editorial '" + editorial.getNombreEditorial() + "'. Verifique los datos.";
        }
    }

    public Editorial obtenerEditorialPorId(int idEditorial) {
        System.out.println("Controlador: Solicitud para obtener editorial con ID " + idEditorial + ".");
        Editorial editorial = editorialService.obtenerEditorialPorId(idEditorial);
        if (editorial != null) {
            System.out.println("Editorial encontrada: " + editorial.getNombreEditorial());
            return editorial;
        } else {
            System.out.println("Editorial con ID " + idEditorial + " no encontrada.");
            return null;
        }
    }
    

    public List<Editorial> obtenerTodasEditoriales() {
        System.out.println("Controlador: Solicitud para obtener todas las editoriales.");
        List<Editorial> editoriales = editorialService.obtenerTodasEditoriales();
        System.out.println("Se encontraron " + editoriales.size() + " editoriales.");
        return editoriales;
    }

    public String actualizarEditorial(Editorial editorial) {
        System.out.println("Controlador: Solicitud para actualizar editorial ID " + editorial.getIdEditorial() + ".");
        if (editorialService.actualizarEditorial(editorial)) {
            return "Editorial ID " + editorial.getIdEditorial() + " actualizada exitosamente.";
        } else {
            return "Error al actualizar editorial ID " + editorial.getIdEditorial() + ". Verifique los datos o si la editorial existe.";
        }
    }

    public String eliminarEditorial(int idEditorial) {
        System.out.println("Controlador: Solicitud para eliminar editorial ID " + idEditorial + ".");
        if (editorialService.eliminarEditorial(idEditorial)) {
            return "Editorial ID " + idEditorial + " eliminada exitosamente.";
        } else {
            return "Error al eliminar editorial ID " + idEditorial + ". Podría tener libros asociados o no existe.";
        }
    }
}
