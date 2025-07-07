/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

public class Autor {
    private int idAutor;
    private String nombreCompleto;

    public Autor() {
    }

    public Autor(int idAutor, String nombreCompleto) {
        this.idAutor = idAutor;
        this.nombreCompleto = nombreCompleto;
    }

    public int getIdAutor() {
        return idAutor;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    @Override
    public String toString() {
        return "Autor{" +
               "idAutor=" + idAutor +
               ", nombreCompleto='" + nombreCompleto + '\'' +
               '}';
    }
}
