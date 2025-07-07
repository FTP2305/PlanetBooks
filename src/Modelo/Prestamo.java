/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.time.LocalDateTime;

public class Prestamo {
    private int idPrestamo;
    private int idUsuario;     
    private String isbnLibro;  
    private LocalDateTime fechaPrestamo;
    private LocalDateTime fechaDevolucionEsperada;
    private LocalDateTime fechaDevolucionReal; 
    private String estadoPrestamo; 

    public Prestamo() {
    }

    public Prestamo(int idPrestamo, int idUsuario, String isbnLibro, LocalDateTime fechaPrestamo, LocalDateTime fechaDevolucionEsperada, String estadoPrestamo) {
        this.idPrestamo = idPrestamo;
        this.idUsuario = idUsuario;
        this.isbnLibro = isbnLibro;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
        this.estadoPrestamo = estadoPrestamo;
    }

    public Prestamo(int idPrestamo, int idUsuario, String isbnLibro, LocalDateTime fechaPrestamo, LocalDateTime fechaDevolucionEsperada, LocalDateTime fechaDevolucionReal, String estadoPrestamo) {
        this.idPrestamo = idPrestamo;
        this.idUsuario = idUsuario;
        this.isbnLibro = isbnLibro;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
        this.fechaDevolucionReal = fechaDevolucionReal;
        this.estadoPrestamo = estadoPrestamo;
    }

    public int getIdPrestamo() {
        return idPrestamo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getIsbnLibro() {
        return isbnLibro;
    }

    public LocalDateTime getFechaPrestamo() {
        return fechaPrestamo;
    }

    public LocalDateTime getFechaDevolucionEsperada() {
        return fechaDevolucionEsperada;
    }

    public LocalDateTime getFechaDevolucionReal() {
        return fechaDevolucionReal;
    }

    public String getEstadoPrestamo() {
        return estadoPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setIsbnLibro(String isbnLibro) {
        this.isbnLibro = isbnLibro;
    }

    public void setFechaPrestamo(LocalDateTime fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public void setFechaDevolucionEsperada(LocalDateTime fechaDevolucionEsperada) {
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
    }

    public void setFechaDevolucionReal(LocalDateTime fechaDevolucionReal) {
        this.fechaDevolucionReal = fechaDevolucionReal;
    }

    public void setEstadoPrestamo(String estadoPrestamo) {
        this.estadoPrestamo = estadoPrestamo;
    }

    @Override
    public String toString() {
        return "Prestamo{" +
               "idPrestamo=" + idPrestamo +
               ", idUsuario=" + idUsuario +
               ", isbnLibro='" + isbnLibro + '\'' +
               ", fechaPrestamo=" + fechaPrestamo +
               ", fechaDevolucionEsperada=" + fechaDevolucionEsperada +
               ", fechaDevolucionReal=" + fechaDevolucionReal +
               ", estadoPrestamo='" + estadoPrestamo + '\'' +
               '}';
    }
}
