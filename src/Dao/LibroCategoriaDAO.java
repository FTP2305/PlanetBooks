/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Conexion.ConexionBD;
import java.util.List;

public class LibroCategoriaDAO {

    public boolean agregarLibroACategoria(String isbnLibro, int idCategoria) {
        String sql = "INSERT INTO libros_categorias (isbn_libro, id_categoria) VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, isbnLibro);
            pstmt.setInt(2, idCategoria);

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Libro " + isbnLibro + " agregado a categoria " + idCategoria + " bien");
                return true;
            } else {
                System.out.println("No se pudo agregar el libro a la categoria");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error al agregar libro a categoria " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de agregar libro a categoria " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public List<Integer> obtenerCategoriasDeLibro(String isbnLibro) {
        List<Integer> idCategorias = new ArrayList<>();
        String sql = "SELECT id_categoria FROM libros_categorias WHERE isbn_libro = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, isbnLibro);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                idCategorias.add(rs.getInt("id_categoria"));
            }
            System.out.println("Categorias de libro " + isbnLibro + " obtenidas");
        } catch (SQLException e) {
            System.err.println("Error al obtener categorias de libro " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de obtener categorias de libro " + e.getMessage());
                e.printStackTrace();
            }
        }
        return idCategorias;
    }
    
    public List<String> obtenerLibrosEnCategoria(int idCategoria) {
        List<String> isbnsLibros = new ArrayList<>();
        String sql = "SELECT isbn_libro FROM libros_categorias WHERE id_categoria = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idCategoria);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                isbnsLibros.add(rs.getString("isbn_libro"));
            }
            System.out.println("Libros en categoria " + idCategoria + " obtenidos");
        } catch (SQLException e) {
            System.err.println("Error al obtener libros en categoria " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de obtener libros en categoria " + e.getMessage());
                e.printStackTrace();
            }
        }
        return isbnsLibros;
    }


    public boolean eliminarLibroDeCategoria(String isbnLibro, int idCategoria) {
        String sql = "DELETE FROM libros_categorias WHERE isbn_libro = ? AND id_categoria = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, isbnLibro);
            pstmt.setInt(2, idCategoria);

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Libro " + isbnLibro + " eliminado de categoria " + idCategoria + " bien");
                return true;
            } else {
                System.out.println("No se pudo eliminar el libro de la categoria. Sera que no estaban juntos?");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar libro de categoria " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de eliminar libro de categoria " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    public boolean asociarLibroCategoria(String isbnLibro, int idCategoria) {
        String sql = "INSERT INTO libro_categoria (isbn_libro, id_categoria) VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, isbnLibro);
            pstmt.setInt(2, idCategoria);

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Libro '" + isbnLibro + "' asociado a categoría " + idCategoria + " exitosamente.");
                return true;
            } else {
                System.out.println("No se pudo asociar el libro a la categoría.");
                return false;
            }
        } catch (SQLException e) {
            
            if (e.getSQLState().startsWith("23")) { 
                System.err.println("Advertencia: La asociación entre libro '" + isbnLibro + "' y categoría " + idCategoria + " ya existe o hay un problema de clave foránea.");
                return false; 
            }
            System.err.println("Error al asociar libro y categoría: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos después de asociar libro y categoría: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public boolean desasociarLibroCategoria(String isbnLibro, int idCategoria) {
        String sql = "DELETE FROM libro_categoria WHERE isbn_libro = ? AND id_categoria = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, isbnLibro);
            pstmt.setInt(2, idCategoria);

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Libro '" + isbnLibro + "' desasociado de categoría " + idCategoria + " exitosamente.");
                return true;
            } else {
                System.out.println("No se encontró la asociación entre el libro y la categoría para eliminar.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error al desasociar libro y categoría: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos después de desasociar libro y categoría: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        LibroCategoriaDAO libroCategoriaDAO = new LibroCategoriaDAO();
        
        String isbnEjemplo = "9781234567890"; 
        int idCategoriaEjemplo = 1;         
        int idCategoriaEjemplo2 = 2;        

        System.out.println("--- Probando agregar libro a categoria ---");
        if (libroCategoriaDAO.agregarLibroACategoria(isbnEjemplo, idCategoriaEjemplo)) {
            System.out.println("Libro agregado a categoria 1");
        } else {
            System.out.println("Fallo al agregar libro a categoria 1");
        }
        
        if (libroCategoriaDAO.agregarLibroACategoria(isbnEjemplo, idCategoriaEjemplo2)) {
            System.out.println("Libro agregado a categoria 2");
        } else {
            System.out.println("Fallo al agregar libro a categoria 2 o ya estaba");
        }


        System.out.println("--- Probando obtener categorias de libro ---");
        List<Integer> categoriasDeLibro = libroCategoriaDAO.obtenerCategoriasDeLibro(isbnEjemplo);
        if (!categoriasDeLibro.isEmpty()) {
            System.out.println("Categorias para el libro " + isbnEjemplo);
            for (Integer idCat : categoriasDeLibro) {
                System.out.println("- ID Categoria " + idCat);
            }
        } else {
            System.out.println("El libro no tiene categorias asignadas");
        }
        
        System.out.println("--- Probando obtener libros en categoria ---");
        List<String> librosEnCategoria = libroCategoriaDAO.obtenerLibrosEnCategoria(idCategoriaEjemplo);
        if (!librosEnCategoria.isEmpty()) {
            System.out.println("Libros en la categoria " + idCategoriaEjemplo);
            for (String isbn : librosEnCategoria) {
                System.out.println("- ISBN Libro " + isbn);
            }
        } else {
            System.out.println("No hay libros en la categoria " + idCategoriaEjemplo);
        }

        System.out.println("--- Probando eliminar libro de categoria CUIDADO ---");
        if (libroCategoriaDAO.eliminarLibroDeCategoria(isbnEjemplo, idCategoriaEjemplo)) {
            System.out.println("Libro eliminado de categoria 1");
        } else {
            System.out.println("Fallo al eliminar libro de categoria 1");
        }
        
        System.out.println("--- Verificando categorias despues de eliminar ---");
        categoriasDeLibro = libroCategoriaDAO.obtenerCategoriasDeLibro(isbnEjemplo);
        if (!categoriasDeLibro.isEmpty()) {
            System.out.println("Categorias restantes para el libro " + isbnEjemplo);
            for (Integer idCat : categoriasDeLibro) {
                System.out.println("- ID Categoria " + idCat);
            }
        } else {
            System.out.println("El libro ya no tiene categorias asignadas");
        }
    }

}
