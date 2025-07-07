/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

/**
 *
 * @author felix
 */
import Conexion.ConexionBD;
import Modelo.Autor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AutorDAO {

    public boolean guardarAutor(Autor autor) {
        String sql = "INSERT INTO autores (nombre_completo) VALUES (?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, autor.getNombreCompleto());

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    autor.setIdAutor(rs.getInt(1));
                    System.out.println("Autor guardado bien ID " + autor.getIdAutor());
                }
                return true;
            } else {
                System.out.println("No se pudo guardar el autor");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar autor " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de guardar autor " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    public Autor buscarAutorPorNombre(String nombre) {
    String sql = "SELECT id_autor, nombre_completo FROM autores WHERE nombre_completo LIKE ?";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Autor autor = null;

    try {
        conn = ConexionBD.getConnection();
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, "%" + nombre + "%"); 
        rs = pstmt.executeQuery();

        if (rs.next()) { 
            autor = new Autor(rs.getInt("id_autor"), rs.getString("nombre_completo"));
        }
    } catch (SQLException e) {
        System.err.println("Error al buscar autor por nombre en DAO: " + e.getMessage());
    } finally {

    }
    return autor;
}
    public Autor obtenerAutorPorId(int idAutor) {
        String sql = "SELECT id_autor, nombre_completo FROM autores WHERE id_autor = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Autor autor = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idAutor);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                autor = new Autor();
                autor.setIdAutor(rs.getInt("id_autor"));
                autor.setNombreCompleto(rs.getString("nombre_completo"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener autor por ID " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de obtener autor por ID " + e.getMessage());
                e.printStackTrace();
            }
        }
        return autor;
    }

    public List<Autor> obtenerTodosAutores() {
        List<Autor> autores = new ArrayList<>();
        String sql = "SELECT id_autor, nombre_completo FROM autores ORDER BY nombre_completo ASC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Autor autor = new Autor();
                autor.setIdAutor(rs.getInt("id_autor"));
                autor.setNombreCompleto(rs.getString("nombre_completo"));
                autores.add(autor);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los autores " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de obtener todos los autores " + e.getMessage());
                e.printStackTrace();
            }
        }
        return autores;
    }

    public boolean actualizarAutor(Autor autor) {
        String sql = "UPDATE autores SET nombre_completo = ? WHERE id_autor = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, autor.getNombreCompleto());
            pstmt.setInt(2, autor.getIdAutor());

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar autor " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de actualizar autor " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public boolean eliminarAutor(int idAutor) {
        String sql = "DELETE FROM autores WHERE id_autor = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idAutor);

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar autor " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de eliminar autor " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    public List<Autor> buscarAutoresPorNombre(String nombre) {
        List<Autor> autores = new ArrayList<>();
        String sql = "SELECT id_autor, nombre_completo FROM autores WHERE LOWER(nombre_completo) LIKE LOWER(?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + nombre + "%"); // Busca nombre que contenga la cadena

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Autor autor = new Modelo.Autor(); 
                autor.setIdAutor(rs.getInt("id_autor"));
                autor.setNombreCompleto(rs.getString("nombre_completo"));
                autores.add(autor);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar autores por nombre: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de buscar autores por nombre: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return autores;
    }

    public static void main(String[] args) {
        AutorDAO autorDAO = new AutorDAO();

        System.out.println("--- Probando guardar autor ---");
        Autor nuevoAutor = new Autor(0, "Gabriel Garcia Marquez");
        if (autorDAO.guardarAutor(nuevoAutor)) {
            System.out.println("Autor guardado correctamente ID " + nuevoAutor.getIdAutor());
        } else {
            System.out.println("Fallo al guardar autor");
        }

        System.out.println("--- Probando obtener autor por ID ---");
        Autor autorEncontrado = autorDAO.obtenerAutorPorId(nuevoAutor.getIdAutor());
        if (autorEncontrado != null) {
            System.out.println("Autor encontrado " + autorEncontrado.getNombreCompleto());
        } else {
            System.out.println("No se encontro el autor");
        }

        System.out.println("--- Probando actualizar autor ---");
        if (autorEncontrado != null) {
            autorEncontrado.setNombreCompleto("Gabo Garcia Marquez");
            if (autorDAO.actualizarAutor(autorEncontrado)) {
                System.out.println("Autor actualizado a " + autorEncontrado.getNombreCompleto());
            } else {
                System.out.println("Fallo al actualizar autor");
            }
        }

        System.out.println("--- Probando obtener todos los autores ---");
        List<Autor> todosAutores = autorDAO.obtenerTodosAutores();
        if (!todosAutores.isEmpty()) {
            System.out.println("Lista de autores");
            for (Autor a : todosAutores) {
                System.out.println("- " + a.getNombreCompleto() + " ID " + a.getIdAutor());
            }
        } else {
            System.out.println("No hay autores en la BD");
        }

        System.out.println("--- Probando eliminar autor CUIDADO ---");
         if (autorDAO.eliminarAutor(nuevoAutor.getIdAutor())) {
             System.out.println("Autor eliminado");
         } else {
             System.out.println("Fallo al eliminar autor");
         }
    }
}
