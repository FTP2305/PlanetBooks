/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

/**
 *
 * @author felix
 */
import Conexion.ConexionBD;
import Controlador.AutorController;
import Controlador.RecomendacionController;
import Controlador.UsuarioController;
import Modelo.Autor;
import Modelo.Libro;
import Modelo.Usuario;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RecomendationView extends JFrame {

    private RecomendacionController recomendacionController;
    private UsuarioController usuarioController;
    private AutorController autorController;

    private JTextField txtIdUsuarioRecomendacion;
    private JButton btnObtenerRecomendaciones;
    private JTextArea txtAreaRecomendaciones;
    private JScrollPane scrollPane;
    private JLabel lblMensaje;

    public RecomendationView() {
        recomendacionController = new RecomendacionController();
        usuarioController = new UsuarioController();

        setTitle("Recomendaciones de Libros");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(245, 245, 245));
        add(mainPanel);

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        inputPanel.setBorder(new TitledBorder("Recomendaciones Personalizadas"));
        inputPanel.setBackground(new Color(240, 248, 255)); // AliceBlue

        txtIdUsuarioRecomendacion = new JTextField(15);
        btnObtenerRecomendaciones = new JButton("Obtener Recomendaciones");

        btnObtenerRecomendaciones.setBackground(new Color(120, 190, 250)); // Un azul más claro
        btnObtenerRecomendaciones.setForeground(Color.WHITE);
        btnObtenerRecomendaciones.setPreferredSize(new Dimension(220, 35));

        inputPanel.add(new JLabel("ID Usuario (opcional):"));
        inputPanel.add(txtIdUsuarioRecomendacion);
        inputPanel.add(btnObtenerRecomendaciones);

        mainPanel.add(inputPanel, BorderLayout.NORTH);

        txtAreaRecomendaciones = new JTextArea();
        txtAreaRecomendaciones.setEditable(false);
        txtAreaRecomendaciones.setFont(new Font("Monospaced", Font.PLAIN, 13));
        txtAreaRecomendaciones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane = new JScrollPane(txtAreaRecomendaciones);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        lblMensaje = new JLabel("");
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        lblMensaje.setFont(new Font("SansSerif", Font.ITALIC, 12));
        lblMensaje.setForeground(Color.DARK_GRAY);
        lblMensaje.setBorder(new EmptyBorder(10, 0, 0, 0));
        mainPanel.add(lblMensaje, BorderLayout.SOUTH);

        btnObtenerRecomendaciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                obtenerRecomendaciones();
            }
        });
        obtenerRecomendaciones();
    }

    private void obtenerRecomendaciones() {
        String idUsuarioStr = txtIdUsuarioRecomendacion.getText().trim();
        List<Libro> recomendaciones = null;
        String tituloRecomendaciones = "";

        if (idUsuarioStr.isEmpty()) {
            recomendaciones = recomendacionController.obtenerRecomendacionesGenerales(); 
            tituloRecomendaciones = "Recomendaciones Generales (Top 5)";
            lblMensaje.setText("Mostrando recomendaciones generales.");
            lblMensaje.setForeground(Color.BLUE);
        } else {
            try {
                int idUsuario = Integer.parseInt(idUsuarioStr);
                Usuario usuario = usuarioController.obtenerUsuarioPorId(idUsuario);

                if (usuario == null) {
                    lblMensaje.setText("Usuario con ID " + idUsuario + " no encontrado. Mostrando recomendaciones generales.");
                    lblMensaje.setForeground(Color.ORANGE);
                    recomendaciones = recomendacionController.obtenerRecomendacionesGenerales();
                    tituloRecomendaciones = "Recomendaciones Generales (Usuario no válido)";
                } else {
                    recomendaciones = recomendacionController.obtenerRecomendacionesParaUsuario(idUsuario); 
                    tituloRecomendaciones = "Recomendaciones para " + usuario.getNombre() + " " + usuario.getApellido();
                    lblMensaje.setText("Mostrando recomendaciones personalizadas para usuario " + idUsuario + ".");
                    lblMensaje.setForeground(new Color(0, 128, 0));
                }
            } catch (NumberFormatException ex) {
                lblMensaje.setText("ID de usuario inválido. Ingrese un número o déjelo vacío para generales.");
                lblMensaje.setForeground(Color.RED);
                txtAreaRecomendaciones.setText("");
                return;
            }
        }

        mostrarResultados(recomendaciones, tituloRecomendaciones);
    }

    private void mostrarResultados(List<Libro> libros, String titulo) {
        StringBuilder sb = new StringBuilder();
        sb.append("--- ").append(titulo).append(" ---\n\n");

        if (libros == null || libros.isEmpty()) {
            sb.append("No hay recomendaciones disponibles en este momento.\n");
            lblMensaje.setText("No se encontraron recomendaciones.");
            lblMensaje.setForeground(Color.ORANGE);
        } else {
            sb.append(String.format("%-15s %-40s %-25s\n",
                    "ISBN", "Título", "Autor"));
            sb.append("-----------------------------------------------------------------------------------------\n");

            for (Libro libro : libros) {
                Autor autor = autorController.obtenerAutorPorId(libro.getIdAutorPrincipal());
                String nombreAutor = (autor != null) ? autor.getNombreCompleto() : "Desconocido";

                sb.append(String.format("%-15s %-40s %-25s\n",
                        libro.getIsbn(),
                        libro.getTitulo(),
                        nombreAutor));
            }
        }
        txtAreaRecomendaciones.setText(sb.toString());
        txtAreaRecomendaciones.setCaretPosition(0);
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
            new RecomendationView().setVisible(true);
        });
    }
}
