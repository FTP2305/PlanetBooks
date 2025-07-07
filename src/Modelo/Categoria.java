/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

public class Categoria {
    private int idCategoria;
    private String nombreCategoria;
    private Integer idCategoriaPadre; 

    public Categoria() {
    }

    public Categoria(int idCategoria, String nombreCategoria, Integer idCategoriaPadre) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
        this.idCategoriaPadre = idCategoriaPadre;
    }

    
    public int getIdCategoria() {
        return idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public Integer getIdCategoriaPadre() {
        return idCategoriaPadre;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public void setIdCategoriaPadre(Integer idCategoriaPadre) {
        this.idCategoriaPadre = idCategoriaPadre;
    }

    @Override
    public String toString() {
        return "Categoria{" +
               "idCategoria=" + idCategoria +
               ", nombreCategoria='" + nombreCategoria + '\'' +
               ", idCategoriaPadre=" + (idCategoriaPadre != null ? idCategoriaPadre : "null") +
               '}';
    }
}
