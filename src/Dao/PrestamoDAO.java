/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Conexion.ConexionBD;
import Modelo.Prestamo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; 
import java.sql.Timestamp; 
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {
    public List<Prestamo> obtenerTodosLosPrestamos() {
        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT id_prestamo, id_usuario, isbn_libro, fecha_prestamo, fecha_devolucion_esperada, fecha_devolucion_real, estado_prestamo FROM prestamos";
        
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Prestamo prestamo = new Prestamo(
                    rs.getInt("id_prestamo"),
                    rs.getInt("id_usuario"),
                    rs.getString("isbn_libro"),
                    rs.getTimestamp("fecha_prestamo").toLocalDateTime(),
                    rs.getTimestamp("fecha_devolucion_esperada").toLocalDateTime(),
                    rs.getTimestamp("fecha_devolucion_real") != null ? rs.getTimestamp("fecha_devolucion_real").toLocalDateTime() : null,
                    rs.getString("estado_prestamo")
                );
                prestamos.add(prestamo);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener todos los prestamos: " + e.getMessage());
        }
        return prestamos;
    }

    public List<Prestamo> obtenerPrestamosPorUsuario(int idUsuario) {
        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT id_prestamo, id_usuario, isbn_libro, fecha_prestamo, fecha_devolucion_esperada, fecha_devolucion_real, estado_prestamo FROM prestamos WHERE id_usuario = ?";
        
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Prestamo prestamo = new Prestamo(
                        rs.getInt("id_prestamo"),
                        rs.getInt("id_usuario"),
                        rs.getString("isbn_libro"),
                        rs.getTimestamp("fecha_prestamo").toLocalDateTime(),
                        rs.getTimestamp("fecha_devolucion_esperada").toLocalDateTime(),
                        rs.getTimestamp("fecha_devolucion_real") != null ? rs.getTimestamp("fecha_devolucion_real").toLocalDateTime() : null,
                        rs.getString("estado_prestamo")
                    );
                    prestamos.add(prestamo);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener prestamos por usuario: " + e.getMessage());
        }
        return prestamos;
    }
    public boolean guardarPrestamo(Prestamo prestamo) {
        String sql = "INSERT INTO prestamos (id_usuario, isbn_libro, fecha_prestamo, fecha_devolucion_esperada, estado_prestamo) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, prestamo.getIdUsuario());
            pstmt.setString(2, prestamo.getIsbnLibro());
            pstmt.setTimestamp(3, Timestamp.valueOf(prestamo.getFechaPrestamo()));
            pstmt.setTimestamp(4, Timestamp.valueOf(prestamo.getFechaDevolucionEsperada()));
            pstmt.setString(5, prestamo.getEstadoPrestamo());

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    prestamo.setIdPrestamo(rs.getInt(1)); 
                    System.out.println("Prestamo registrado y ID asignado: " + prestamo.getIdPrestamo());
                }
                return true;
            } else {
                System.out.println("No se pudo guardar el prestamo.");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Error al guardar prestamo: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de guardar prestamo: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public Prestamo obtenerPrestamoPorId(int idPrestamo) {
        String sql = "SELECT id_prestamo, id_usuario, isbn_libro, fecha_prestamo, fecha_devolucion_esperada, fecha_devolucion_real, estado_prestamo FROM prestamos WHERE id_prestamo = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Prestamo prestamo = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idPrestamo);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                prestamo = new Prestamo();
                prestamo.setIdPrestamo(rs.getInt("id_prestamo"));
                prestamo.setIdUsuario(rs.getInt("id_usuario"));
                prestamo.setIsbnLibro(rs.getString("isbn_libro"));
                prestamo.setFechaPrestamo(rs.getTimestamp("fecha_prestamo").toLocalDateTime());
                prestamo.setFechaDevolucionEsperada(rs.getTimestamp("fecha_devolucion_esperada").toLocalDateTime());
                
                Timestamp fechaRealTimestamp = rs.getTimestamp("fecha_devolucion_real");
                if (fechaRealTimestamp != null) {
                    prestamo.setFechaDevolucionReal(fechaRealTimestamp.toLocalDateTime());
                } else {
                    prestamo.setFechaDevolucionReal(null);
                }
                
                prestamo.setEstadoPrestamo(rs.getString("estado_prestamo"));
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener prestamo por ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de obtener prestamo por ID: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return prestamo;
    }

    public boolean actualizarPrestamo(Prestamo prestamo) {
        String sql = "UPDATE prestamos SET id_usuario = ?, isbn_libro = ?, fecha_devolucion_esperada = ?, fecha_devolucion_real = ?, estado_prestamo = ? WHERE id_prestamo = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, prestamo.getIdUsuario());
            pstmt.setString(2, prestamo.getIsbnLibro());
            pstmt.setTimestamp(3, Timestamp.valueOf(prestamo.getFechaDevolucionEsperada()));
            
            if (prestamo.getFechaDevolucionReal() != null) {
                pstmt.setTimestamp(4, Timestamp.valueOf(prestamo.getFechaDevolucionReal()));
            } else {
                pstmt.setNull(4, java.sql.Types.TIMESTAMP);
            }
            
            pstmt.setString(5, prestamo.getEstadoPrestamo());
            pstmt.setInt(6, prestamo.getIdPrestamo());

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar prestamo: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de actualizar prestamo: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    

    public static void main(String[] args) {
        PrestamoDAO prestamoDAO = new PrestamoDAO();

        System.out.println("\n--- Probando guardar prestamo ---");
        int idUsuarioPrueba = 1; 
        String isbnLibroPrueba = "9781234567890"; 
        LocalDateTime fechaPrestamo = LocalDateTime.now();
        LocalDateTime fechaDevolucionEsperada = fechaPrestamo.plusWeeks(2);
        Prestamo nuevoPrestamo = new Prestamo(0, idUsuarioPrueba, isbnLibroPrueba, fechaPrestamo, fechaDevolucionEsperada, "ACTIVO");

        if (prestamoDAO.guardarPrestamo(nuevoPrestamo)) {
            System.out.println("Prestamo registrado bien. ID del nuevo prestamo: " + nuevoPrestamo.getIdPrestamo());
            int idPrestamoGenerado = nuevoPrestamo.getIdPrestamo();

            System.out.println("\n--- Probando obtener prestamo por ID ---");
            Prestamo prestamoEncontrado = prestamoDAO.obtenerPrestamoPorId(idPrestamoGenerado);
            if (prestamoEncontrado != null) {
                System.out.println("Encontre el prestamo ID " + prestamoEncontrado.getIdPrestamo() + " para el usuario " + prestamoEncontrado.getIdUsuario());
            } else {
                System.out.println("No se encontro el prestamo ID " + idPrestamoGenerado);
            }

            System.out.println("\n--- Probando actualizar prestamo (simulando devolucion) ---");
            if (prestamoEncontrado != null) {
                prestamoEncontrado.setFechaDevolucionReal(LocalDateTime.now());
                prestamoEncontrado.setEstadoPrestamo("DEVUELTO");
                if (prestamoDAO.actualizarPrestamo(prestamoEncontrado)) {
                    System.out.println("Prestamo ID " + prestamoEncontrado.getIdPrestamo() + " marcado como DEVUELTO.");
                } else {
                    System.out.println("Fallo al actualizar el prestamo ID " + prestamoEncontrado.getIdPrestamo());
                }
            }

            System.out.println("\n--- Probando obtener prestamos de un usuario ---");
            List<Prestamo> prestamosDelUsuario = prestamoDAO.obtenerPrestamosPorUsuario(idUsuarioPrueba);
            if (!prestamosDelUsuario.isEmpty()) {
                System.out.println("Prestamos del usuario ID " + idUsuarioPrueba + ":");
                for (Prestamo p : prestamosDelUsuario) {
                    System.out.println("- Prestamo ID: " + p.getIdPrestamo() + ", Libro: " + p.getIsbnLibro() + ", Estado: " + p.getEstadoPrestamo());
                }
            } else {
                System.out.println("El usuario ID " + idUsuarioPrueba + " no tiene prestamos registrados.");
            }

        } else {
            System.out.println("Fallo total al registrar el primer prestamo. Algo salio mal.");
        }
    }
}

