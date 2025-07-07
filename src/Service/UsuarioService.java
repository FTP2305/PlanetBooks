/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Dao.UsuarioDAO;
import Modelo.Usuario;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsuarioService {

    private UsuarioDAO usuarioDAO;
    private static final String EMAIL_PATTERN =
        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public boolean registrarUsuario(Usuario usuario) {
        System.out.println("Servicio: Intentando registrar usuario " + usuario.getNombre() + " " + usuario.getApellido());
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            System.out.println("Error: El nombre del usuario no puede ser vacío.");
            return false;
        }
        if (usuario.getApellido() == null || usuario.getApellido().trim().isEmpty()) {
            System.out.println("Error: El apellido del usuario no puede ser vacío.");
            return false;
        }
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            System.out.println("Error: El email del usuario no puede ser vacío.");
            return false;
        }
        if (!validarEmail(usuario.getEmail())) {
            System.out.println("Error: El formato del email no es válido: " + usuario.getEmail());
            return false;
        }
        List<Usuario> todosUsuarios = usuarioDAO.obtenerTodosUsuarios();
        for (Usuario u : todosUsuarios) {
            if (u.getEmail().equalsIgnoreCase(usuario.getEmail())) {
                System.out.println("Error: Ya existe un usuario con el email '" + usuario.getEmail() + "'.");
                return false;
            }
        }

        return usuarioDAO.guardarUsuario(usuario);
    }

    public Usuario obtenerUsuarioPorId(int idUsuario) {
        System.out.println("Servicio: Obteniendo usuario por ID " + idUsuario);
        return usuarioDAO.obtenerUsuarioPorId(idUsuario);
    }

    public List<Usuario> obtenerTodosUsuarios() {
        System.out.println("Servicio: Obteniendo todos los usuarios");
        return usuarioDAO.obtenerTodosUsuarios();
    }

    public boolean actualizarUsuario(Usuario usuario) {
        System.out.println("Servicio: Intentando actualizar usuario ID " + usuario.getIdUsuario());
        if (usuarioDAO.obtenerUsuarioPorId(usuario.getIdUsuario()) == null) {
            System.out.println("Error: Usuario con ID " + usuario.getIdUsuario() + " no encontrado para actualizar.");
            return false;
        }
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            System.out.println("Error: El nombre del usuario no puede ser vacío al actualizar.");
            return false;
        }
        if (usuario.getApellido() == null || usuario.getApellido().trim().isEmpty()) {
            System.out.println("Error: El apellido del usuario no puede ser vacío al actualizar.");
            return false;
        }
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            System.out.println("Error: El email del usuario no puede ser vacío al actualizar.");
            return false;
        }
        if (!validarEmail(usuario.getEmail())) {
            System.out.println("Error: El formato del email no es válido al actualizar: " + usuario.getEmail());
            return false;
        }
        List<Usuario> todosUsuarios = usuarioDAO.obtenerTodosUsuarios();
        for (Usuario u : todosUsuarios) {
            if (u.getIdUsuario() != usuario.getIdUsuario() && u.getEmail().equalsIgnoreCase(usuario.getEmail())) {
                System.out.println("Error: El email '" + usuario.getEmail() + "' ya está registrado por otro usuario.");
                return false;
            }
        }

        return usuarioDAO.actualizarUsuario(usuario);
    }

    public boolean eliminarUsuario(int idUsuario) {
        System.out.println("Servicio: Intentando eliminar usuario ID " + idUsuario);
        if (usuarioDAO.obtenerUsuarioPorId(idUsuario) == null) {
            System.out.println("Error: Usuario con ID " + idUsuario + " no encontrado para eliminar.");
            return false;
        }
        return usuarioDAO.eliminarUsuario(idUsuario);
    }

    private boolean validarEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
