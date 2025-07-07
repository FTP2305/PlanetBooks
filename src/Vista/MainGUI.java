/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Conexion.ConexionBD;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author felix
 */
public class MainGUI {
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
            LibroView libroFrame = new LibroView();
            libroFrame.setVisible(true);
        });
    }
}
