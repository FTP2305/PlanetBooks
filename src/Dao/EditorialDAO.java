/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Conexion.ConexionBD;
import Modelo.Editorial;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EditorialDAO {

    public boolean guardarEditorial(Editorial editorial) {
        String sql = "INSERT INTO editoriales (nombre_editorial) VALUES (?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, editorial.getNombreEditorial());

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    editorial.setIdEditorial(rs.getInt(1));
                    System.out.println("Editorial guardada bien ID " + editorial.getIdEditorial());
                }
                return true;
            } else {
                System.out.println("No se pudo guardar la editorial");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar editorial " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de guardar editorial " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public Editorial obtenerEditorialPorId(int idEditorial) {
        String sql = "SELECT id_editorial, nombre_editorial FROM editoriales WHERE id_editorial = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Editorial editorial = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idEditorial);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                editorial = new Editorial();
                editorial.setIdEditorial(rs.getInt("id_editorial"));
                editorial.setNombreEditorial(rs.getString("nombre_editorial"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener editorial por ID " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de obtener editorial por ID " + e.getMessage());
                e.printStackTrace();
            }
        }
        return editorial;
    }

    public List<Editorial> obtenerTodasEditoriales() {
        List<Editorial> editoriales = new ArrayList<>();
        String sql = "SELECT id_editorial, nombre_editorial FROM editoriales ORDER BY nombre_editorial ASC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Editorial editorial = new Editorial();
                editorial.setIdEditorial(rs.getInt("id_editorial"));
                editorial.setNombreEditorial(rs.getString("nombre_editorial"));
                editoriales.add(editorial);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todas las editoriales " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de obtener todas las editoriales " + e.getMessage());
                e.printStackTrace();
            }
        }
        return editoriales;
    }

    public boolean actualizarEditorial(Editorial editorial) {
        String sql = "UPDATE editoriales SET nombre_editorial = ? WHERE id_editorial = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, editorial.getNombreEditorial());
            pstmt.setInt(2, editorial.getIdEditorial());

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar editorial " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de actualizar editorial " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public boolean eliminarEditorial(int idEditorial) {
        String sql = "DELETE FROM editoriales WHERE id_editorial = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idEditorial);

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar editorial " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de eliminar editorial " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        EditorialDAO editorialDAO = new EditorialDAO();

        System.out.println("--- Probando guardar editorial ---");
        Editorial nuevaEditorial = new Editorial(0, "Penguin Random House");
        if (editorialDAO.guardarEditorial(nuevaEditorial)) {
            System.out.println("Editorial guardada correctamente ID " + nuevaEditorial.getIdEditorial());
        } else {
            System.out.println("Fallo al guardar editorial");
        }

        System.out.println("--- Probando obtener editorial por ID ---");
        Editorial editorialEncontrada = editorialDAO.obtenerEditorialPorId(nuevaEditorial.getIdEditorial());
        if (editorialEncontrada != null) {
            System.out.println("Editorial encontrada " + editorialEncontrada.getNombreEditorial());
        } else {
            System.out.println("No se encontro la editorial");
        }

        System.out.println("--- Probando actualizar editorial ---");
        if (editorialEncontrada != null) {
            editorialEncontrada.setNombreEditorial("Penguin RH");
            if (editorialDAO.actualizarEditorial(editorialEncontrada)) {
                System.out.println("Editorial actualizada a " + editorialEncontrada.getNombreEditorial());
            } else {
                System.out.println("Fallo al actualizar editorial");
            }
        }

        System.out.println("--- Probando obtener todas las editoriales ---");
        List<Editorial> todasEditoriales = editorialDAO.obtenerTodasEditoriales();
        if (!todasEditoriales.isEmpty()) {
            System.out.println("Lista de editoriales");
            for (Editorial e : todasEditoriales) {
                System.out.println("- " + e.getNombreEditorial() + " ID " + e.getIdEditorial());
            }
        } else {
            System.out.println("No hay editoriales en la BD");
        }

        System.out.println("--- Probando eliminar editorial CUIDADO ---");
         if (editorialDAO.eliminarEditorial(nuevaEditorial.getIdEditorial())) {
             System.out.println("Editorial eliminada");
        } else {
            System.out.println("Fallo al eliminar editorial");
        }
    }
}
