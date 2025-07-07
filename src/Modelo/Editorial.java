/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

public class Editorial {
    private int idEditorial;
    private String nombreEditorial;

    public Editorial() {
    }

    public Editorial(int idEditorial, String nombreEditorial) {
        this.idEditorial = idEditorial;
        this.nombreEditorial = nombreEditorial;
    }

    public int getIdEditorial() {
        return idEditorial;
    }

    public String getNombreEditorial() {
        return nombreEditorial;
    }

    public void setIdEditorial(int idEditorial) {
        this.idEditorial = idEditorial;
    }

    public void setNombreEditorial(String nombreEditorial) {
        this.nombreEditorial = nombreEditorial;
    }

    @Override
    public String toString() {
        return "Editorial{" +
               "idEditorial=" + idEditorial +
               ", nombreEditorial='" + nombreEditorial + '\'' +
               '}';
    }
}
