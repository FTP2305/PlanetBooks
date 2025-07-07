
package Dao; 

import Conexion.ConexionBD;

import Modelo.Libro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {

    public boolean guardarLibro(Libro libro) {
        String sql = "INSERT INTO libros (isbn, titulo, id_autor_principal, id_editorial, anio_publicacion, stock_total, stock_disponible) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, libro.getIsbn());
            pstmt.setString(2, libro.getTitulo());
            pstmt.setInt(3, libro.getIdAutorPrincipal());
            pstmt.setInt(4, libro.getIdEditorial());
            pstmt.setInt(5, libro.getAnioPublicacion());
            pstmt.setInt(6, libro.getStockTotal());
            pstmt.setInt(7, libro.getStockDisponible());

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al guardar libro: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de guardar libro: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    public List<Libro> obtenerLibrosPorAutor(int idAutor) { 
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libros WHERE id_autor_principal = ?"; 
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idAutor);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Libro libro = new Libro(
                    rs.getString("isbn"),
                    rs.getString("titulo"),
                    rs.getInt("id_autor_principal"), 
                    rs.getInt("id_editorial"),
                    rs.getInt("anio_publicacion"),
                    rs.getInt("stock_total"),
                    rs.getInt("stock_disponible")
                );
                libros.add(libro);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener libros por autor en DAO: " + e.getMessage());
        } finally {
        }
        return libros;
    }
     public List<Libro> obtenerCatalogoOrdenadoPorTitulo() {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT isbn, titulo, id_autor_principal, id_editorial, anio_publicacion, genero, stock_total, stock_disponible FROM libros ORDER BY titulo ASC";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Libro libro = new Libro(
                    rs.getString("isbn"),
                    rs.getString("titulo"),
                    rs.getInt("id_autor_principal"),
                    rs.getInt("id_editorial"),
                    rs.getInt("anio_publicacion"),
                    rs.getInt("stock_total"),
                    rs.getInt("stock_disponible")
                );
                libros.add(libro);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el catálogo de libros: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos en obtenerCatalogoOrdenadoPorTitulo: " + e.getMessage());
            }
        }
        return libros;
    }

    public Libro obtenerLibroPorIsbn(String isbn) {
        String sql = "SELECT isbn, titulo, id_autor_principal, id_editorial, anio_publicacion, stock_total, stock_disponible FROM libros WHERE isbn = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Libro libro = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, isbn);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                libro = new Libro();
                libro.setIsbn(rs.getString("isbn"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setIdAutorPrincipal(rs.getInt("id_autor_principal"));
                libro.setIdEditorial(rs.getInt("id_editorial"));
                libro.setAnioPublicacion(rs.getInt("anio_publicacion"));
                libro.setStockTotal(rs.getInt("stock_total"));
                libro.setStockDisponible(rs.getInt("stock_disponible"));
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener libro por ISBN: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de obtener libro por ISBN: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return libro;
    }

    public boolean actualizarLibro(Libro libro) {
        String sql = "UPDATE libros SET titulo = ?, id_autor_principal = ?, id_editorial = ?, anio_publicacion = ?, stock_total = ?, stock_disponible = ? WHERE isbn = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, libro.getTitulo());
            pstmt.setInt(2, libro.getIdAutorPrincipal());
            pstmt.setInt(3, libro.getIdEditorial());
            pstmt.setInt(4, libro.getAnioPublicacion());
            pstmt.setInt(5, libro.getStockTotal());
            pstmt.setInt(6, libro.getStockDisponible());
            pstmt.setString(7, libro.getIsbn());

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar libro: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de actualizar libro: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public boolean eliminarLibro(String isbn) {
        String sql = "DELETE FROM libros WHERE isbn = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, isbn);

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar libro: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de eliminar libro: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public List<Libro> obtenerTodosLosLibros() {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT isbn, titulo, id_autor_principal, id_editorial, anio_publicacion, stock_total, stock_disponible FROM libros";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Libro libro = new Libro(
                    rs.getString("isbn"),
                    rs.getString("titulo"),
                    rs.getInt("id_autor_principal"), 
                    rs.getInt("id_editorial"),
                    rs.getInt("anio_publicacion"),
                    rs.getInt("stock_total"),
                    rs.getInt("stock_disponible")
                );
                libros.add(libro);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los libros: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de obtener todos los libros: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return libros;
    }


    public boolean actualizarStock(String isbn, int nuevaCantidadDisponible) {
        String sql = "UPDATE libros SET stock_disponible = ? WHERE isbn = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, nuevaCantidadDisponible);
            pstmt.setString(2, isbn);

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar stock de libro: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de actualizar stock: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public List<Libro> buscarLibrosPorTitulo(String titulo) {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT isbn, titulo, id_autor_principal, id_editorial, anio_publicacion, stock_total, stock_disponible FROM libros WHERE titulo LIKE ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + titulo + "%"); 

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Libro libro = new Libro();
                libro.setIsbn(rs.getString("isbn"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setIdAutorPrincipal(rs.getInt("id_autor_principal"));
                libro.setIdEditorial(rs.getInt("id_editorial"));
                libro.setAnioPublicacion(rs.getInt("anio_publicacion"));
                libro.setStockTotal(rs.getInt("stock_total"));
                libro.setStockDisponible(rs.getInt("stock_disponible"));
                libros.add(libro);
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar libros por titulo: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de buscar libros por titulo: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return libros;
    }

    public List<Libro> buscarLibrosPorAutor(int idAutor) {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT isbn, titulo, id_autor_principal, id_editorial, anio_publicacion, stock_total, stock_disponible FROM libros WHERE id_autor_principal = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idAutor);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Libro libro = new Libro();
                libro.setIsbn(rs.getString("isbn"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setIdAutorPrincipal(rs.getInt("id_autor_principal"));
                libro.setIdEditorial(rs.getInt("id_editorial"));
                libro.setAnioPublicacion(rs.getInt("anio_publicacion"));
                libro.setStockTotal(rs.getInt("stock_total"));
                libro.setStockDisponible(rs.getInt("stock_disponible"));
                libros.add(libro);
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar libros por autor: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de buscar libros por autor: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return libros;
    }

    public List<Libro> obtenerTodosLibrosOrdenadosPorTitulo() {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT isbn, titulo, id_autor_principal, id_editorial, anio_publicacion, stock_total, stock_disponible FROM libros ORDER BY titulo ASC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Libro libro = new Libro();
                libro.setIsbn(rs.getString("isbn"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setIdAutorPrincipal(rs.getInt("id_autor_principal"));
                libro.setIdEditorial(rs.getInt("id_editorial"));
                libro.setAnioPublicacion(rs.getInt("anio_publicacion"));
                libro.setStockTotal(rs.getInt("stock_total"));
                libro.setStockDisponible(rs.getInt("stock_disponible"));
                libros.add(libro);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener libros ordenados por titulo: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de obtener libros ordenados: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return libros;
    }
}