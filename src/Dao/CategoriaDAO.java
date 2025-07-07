/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Conexion.ConexionBD;
import Modelo.Categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    public boolean guardarCategoria(Categoria categoria) {
        String sql = "INSERT INTO categorias (nombre_categoria, id_categoria_padre) VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, categoria.getNombreCategoria());
            
            if (categoria.getIdCategoriaPadre() != null) {
                pstmt.setInt(2, categoria.getIdCategoriaPadre());
            } else {
                pstmt.setNull(2, java.sql.Types.INTEGER);
            }

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    categoria.setIdCategoria(rs.getInt(1));
                    System.out.println("Categoria guardada bien ID " + categoria.getIdCategoria());
                }
                return true;
            } else {
                System.out.println("No se pudo guardar la categoria");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar categoria " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de guardar categoria " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public Categoria obtenerCategoriaPorId(int idCategoria) {
        String sql = "SELECT id_categoria, nombre_categoria, id_categoria_padre FROM categorias WHERE id_categoria = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Categoria categoria = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idCategoria);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                categoria = new Categoria();
                categoria.setIdCategoria(rs.getInt("id_categoria"));
                categoria.setNombreCategoria(rs.getString("nombre_categoria"));
                
                int idPadre = rs.getInt("id_categoria_padre");
                if (rs.wasNull()) { // Si el valor en la BD fue NULL
                    categoria.setIdCategoriaPadre(null);
                } else {
                    categoria.setIdCategoriaPadre(idPadre);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener categoria por ID " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de obtener categoria por ID " + e.getMessage());
                e.printStackTrace();
            }
        }
        return categoria;
    }

    public List<Categoria> obtenerTodasCategorias() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT id_categoria, nombre_categoria, id_categoria_padre FROM categorias ORDER BY nombre_categoria ASC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setIdCategoria(rs.getInt("id_categoria"));
                categoria.setNombreCategoria(rs.getString("nombre_categoria"));
                
                int idPadre = rs.getInt("id_categoria_padre");
                if (rs.wasNull()) {
                    categoria.setIdCategoriaPadre(null);
                } else {
                    categoria.setIdCategoriaPadre(idPadre);
                }
                categorias.add(categoria);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todas las categorias " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de obtener todas las categorias " + e.getMessage());
                e.printStackTrace();
            }
        }
        return categorias;
    }

    public boolean actualizarCategoria(Categoria categoria) {
        String sql = "UPDATE categorias SET nombre_categoria = ?, id_categoria_padre = ? WHERE id_categoria = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, categoria.getNombreCategoria());
            
            if (categoria.getIdCategoriaPadre() != null) {
                pstmt.setInt(2, categoria.getIdCategoriaPadre());
            } else {
                pstmt.setNull(2, java.sql.Types.INTEGER);
            }
            pstmt.setInt(3, categoria.getIdCategoria());

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar categoria " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de actualizar categoria " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public boolean eliminarCategoria(int idCategoria) {
        String sql = "DELETE FROM categorias WHERE id_categoria = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idCategoria);

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar categoria " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de eliminar categoria " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        CategoriaDAO categoriaDAO = new CategoriaDAO();

        System.out.println("--- Probando guardar categoria raiz ---");
        Categoria nuevaCategoriaRaiz = new Categoria(0, "Ficcion", null);
        if (categoriaDAO.guardarCategoria(nuevaCategoriaRaiz)) {
            System.out.println("Categoria Ficcion guardada ID " + nuevaCategoriaRaiz.getIdCategoria());
        } else {
            System.out.println("Fallo al guardar Ficcion");
        }

        System.out.println("--- Probando guardar subcategoria ---");
        Categoria nuevaSubCategoria = new Categoria(0, "Ciencia Ficcion", nuevaCategoriaRaiz.getIdCategoria());
        if (categoriaDAO.guardarCategoria(nuevaSubCategoria)) {
            System.out.println("Categoria Ciencia Ficcion guardada ID " + nuevaSubCategoria.getIdCategoria());
        } else {
            System.out.println("Fallo al guardar Ciencia Ficcion");
        }

        System.out.println("--- Probando obtener categoria por ID ---");
        Categoria categoriaEncontrada = categoriaDAO.obtenerCategoriaPorId(nuevaSubCategoria.getIdCategoria());
        if (categoriaEncontrada != null) {
            System.out.println("Categoria encontrada " + categoriaEncontrada.getNombreCategoria() +
                               " Padre " + (categoriaEncontrada.getIdCategoriaPadre() != null ? categoriaEncontrada.getIdCategoriaPadre() : "Ninguno"));
        } else {
            System.out.println("No se encontro la categoria");
        }

        System.out.println("--- Probando actualizar categoria ---");
        if (categoriaEncontrada != null) {
            categoriaEncontrada.setNombreCategoria("Sci-Fi");
            if (categoriaDAO.actualizarCategoria(categoriaEncontrada)) {
                System.out.println("Categoria actualizada a " + categoriaEncontrada.getNombreCategoria());
            } else {
                System.out.println("Fallo al actualizar categoria");
            }
        }

        System.out.println("--- Probando obtener todas las categorias ---");
        List<Categoria> todasCategorias = categoriaDAO.obtenerTodasCategorias();
        if (!todasCategorias.isEmpty()) {
            System.out.println("Lista de categorias");
            for (Categoria c : todasCategorias) {
                System.out.println("- " + c.getNombreCategoria() + " ID " + c.getIdCategoria() +
                                   " Padre " + (c.getIdCategoriaPadre() != null ? c.getIdCategoriaPadre() : "Ninguno"));
            }
        } else {
            System.out.println("No hay categorias en la BD");
        }

        System.out.println("--- Probando eliminar categoria CUIDADO ---");
        if (categoriaDAO.eliminarCategoria(nuevaSubCategoria.getIdCategoria())) {
             System.out.println("Subcategoria eliminada");
        } else {
             System.out.println("Fallo al eliminar subcategoria");
         }
         if (categoriaDAO.eliminarCategoria(nuevaCategoriaRaiz.getIdCategoria())) {
             System.out.println("Categoria raiz eliminada");
         } else {
             System.out.println("Fallo al eliminar categoria raiz");
         }
    }
}
