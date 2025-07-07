/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Conexion.ConexionBD;
import Modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public boolean guardarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, apellido, email) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConexionBD.getConnection(); 
            pstmt = conn.prepareStatement(sql); 
            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getApellido());
            pstmt.setString(3, usuario.getEmail());

            int filasAfectadas = pstmt.executeUpdate(); 

            if (filasAfectadas > 0) {
                System.out.println("Usuario guardado chevere.");
                return true;
            } else {
                System.out.println("No se pudo guardar el usuario, algo raro paso.");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Error al guardar usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de guardar usuario: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public Usuario obtenerUsuarioPorId(int idUsuario) {
        String sql = "SELECT id_usuario, nombre, apellido, email FROM usuarios WHERE id_usuario = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Usuario usuario = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idUsuario); 
            rs = pstmt.executeQuery(); 

            if (rs.next()) { 
                usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setEmail(rs.getString("email"));
                System.out.println("Usuario encontrado sin problemas.");
            } else {
                System.out.println("Usuario con ID " + idUsuario + " no se encontro. Vuela a ver.");
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener usuario por ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de obtener usuario por ID: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return usuario;
    }

    public boolean actualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, email = ? WHERE id_usuario = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getApellido());
            pstmt.setString(3, usuario.getEmail());
            pstmt.setInt(4, usuario.getIdUsuario()); 

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Usuario actualizado bacan.");
                return true;
            } else {
                System.out.println("No se pudo actualizar el usuario, quizas no existe.");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de actualizar usuario: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public boolean eliminarUsuario(int idUsuario) {
        String sql = "DELETE FROM usuarios WHERE id_usuario = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idUsuario);

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Usuario eliminado sin piedad.");
                return true;
            } else {
                System.out.println("No se pudo eliminar el usuario, a lo mejor ya se fue.");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de eliminar usuario: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public List<Usuario> obtenerTodosUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT id_usuario, nombre, apellido, email FROM usuarios";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setEmail(rs.getString("email"));
                usuarios.add(usuario);
            }
            System.out.println("Lista de usuarios obtenida, mira que cantidad!");

        } catch (SQLException e) {
            System.err.println("Error al obtener todos los usuarios: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos despues de obtener todos los usuarios: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return usuarios;
    }
    //Prueba
    public static void main(String[] args) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        System.out.println("\n--- Probando guardar usuario ---");
        Usuario nuevoUsuario = new Usuario(0, "Juan", "Perez", "juan.perez@example.com");
        if (usuarioDAO.guardarUsuario(nuevoUsuario)) {
            System.out.println("Juan Perez se guardo. Checa tu BD!");
        }

        System.out.println("\n--- Probando obtener usuario por ID ---");
        Usuario usuarioEncontrado = usuarioDAO.obtenerUsuarioPorId(1); 
        if (usuarioEncontrado != null) {
            System.out.println("Encontre a: " + usuarioEncontrado.getNombre() + " " + usuarioEncontrado.getApellido());
        } else {
            System.out.println("No se encontro el usuario con ID 1.");
        }

        System.out.println("\n--- Probando actualizar usuario ---");
        if (usuarioEncontrado != null) {
            usuarioEncontrado.setEmail("juan.perez.nuevo@example.com");
            if (usuarioDAO.actualizarUsuario(usuarioEncontrado)) {
                System.out.println("Email de Juan actualizado. Revisa el ID: " + usuarioEncontrado.getIdUsuario());
            }
        }
        System.out.println("\n--- Probando obtener todos los usuarios ---");
        List<Usuario> todosLosUsuarios = usuarioDAO.obtenerTodosUsuarios();
        if (!todosLosUsuarios.isEmpty()) {
            System.out.println("Lista de usuarios:");
            for (Usuario u : todosLosUsuarios) {
                System.out.println("- " + u.getNombre() + " " + u.getApellido() + " (" + u.getEmail() + ")");
            }
        } else {
            System.out.println("No hay usuarios en la BD. Que triste.");
        }

        System.out.println("\n--- Probando eliminar usuario ---");      
        if (usuarioDAO.eliminarUsuario(3)) { 
            System.out.println("Usuario ID 3 fue borrado. Adios.");
        } else {
            System.out.println("No se pudo borrar el usuario ID 3. Sera que no existia?");
        }
    }
}
