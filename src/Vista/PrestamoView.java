/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Conexion.ConexionBD;
import Controlador.LibroController;
import Controlador.PrestamoController;
import Controlador.UsuarioController;
import Modelo.Libro;
import Modelo.Usuario;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
public class PrestamoView extends JFrame {

    private PrestamoController prestamoController;
    private UsuarioController usuarioController;
    private LibroController libroController;

    private JTextField txtIdUsuarioPrestamo;
    private JTextField txtIsbnLibroPrestamo;
    private JTextField txtDuracionDias;
    private JButton btnRegistrarPrestamo;
    private JLabel lblMensajePrestamo;

    private JTextField txtIdPrestamoDevolucion;
    private JButton btnRegistrarDevolucion;
    private JLabel lblMensajeDevolucion;

    public PrestamoView() {
        prestamoController = new PrestamoController();
        usuarioController = new UsuarioController();
        libroController = new LibroController();

        setTitle("Gestión de Préstamos y Devoluciones");
        setSize(550, 550); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15)); 
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        add(mainPanel);

        JPanel panelRegistroPrestamo = new JPanel(new GridBagLayout());
        panelRegistroPrestamo.setBorder(new TitledBorder("Registrar Nuevo Préstamo"));
        panelRegistroPrestamo.setBackground(new Color(240, 248, 255)); 
        GridBagConstraints gbcPrestamo = new GridBagConstraints();
        gbcPrestamo.insets = new Insets(8, 8, 8, 8);
        gbcPrestamo.fill = GridBagConstraints.HORIZONTAL;

        gbcPrestamo.gridx = 0; gbcPrestamo.gridy = 0; panelRegistroPrestamo.add(new JLabel("ID Usuario:"), gbcPrestamo);
        gbcPrestamo.gridx = 1; gbcPrestamo.gridy = 0; txtIdUsuarioPrestamo = new JTextField(15); panelRegistroPrestamo.add(txtIdUsuarioPrestamo, gbcPrestamo);

        gbcPrestamo.gridx = 0; gbcPrestamo.gridy = 1; panelRegistroPrestamo.add(new JLabel("ISBN Libro:"), gbcPrestamo);
        gbcPrestamo.gridx = 1; gbcPrestamo.gridy = 1; txtIsbnLibroPrestamo = new JTextField(15); panelRegistroPrestamo.add(txtIsbnLibroPrestamo, gbcPrestamo);

        gbcPrestamo.gridx = 0; gbcPrestamo.gridy = 2; panelRegistroPrestamo.add(new JLabel("Duración (días):"), gbcPrestamo);
        gbcPrestamo.gridx = 1; gbcPrestamo.gridy = 2; txtDuracionDias = new JTextField(5); panelRegistroPrestamo.add(txtDuracionDias, gbcPrestamo);

        gbcPrestamo.gridx = 0; gbcPrestamo.gridy = 3; gbcPrestamo.gridwidth = 2;
        btnRegistrarPrestamo = new JButton("Registrar Préstamo");
        btnRegistrarPrestamo.setPreferredSize(new Dimension(200, 35)); 
        panelRegistroPrestamo.add(btnRegistrarPrestamo, gbcPrestamo);

        gbcPrestamo.gridx = 0; gbcPrestamo.gridy = 4; gbcPrestamo.gridwidth = 2; gbcPrestamo.weighty = 1.0; 
        lblMensajePrestamo = new JLabel("");
        lblMensajePrestamo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblMensajePrestamo.setForeground(Color.DARK_GRAY);
        panelRegistroPrestamo.add(lblMensajePrestamo, gbcPrestamo);

        mainPanel.add(panelRegistroPrestamo, BorderLayout.NORTH);

        JPanel panelDevolucionPrestamo = new JPanel(new GridBagLayout());
        panelDevolucionPrestamo.setBorder(new TitledBorder("Registrar Devolución"));
        panelDevolucionPrestamo.setBackground(new Color(230, 240, 255)); 
        GridBagConstraints gbcDevolucion = new GridBagConstraints();
        gbcDevolucion.insets = new Insets(8, 8, 8, 8);
        gbcDevolucion.fill = GridBagConstraints.HORIZONTAL;

        gbcDevolucion.gridx = 0; gbcDevolucion.gridy = 0; panelDevolucionPrestamo.add(new JLabel("ID Préstamo:"), gbcDevolucion);
        gbcDevolucion.gridx = 1; gbcDevolucion.gridy = 0; txtIdPrestamoDevolucion = new JTextField(10); panelDevolucionPrestamo.add(txtIdPrestamoDevolucion, gbcDevolucion);

        gbcDevolucion.gridx = 0; gbcDevolucion.gridy = 1; gbcDevolucion.gridwidth = 2;
        btnRegistrarDevolucion = new JButton("Registrar Devolución");
        btnRegistrarDevolucion.setPreferredSize(new Dimension(200, 35)); 
        panelDevolucionPrestamo.add(btnRegistrarDevolucion, gbcDevolucion);

        gbcDevolucion.gridx = 0; gbcDevolucion.gridy = 2; gbcDevolucion.gridwidth = 2; gbcDevolucion.weighty = 1.0;
        lblMensajeDevolucion = new JLabel("");
        lblMensajeDevolucion.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblMensajeDevolucion.setForeground(Color.DARK_GRAY);
        panelDevolucionPrestamo.add(lblMensajeDevolucion, gbcDevolucion);

        mainPanel.add(panelDevolucionPrestamo, BorderLayout.CENTER);


        btnRegistrarPrestamo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarNuevoPrestamo();
            }
        });

        btnRegistrarDevolucion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarDevolucion();
            }
        });
    }

    private void registrarNuevoPrestamo() {
        String idUsuarioStr = txtIdUsuarioPrestamo.getText().trim();
        String isbnLibro = txtIsbnLibroPrestamo.getText().trim();
        String duracionDiasStr = txtDuracionDias.getText().trim();

        if (idUsuarioStr.isEmpty() || isbnLibro.isEmpty() || duracionDiasStr.isEmpty()) {
            lblMensajePrestamo.setText("Por favor, complete todos los campos para el préstamo.");
            lblMensajePrestamo.setForeground(Color.RED);
            return;
        }

        try {
            int idUsuario = Integer.parseInt(idUsuarioStr);
            int duracionDias = Integer.parseInt(duracionDiasStr);

            Usuario usuario = usuarioController.obtenerUsuarioPorId(idUsuario);
            Libro libro = libroController.obtenerLibroPorIsbn(isbnLibro);

            if (usuario == null) {
                lblMensajePrestamo.setText("Error: Usuario con ID " + idUsuario + " no encontrado.");
                lblMensajePrestamo.setForeground(Color.RED);
                return;
            }
            if (libro == null) {
                lblMensajePrestamo.setText("Error: Libro con ISBN " + isbnLibro + " no encontrado.");
                lblMensajePrestamo.setForeground(Color.RED);
                return;
            }
            if (libro.getStockDisponible() <= 0) {
                lblMensajePrestamo.setText("Error: Libro '" + libro.getTitulo() + "' no disponible (stock 0).");
                lblMensajePrestamo.setForeground(Color.RED);
                return;
            }


            String resultado = prestamoController.registrarNuevoPrestamo(idUsuario, isbnLibro, duracionDias);
            lblMensajePrestamo.setText(resultado);
            if (resultado.contains("exitosa")) {
                lblMensajePrestamo.setForeground(new Color(0, 128, 0)); 
                JOptionPane.showMessageDialog(this, "Préstamo registrado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCamposPrestamo();
            } else {
                lblMensajePrestamo.setForeground(Color.RED);
                JOptionPane.showMessageDialog(this, "Error al registrar préstamo: " + resultado, "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            lblMensajePrestamo.setText("ID de usuario o duración inválidos. Ingrese números.");
            lblMensajePrestamo.setForeground(Color.RED);
        }
    }

    private void registrarDevolucion() {
        String idPrestamoStr = txtIdPrestamoDevolucion.getText().trim();

        if (idPrestamoStr.isEmpty()) {
            lblMensajeDevolucion.setText("Ingrese el ID del préstamo a devolver.");
            lblMensajeDevolucion.setForeground(Color.ORANGE);
            return;
        }

        try {
            int idPrestamo = Integer.parseInt(idPrestamoStr);
            String resultado = prestamoController.registrarDevolucion(idPrestamo);
            lblMensajeDevolucion.setText(resultado);
            if (resultado.contains("exitosa")) {
                lblMensajeDevolucion.setForeground(new Color(0, 128, 0)); 
                JOptionPane.showMessageDialog(this, "Devolución registrada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCamposDevolucion();
            } else {
                lblMensajeDevolucion.setForeground(Color.RED);
                JOptionPane.showMessageDialog(this, "Error al registrar devolución: " + resultado, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            lblMensajeDevolucion.setText("ID de préstamo inválido. Ingrese un número.");
            lblMensajeDevolucion.setForeground(Color.RED);
        }
    }

    private void limpiarCamposPrestamo() {
        txtIdUsuarioPrestamo.setText("");
        txtIsbnLibroPrestamo.setText("");
        txtDuracionDias.setText("");
        lblMensajePrestamo.setText("");
        lblMensajePrestamo.setForeground(Color.DARK_GRAY); 
    }

    private void limpiarCamposDevolucion() {
        txtIdPrestamoDevolucion.setText("");
        lblMensajeDevolucion.setText("");
        lblMensajeDevolucion.setForeground(Color.DARK_GRAY); 
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
            new PrestamoView().setVisible(true);
        });
    }
}
