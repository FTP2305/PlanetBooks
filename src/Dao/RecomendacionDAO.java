/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Conexion.ConexionBD;
import Modelo.Recomendacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RecomendacionDAO {

    public boolean guardarRecomendacion(Recomendacion recomendacion) {
        String sql = "INSERT INTO recomendaciones (id_usuario, isbn_libro_recomendado, fecha_generacion) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, recomendacion.getIdUsuario());
            pstmt.setString(2, recomendacion.getIsbnLibroRecomendado());
            pstmt.setTimestamp(3, Timestamp.valueOf(recomendacion.getFechaGeneracion()));

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    recomendacion.setIdRecomendacion(rs.getInt(1));
                    System.out.println("Recomendacion guardada con exito ID " + recomendacion.getIdRecomendacion());
                }
                return true;
            } else {
                System.out.println("No se pudo guardar la recomendacion");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Error al guardar recomendacion " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de guardar recomendacion " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public Recomendacion obtenerRecomendacionPorId(int idRecomendacion) {
        String sql = "SELECT id_recomendacion, id_usuario, isbn_libro_recomendado, fecha_generacion FROM recomendaciones WHERE id_recomendacion = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Recomendacion recomendacion = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idRecomendacion);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                recomendacion = new Recomendacion();
                recomendacion.setIdRecomendacion(rs.getInt("id_recomendacion"));
                recomendacion.setIdUsuario(rs.getInt("id_usuario"));
                recomendacion.setIsbnLibroRecomendado(rs.getString("isbn_libro_recomendado"));
                recomendacion.setFechaGeneracion(rs.getTimestamp("fecha_generacion").toLocalDateTime());
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener recomendacion por ID " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de obtener recomendacion por ID " + e.getMessage());
                e.printStackTrace();
            }
        }
        return recomendacion;
    }

    public List<Recomendacion> obtenerRecomendacionesPorUsuario(int idUsuario) {
        List<Recomendacion> recomendaciones = new ArrayList<>();
        String sql = "SELECT id_recomendacion, id_usuario, isbn_libro_recomendado, fecha_generacion FROM recomendaciones WHERE id_usuario = ? ORDER BY fecha_generacion DESC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idUsuario);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Recomendacion recomendacion = new Recomendacion();
                recomendacion.setIdRecomendacion(rs.getInt("id_recomendacion"));
                recomendacion.setIdUsuario(rs.getInt("id_usuario"));
                recomendacion.setIsbnLibroRecomendado(rs.getString("isbn_libro_recomendado"));
                recomendacion.setFechaGeneracion(rs.getTimestamp("fecha_generacion").toLocalDateTime());
                recomendaciones.add(recomendacion);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener recomendaciones por usuario " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de obtener recomendaciones por usuario " + e.getMessage());
                e.printStackTrace();
            }
        }
        return recomendaciones;
    }

    public boolean eliminarRecomendacion(int idRecomendacion) {
        String sql = "DELETE FROM recomendaciones WHERE id_recomendacion = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idRecomendacion);

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar recomendacion " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de eliminar recomendacion " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        RecomendacionDAO recomendacionDAO = new RecomendacionDAO();
        int idUsuarioPrueba = 1;
        String isbnLibroPrueba = "9781234567890";

        System.out.println("--- Probando guardar recomendacion ---");
        Recomendacion nuevaRecomendacion = new Recomendacion(0, idUsuarioPrueba, isbnLibroPrueba, LocalDateTime.now());

        if (recomendacionDAO.guardarRecomendacion(nuevaRecomendacion)) {
            System.out.println("Recomendacion registrada correctamente ID " + nuevaRecomendacion.getIdRecomendacion());
            int idRecomendacionGenerada = nuevaRecomendacion.getIdRecomendacion();

            System.out.println("--- Probando obtener recomendacion por ID ---");
            Recomendacion recEncontrada = recomendacionDAO.obtenerRecomendacionPorId(idRecomendacionGenerada);
            if (recEncontrada != null) {
                System.out.println("Recomendacion encontrada ID " + recEncontrada.getIdRecomendacion() +
                                   " Usuario " + recEncontrada.getIdUsuario() +
                                   " Libro " + recEncontrada.getIsbnLibroRecomendado());
            } else {
                System.out.println("No se encontro la recomendacion con ID " + idRecomendacionGenerada);
            }

            System.out.println("--- Probando obtener recomendaciones por usuario ---");
            List<Recomendacion> recsDelUsuario = recomendacionDAO.obtenerRecomendacionesPorUsuario(idUsuarioPrueba);
            if (!recsDelUsuario.isEmpty()) {
                System.out.println("Recomendaciones para el usuario " + idUsuarioPrueba);
                for (Recomendacion r : recsDelUsuario) {
                    System.out.println("ID Rec " + r.getIdRecomendacion() + " Libro " + r.getIsbnLibroRecomendado() + " Fecha " + r.getFechaGeneracion());
                }
            } else {
                System.out.println("No hay recomendaciones para el usuario " + idUsuarioPrueba);
            }

            System.out.println("--- Probando eliminar recomendacion CUIDADO ---");
            if (recomendacionDAO.eliminarRecomendacion(idRecomendacionGenerada)) {
                System.out.println("Recomendacion ID " + idRecomendacionGenerada + " eliminada con exito");
            } else {
                 System.out.println("Fallo al eliminar la recomendacion ID " + idRecomendacionGenerada);
            }

        } else {
            System.out.println("Fallo al registrar la recomendacion inicial");
        }
    }
}
