/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Usuario;
import Service.UsuarioService;
import java.util.List;

public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController() {
        this.usuarioService = new UsuarioService();
    }

    public String crearUsuario(Usuario usuario) {
        System.out.println("Controlador: Solicitud para crear usuario.");
        if (usuarioService.registrarUsuario(usuario)) {
            return "Usuario '" + usuario.getNombre() + " " + usuario.getApellido() + "' registrado exitosamente.";
        } else {
            return "Error al registrar usuario '" + usuario.getNombre() + " " + usuario.getApellido() + "'. Verifique los datos (ej. email duplicado).";
        }
    }

    public Usuario obtenerUsuarioPorId(int idUsuario) {
        System.out.println("Controlador: Solicitud para obtener usuario con ID " + idUsuario + ".");
        Usuario usuario = usuarioService.obtenerUsuarioPorId(idUsuario);
        if (usuario != null) {
            System.out.println("Usuario encontrado: " + usuario.getNombre() + " " + usuario.getApellido());
            return usuario;
        } else {
            System.out.println("Usuario con ID " + idUsuario + " no encontrado.");
            return null;
        }
    }

    public List<Usuario> obtenerTodosUsuarios() {
        System.out.println("Controlador: Solicitud para obtener todos los usuarios.");
        List<Usuario> usuarios = usuarioService.obtenerTodosUsuarios();
        System.out.println("Se encontraron " + usuarios.size() + " usuarios.");
        return usuarios;
    }

    public String actualizarUsuario(Usuario usuario) {
        System.out.println("Controlador: Solicitud para actualizar usuario ID " + usuario.getIdUsuario() + ".");
        if (usuarioService.actualizarUsuario(usuario)) {
            return "Usuario ID " + usuario.getIdUsuario() + " actualizado exitosamente.";
        } else {
            return "Error al actualizar usuario ID " + usuario.getIdUsuario() + ". Verifique los datos o si el usuario existe.";
        }
    }

    public String eliminarUsuario(int idUsuario) {
        System.out.println("Controlador: Solicitud para eliminar usuario ID " + idUsuario + ".");
        if (usuarioService.eliminarUsuario(idUsuario)) {
            return "Usuario ID " + idUsuario + " eliminado exitosamente.";
        } else {
            return "Error al eliminar usuario ID " + idUsuario + ". Podría tener préstamos activos o no existe.";
        }
    }
}
