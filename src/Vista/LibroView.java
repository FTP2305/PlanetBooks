/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Conexion.ConexionBD;
import Controlador.AutorController;
import Controlador.EditorialController;
import Controlador.LibroController;
import Modelo.Autor;
import Modelo.Editorial;
import Modelo.Libro;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author felix
 */
public class LibroView extends JFrame {

    private LibroController libroController;
    private AutorController autorController;
    private EditorialController editorialController;

    private JTextField txtIsbn;
    private JTextField txtTitulo;
    private JComboBox<String> cmbAutor; 
    private JComboBox<String> cmbEditorial; 
    private JTextField txtAnioPublicacion;
    private JTextField txtStockTotal;
    private JTextField txtStockDisponible; 
    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JLabel lblMensaje;

    private java.util.Map<String, Integer> autorNameToIdMap;
    private java.util.Map<String, Integer> editorialNameToIdMap;

    public LibroView() {

        libroController = new LibroController();
        autorController = new AutorController();
        editorialController = new EditorialController();

        setTitle("Gestión de Libros");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setLocationRelativeTo(null); 

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridBagLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
        add(panelPrincipal);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); 
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblIsbn = new JLabel("ISBN:");
        txtIsbn = new JTextField(20);

        JLabel lblTitulo = new JLabel("Título:");
        txtTitulo = new JTextField(20);

        JLabel lblAutor = new JLabel("Autor Principal:");
        cmbAutor = new JComboBox<>();
        cargarAutores(); 

        JLabel lblEditorial = new JLabel("Editorial:");
        cmbEditorial = new JComboBox<>();
        cargarEditoriales(); 

        JLabel lblAnioPublicacion = new JLabel("Año Publicación:");
        txtAnioPublicacion = new JTextField(10);

        JLabel lblStockTotal = new JLabel("Stock Total:");
        txtStockTotal = new JTextField(10);

        JLabel lblStockDisponible = new JLabel("Stock Disponible:");
        txtStockDisponible = new JTextField(10);
        txtStockDisponible.setEditable(false); 

        btnGuardar = new JButton("Guardar Libro");
        btnLimpiar = new JButton("Limpiar Campos");
        lblMensaje = new JLabel("");
        lblMensaje.setForeground(Color.RED); 

        gbc.gridx = 0; gbc.gridy = 0; panelPrincipal.add(lblIsbn, gbc);
        gbc.gridx = 1; gbc.gridy = 0; panelPrincipal.add(txtIsbn, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panelPrincipal.add(lblTitulo, gbc);
        gbc.gridx = 1; gbc.gridy = 1; panelPrincipal.add(txtTitulo, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panelPrincipal.add(lblAutor, gbc);
        gbc.gridx = 1; gbc.gridy = 2; panelPrincipal.add(cmbAutor, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panelPrincipal.add(lblEditorial, gbc);
        gbc.gridx = 1; gbc.gridy = 3; panelPrincipal.add(cmbEditorial, gbc);

        gbc.gridx = 0; gbc.gridy = 4; panelPrincipal.add(lblAnioPublicacion, gbc);
        gbc.gridx = 1; gbc.gridy = 4; panelPrincipal.add(txtAnioPublicacion, gbc);

        gbc.gridx = 0; gbc.gridy = 5; panelPrincipal.add(lblStockTotal, gbc);
        gbc.gridx = 1; gbc.gridy = 5; panelPrincipal.add(txtStockTotal, gbc);

        gbc.gridx = 0; gbc.gridy = 6; panelPrincipal.add(lblStockDisponible, gbc);
        gbc.gridx = 1; gbc.gridy = 6; panelPrincipal.add(txtStockDisponible, gbc);

        txtStockTotal.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateStockDisponible();
            }
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateStockDisponible();
            }
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateStockDisponible();
            }
            private void updateStockDisponible() {
                try {
                    int total = Integer.parseInt(txtStockTotal.getText());
                    txtStockDisponible.setText(String.valueOf(total));
                } catch (NumberFormatException ex) {
                    txtStockDisponible.setText(""); 
                }
            }
        });


        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2; panelPrincipal.add(btnGuardar, gbc);
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2; panelPrincipal.add(btnLimpiar, gbc);
        gbc.gridx = 0; gbc.gridy = 9; gbc.gridwidth = 2; panelPrincipal.add(lblMensaje, gbc);

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarLibro();
            }
        });

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
    }

    private void cargarAutores() {
        List<Autor> autores = autorController.obtenerTodosLosAutores();
        autorNameToIdMap = new java.util.HashMap<>();
        cmbAutor.addItem("-- Seleccione Autor --");
        for (Autor autor : autores) {
            cmbAutor.addItem(autor.getNombreCompleto());
            autorNameToIdMap.put(autor.getNombreCompleto(), autor.getIdAutor());
        }
    }

    private void cargarEditoriales() {
        List<Editorial> editoriales = editorialController.obtenerTodasEditoriales();
        editorialNameToIdMap = new java.util.HashMap<>();
        cmbEditorial.addItem("-- Seleccione Editorial --");
        for (Editorial editorial : editoriales) {
            cmbEditorial.addItem(editorial.getNombreEditorial());
            editorialNameToIdMap.put(editorial.getNombreEditorial(), editorial.getIdEditorial());
        }
    }

    private void guardarLibro() {
        String isbn = txtIsbn.getText().trim();
        String titulo = txtTitulo.getText().trim();
        String autorSeleccionado = (String) cmbAutor.getSelectedItem();
        String editorialSeleccionada = (String) cmbEditorial.getSelectedItem();
        String anioPubStr = txtAnioPublicacion.getText().trim();
        String stockTotalStr = txtStockTotal.getText().trim();
        String stockDispStr = txtStockDisponible.getText().trim(); // Se actualiza automáticamente

        if (isbn.isEmpty() || titulo.isEmpty() || autorSeleccionado.equals("-- Seleccione Autor --") ||
            editorialSeleccionada.equals("-- Seleccione Editorial --") || anioPubStr.isEmpty() ||
            stockTotalStr.isEmpty() || stockDispStr.isEmpty()) {
            lblMensaje.setText("Por favor, complete todos los campos.");
            return;
        }

        try {
            int idAutor = autorNameToIdMap.get(autorSeleccionado);
            int idEditorial = editorialNameToIdMap.get(editorialSeleccionada);
            int anioPublicacion = Integer.parseInt(anioPubStr);
            int stockTotal = Integer.parseInt(stockTotalStr);
            int stockDisponible = Integer.parseInt(stockDispStr);

            Libro nuevoLibro = new Libro(isbn, titulo, idAutor, idEditorial, anioPublicacion, stockTotal, stockDisponible);
            String resultado = libroController.crearLibro(nuevoLibro);
            lblMensaje.setText(resultado); // Muestra el mensaje del controlador

            if (resultado.contains("exitosa")) {
                JOptionPane.showMessageDialog(this, "Libro guardado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar libro: " + resultado, "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            lblMensaje.setText("Error en formato numérico (Año, Stock).");
        } catch (NullPointerException ex) {
            lblMensaje.setText("Error al obtener ID de autor/editorial. Recargue la ventana.");
        }
    }

    private void limpiarCampos() {
        txtIsbn.setText("");
        txtTitulo.setText("");
        cmbAutor.setSelectedIndex(0);
        cmbEditorial.setSelectedIndex(0);
        txtAnioPublicacion.setText("");
        txtStockTotal.setText("");
        txtStockDisponible.setText("");
        lblMensaje.setText("");
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
            new LibroView().setVisible(true);
        });
    }
}
