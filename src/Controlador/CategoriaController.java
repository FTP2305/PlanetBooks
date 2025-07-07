/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Categoria;
import Modelo.Libro;
import Service.CategoriaService;
import java.util.List;
import java.util.Set;

public class CategoriaController {

    private CategoriaService categoriaService;

    public CategoriaController() {
        this.categoriaService = new CategoriaService();
    }

    public String crearCategoria(Categoria categoria) {
        System.out.println("Controlador: Solicitud para crear categoría.");
        if (categoriaService.registrarCategoria(categoria)) {
            return "Categoría '" + categoria.getNombreCategoria() + "' registrada exitosamente.";
        } else {
            return "Error al registrar categoría '" + categoria.getNombreCategoria() + "'. Verifique los datos.";
        }
    }

    public Categoria obtenerCategoriaPorId(int idCategoria) {
        System.out.println("Controlador: Solicitud para obtener categoría con ID " + idCategoria + ".");
        List<Categoria> todasCategorias = categoriaService.obtenerTodasCategorias();
        for (Categoria c : todasCategorias) {
            if (c.getIdCategoria() == idCategoria) {
                System.out.println("Categoría encontrada: " + c.getNombreCategoria());
                return c;
            }
        }
        System.out.println("Categoría con ID " + idCategoria + " no encontrada.");
        return null;
    }

    public List<Categoria> obtenerTodasCategorias() {
        System.out.println("Controlador: Solicitud para obtener todas las categorías.");
        List<Categoria> categorias = categoriaService.obtenerTodasCategorias();
        System.out.println("Se encontraron " + categorias.size() + " categorías.");
        return categorias;
    }

    public String actualizarCategoria(Categoria categoria) {
        System.out.println("Controlador: Solicitud para actualizar categoría ID " + categoria.getIdCategoria() + ".");
        if (categoriaService.actualizarCategoria(categoria)) {
            return "Categoría ID " + categoria.getIdCategoria() + " actualizada exitosamente.";
        } else {
            return "Error al actualizar categoría ID " + categoria.getIdCategoria() + ". Verifique los datos o si la categoría existe.";
        }
    }

    public String eliminarCategoria(int idCategoria) {
        System.out.println("Controlador: Solicitud para eliminar categoría ID " + idCategoria + ".");
        if (categoriaService.eliminarCategoria(idCategoria)) {
            return "Categoría ID " + idCategoria + " eliminada exitosamente.";
        } else {
            return "Error al eliminar categoría ID " + idCategoria + ". Podría tener subcategorías o libros asociados.";
        }
    }

    public Set<Libro> buscarLibrosEnCategoriaConSubcategorias(int idCategoriaPrincipal) {
        System.out.println("Controlador: Solicitud para buscar libros en categoría ID " + idCategoriaPrincipal + " y sus subcategorías.");
        Set<Libro> libros = categoriaService.buscarLibrosPorCategoriaConSubcategorias(idCategoriaPrincipal);
        System.out.println("Se encontraron " + libros.size() + " libros en la categoría y sus subcategorías.");
        return libros;
    }
}
