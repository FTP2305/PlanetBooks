/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Conexion.ConexionBD;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 *
 * @author felix
 */
public class MainAppFrame extends JFrame {

    public MainAppFrame() {
        setTitle("Sistema de Gestión de Biblioteca");
        setSize(450, 550); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(25, 25, 25, 25));
        mainPanel.setBackground(new Color(245, 245, 245));
        add(mainPanel);

        
        JLabel lblTituloApp = new JLabel("Sistema de Gestión de Biblioteca", SwingConstants.CENTER);
        lblTituloApp.setFont(new Font("Arial", Font.BOLD, 24));
        lblTituloApp.setForeground(new Color(40, 40, 40));
        lblTituloApp.setBorder(new EmptyBorder(0, 0, 20, 0));
        mainPanel.add(lblTituloApp, BorderLayout.NORTH);

        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 1, 0, 15)); 
        buttonPanel.setBorder(new EmptyBorder(10, 50, 10, 50));
        buttonPanel.setBackground(new Color(245, 245, 245));

        JButton btnGestionLibros = new JButton("Gestión de Libros");
        JButton btnGestionUsuarios = new JButton("Gestión de Usuarios");
        JButton btnGestionPrestamos = new JButton("Gestión de Préstamos");
        JButton btnBuscarCatalogo = new JButton("Buscar Libros / Catálogo"); 
        JButton btnRecomendaciones = new JButton("Recomendaciones"); 
        JButton btnSalir = new JButton("Salir");

        Dimension buttonSize = new Dimension(250, 50);
        Font buttonFont = new Font("SansSerif", Font.BOLD, 16);

        JButton[] buttons = {btnGestionLibros, btnGestionUsuarios, btnGestionPrestamos, btnBuscarCatalogo, btnRecomendaciones, btnSalir};
        for (JButton btn : buttons) {
            btn.setPreferredSize(buttonSize);
            btn.setMaximumSize(buttonSize);
            btn.setFont(buttonFont);
            btn.setBackground(new Color(100, 180, 230));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(70, 160, 210), 2),
                    new EmptyBorder(5, 15, 5, 15)));
        }

        
        btnSalir.setBackground(new Color(220, 90, 90));
        btnSalir.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(180, 60, 60), 2),
                new EmptyBorder(5, 15, 5, 15)));

        
        btnBuscarCatalogo.setBackground(new Color(70, 190, 150)); // Verde
        btnBuscarCatalogo.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(50, 150, 110), 2),
                new EmptyBorder(5, 15, 5, 15)));

        btnRecomendaciones.setBackground(new Color(255, 170, 70)); // Naranja
        btnRecomendaciones.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 120, 50), 2),
                new EmptyBorder(5, 15, 5, 15)));


        buttonPanel.add(btnGestionLibros);
        buttonPanel.add(btnGestionUsuarios);
        buttonPanel.add(btnGestionPrestamos);
        buttonPanel.add(btnBuscarCatalogo); 
        buttonPanel.add(btnRecomendaciones); 
        buttonPanel.add(btnSalir);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);


        
        btnGestionLibros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LibroView().setVisible(true);
            }
        });

        btnGestionUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UsuarioView().setVisible(true);
            }
        });

        btnGestionPrestamos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PrestamoView().setVisible(true);
            }
        });

        btnBuscarCatalogo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SearchView().setVisible(true);
            }
        });

        btnRecomendaciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RecomendationView().setVisible(true);
            }
        });

        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(MainAppFrame.this,
                        "¿Está seguro que desea salir de la aplicación?", "Salir del Sistema",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    public static void main(String[] args) {
        try {
            ConexionBD.getConnection();
            System.out.println("Conexión a la base de datos establecida exitosamente para la GUI.");
        } catch (Exception e) {
            System.err.println("Error FATAL al conectar a la base de datos para la GUI: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos. Verifique la conexión.", "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SwingUtilities.invokeLater(() -> {
            new MainAppFrame().setVisible(true);
        });
    }
}
