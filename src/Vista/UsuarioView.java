/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Conexion.ConexionBD;
import Controlador.UsuarioController;
import Modelo.Usuario;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author felix
 */
public class UsuarioView extends JFrame {

    private UsuarioController usuarioController;

    private JTextField txtIdUsuario;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtEmail;

    private JButton btnRegistrar;
    private JButton btnBuscar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JLabel lblMensaje;

    public UsuarioView() {
        usuarioController = new UsuarioController();

        setTitle("Gestión de Usuarios");
        setSize(500, 450); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar ventana

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15)); 
        add(mainPanel);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(new TitledBorder("Datos del Usuario")); 
        inputPanel.setBackground(new Color(240, 248, 255)); 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8); 
        gbc.fill = GridBagConstraints.HORIZONTAL; 

        gbc.gridx = 0; gbc.gridy = 0; inputPanel.add(new JLabel("ID Usuario:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.8; txtIdUsuario = new JTextField(10); inputPanel.add(txtIdUsuario, gbc);
        gbc.gridx = 2; gbc.gridy = 0; gbc.weightx = 0.2; btnBuscar = new JButton("Buscar"); inputPanel.add(btnBuscar, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; inputPanel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 2; txtNombre = new JTextField(20); inputPanel.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1; inputPanel.add(new JLabel("Apellido:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 2; txtApellido = new JTextField(20); inputPanel.add(txtApellido, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1; inputPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.gridwidth = 2; txtEmail = new JTextField(20); inputPanel.add(txtEmail, gbc);

        mainPanel.add(inputPanel, BorderLayout.CENTER); 

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15)); 
        buttonPanel.setBackground(new Color(230, 230, 250)); 
        btnRegistrar = new JButton("Registrar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");

        Dimension buttonSize = new Dimension(120, 30);
        btnRegistrar.setPreferredSize(buttonSize);
        btnActualizar.setPreferredSize(buttonSize);
        btnEliminar.setPreferredSize(buttonSize);
        btnLimpiar.setPreferredSize(buttonSize);

        buttonPanel.add(btnRegistrar);
        buttonPanel.add(btnActualizar);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnLimpiar);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        messagePanel.setBorder(new EmptyBorder(5, 0, 0, 0)); 
        lblMensaje = new JLabel("");
        lblMensaje.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblMensaje.setForeground(Color.DARK_GRAY); 

        messagePanel.add(lblMensaje);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 3; 
        gbc.insets = new Insets(15, 8, 0, 8); 
        gbc.weighty = 1.0; 
        inputPanel.add(lblMensaje, gbc);

        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarUsuario();
            }
        });

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarUsuario();
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarUsuario();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarUsuario();
            }
        });

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
    }

    private void registrarUsuario() {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String email = txtEmail.getText().trim();

        if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty()) {
            lblMensaje.setText("Por favor, complete todos los campos.");
            lblMensaje.setForeground(Color.RED);
            return;
        }

        Usuario nuevoUsuario = new Usuario(0, nombre, apellido, email);
        String resultado = usuarioController.crearUsuario(nuevoUsuario);
        lblMensaje.setText(resultado);
        if (resultado.contains("exitosa")) {
            lblMensaje.setForeground(new Color(0, 128, 0)); 
            JOptionPane.showMessageDialog(this, "Usuario registrado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
        } else {
            lblMensaje.setForeground(Color.RED);
            JOptionPane.showMessageDialog(this, "Error al registrar usuario: " + resultado, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarUsuario() {
        String idStr = txtIdUsuario.getText().trim();
        if (idStr.isEmpty()) {
            lblMensaje.setText("Ingrese un ID de usuario para buscar.");
            lblMensaje.setForeground(Color.ORANGE); 
            return;
        }

        try {
            int idUsuario = Integer.parseInt(idStr);
            Usuario usuario = usuarioController.obtenerUsuarioPorId(idUsuario);

            if (usuario != null) {
                txtNombre.setText(usuario.getNombre());
                txtApellido.setText(usuario.getApellido());
                txtEmail.setText(usuario.getEmail());
                lblMensaje.setText("Usuario encontrado. Modifique y actualice o elimine.");
                lblMensaje.setForeground(Color.BLUE);
            } else {
                limpiarCamposSinId(); 
                txtIdUsuario.setText(idStr); 
                lblMensaje.setText("Usuario no encontrado con ID: " + idStr);
                lblMensaje.setForeground(Color.RED);
            }
        } catch (NumberFormatException ex) {
            lblMensaje.setText("ID de usuario inválido. Ingrese un número entero.");
            lblMensaje.setForeground(Color.RED);
        }
    }

    private void actualizarUsuario() {
        String idStr = txtIdUsuario.getText().trim();
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String email = txtEmail.getText().trim();

        if (idStr.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || email.isEmpty()) {
            lblMensaje.setText("Complete todos los campos y el ID para actualizar.");
            lblMensaje.setForeground(Color.RED);
            return;
        }

        try {
            int idUsuario = Integer.parseInt(idStr);
            Usuario usuarioExistente = new Usuario(idUsuario, nombre, apellido, email);
            String resultado = usuarioController.actualizarUsuario(usuarioExistente);
            lblMensaje.setText(resultado);
            if (resultado.contains("exitosa")) {
                lblMensaje.setForeground(new Color(0, 128, 0)); 
                JOptionPane.showMessageDialog(this, "Usuario actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
            } else {
                lblMensaje.setForeground(Color.RED);
                JOptionPane.showMessageDialog(this, "Error al actualizar usuario: " + resultado, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            lblMensaje.setText("ID de usuario inválido. Ingrese un número entero.");
            lblMensaje.setForeground(Color.RED);
        }
    }

    private void eliminarUsuario() {
        String idStr = txtIdUsuario.getText().trim();
        if (idStr.isEmpty()) {
            lblMensaje.setText("Ingrese el ID del usuario a eliminar.");
            lblMensaje.setForeground(Color.ORANGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar este usuario?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.NO_OPTION) {
            lblMensaje.setText("Eliminación cancelada.");
            lblMensaje.setForeground(Color.DARK_GRAY);
            return;
        }

        try {
            int idUsuario = Integer.parseInt(idStr);
            String resultado = usuarioController.eliminarUsuario(idUsuario);
            lblMensaje.setText(resultado);
            if (resultado.contains("exitosa")) {
                lblMensaje.setForeground(new Color(0, 128, 0)); 
                JOptionPane.showMessageDialog(this, "Usuario eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
            } else {
                lblMensaje.setForeground(Color.RED);
                JOptionPane.showMessageDialog(this, "Error al eliminar usuario: " + resultado, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            lblMensaje.setText("ID de usuario inválido. Ingrese un número entero.");
            lblMensaje.setForeground(Color.RED);
        }
    }

    private void limpiarCampos() {
        txtIdUsuario.setText("");
        limpiarCamposSinId();
    }

    private void limpiarCamposSinId() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtEmail.setText("");
        lblMensaje.setText("");
        lblMensaje.setForeground(Color.DARK_GRAY); 
    }

    public static void main(String[] args) {
        try {
            ConexionBD.getConnection();
            System.out.println("Conexión a la base de datos establecida exitosamente.");
        } catch (Exception e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos. Verifique la conexión.", "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SwingUtilities.invokeLater(() -> {
            new UsuarioView().setVisible(true);
        });
    }
}
