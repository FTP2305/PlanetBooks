/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Libro;
import Service.RecomendacionService;
import java.util.List;

public class RecomendacionController {

    private RecomendacionService recomendacionService;

    public RecomendacionController() {
        this.recomendacionService = new RecomendacionService();
    }

    public List<Libro> obtenerRecomendacionesGenerales() {
        return recomendacionService.obtenerRecomendacionesGenerales();
    }

    public List<Libro> obtenerRecomendacionesParaUsuario(int idUsuario) {
        return recomendacionService.obtenerRecomendacionesParaUsuario(idUsuario);
    }
    public List<Libro> recomendarLibrosPorLibro(String isbnLibroOrigen, int cantidadMaxima) {
        System.out.println("Controlador: Solicitud de recomendación de libros basada en libro con ISBN '" + isbnLibroOrigen + "'.");
        List<Libro> recomendaciones = recomendacionService.recomendarLibrosPorLibro(isbnLibroOrigen, cantidadMaxima);
        System.out.println("Se encontraron " + recomendaciones.size() + " recomendaciones para el libro '" + isbnLibroOrigen + "'.");
        return recomendaciones;
    }

    public List<Libro> recomendarLibrosPorUsuario(int idUsuario, int cantidadMaxima) {
        System.out.println("Controlador: Solicitud de recomendación de libros para el usuario ID " + idUsuario + ".");
        List<Libro> recomendaciones = recomendacionService.recomendarLibrosPorUsuario(idUsuario, cantidadMaxima);
        System.out.println("Se encontraron " + recomendaciones.size() + " recomendaciones para el usuario ID " + idUsuario + ".");
        return recomendaciones;
    }
}
