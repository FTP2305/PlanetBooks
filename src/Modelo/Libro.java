/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author felix
 */
public class Libro {
    private String isbn;
    private String titulo;
    private int idAutorPrincipal; 
    private int idEditorial;      
    private int anioPublicacion;  
    private int stockTotal;
    private int stockDisponible;

    public Libro() {
    }

    public Libro(String isbn, String titulo, int idAutorPrincipal, int idEditorial, int anioPublicacion, int stockTotal, int stockDisponible) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.idAutorPrincipal = idAutorPrincipal;
        this.idEditorial = idEditorial;
        this.anioPublicacion = anioPublicacion;
        this.stockTotal = stockTotal;
        this.stockDisponible = stockDisponible;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getIdAutorPrincipal() {
        return idAutorPrincipal;
    }

    public int getIdEditorial() {
        return idEditorial;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public int getStockTotal() {
        return stockTotal;
    }

    public int getStockDisponible() {
        return stockDisponible;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setIdAutorPrincipal(int idAutorPrincipal) {
        this.idAutorPrincipal = idAutorPrincipal;
    }

    public void setIdEditorial(int idEditorial) {
        this.idEditorial = idEditorial;
    }

    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public void setStockTotal(int stockTotal) {
        this.stockTotal = stockTotal;
    }

    public void setStockDisponible(int stockDisponible) {
        this.stockDisponible = stockDisponible;
    }

    @Override
    public String toString() {
        return "Libro{" +
               "isbn='" + isbn + '\'' +
               ", titulo='" + titulo + '\'' +
               ", idAutorPrincipal=" + idAutorPrincipal +
               ", idEditorial=" + idEditorial +
               ", anioPublicacion=" + anioPublicacion +
               ", stockTotal=" + stockTotal +
               ", stockDisponible=" + stockDisponible +
               '}';
    }
}
