/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.time.LocalDateTime;

public class Recomendacion {
    private int idRecomendacion;
    private int idUsuario;
    private String isbnLibroRecomendado;
    private LocalDateTime fechaGeneracion;

    public Recomendacion() {
    }

    public Recomendacion(int idRecomendacion, int idUsuario, String isbnLibroRecomendado, LocalDateTime fechaGeneracion) {
        this.idRecomendacion = idRecomendacion;
        this.idUsuario = idUsuario;
        this.isbnLibroRecomendado = isbnLibroRecomendado;
        this.fechaGeneracion = fechaGeneracion;
    }

    public int getIdRecomendacion() {
        return idRecomendacion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getIsbnLibroRecomendado() {
        return isbnLibroRecomendado;
    }

    public LocalDateTime getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setIdRecomendacion(int idRecomendacion) {
        this.idRecomendacion = idRecomendacion;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setIsbnLibroRecomendado(String isbnLibroRecomendado) {
        this.isbnLibroRecomendado = isbnLibroRecomendado;
    }

    public void setFechaGeneracion(LocalDateTime fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    @Override
    public String toString() {
        return "Recomendacion{" +
               "idRecomendacion=" + idRecomendacion +
               ", idUsuario=" + idUsuario +
               ", isbnLibroRecomendado='" + isbnLibroRecomendado + '\'' +
               ", fechaGeneracion=" + fechaGeneracion +
               '}';
    }
}
