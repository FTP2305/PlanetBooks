/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Conexion.ConexionBD;
import Controlador.AutorController;
import Controlador.CategoriaController;
import Controlador.EditorialController;
import Controlador.LibroController;
import Modelo.Autor;
import Modelo.Editorial;
import Modelo.Libro;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author felix
 */
public class SearchView extends JFrame {

    private LibroController libroController;
    private AutorController autorController;
    private CategoriaController categoriaController; 
    private EditorialController editorialController; 

    private JComboBox<String> cmbCriterioBusqueda;
    private JTextField txtValorBusqueda;
    private JButton btnBuscar;
    private JButton btnListarTodo;
    private JTextArea txtAreaResultados;
    private JScrollPane scrollPane;
    private JLabel lblMensaje;

    public SearchView() {
        libroController = new LibroController();
        autorController = new AutorController();
        editorialController = new EditorialController(); 
        categoriaController = new CategoriaController(); 

        setTitle("Búsqueda y Catálogo de Libros");
        setSize(700, 600); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(245, 245, 245));
        add(mainPanel);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        searchPanel.setBorder(new TitledBorder("Buscar Libros"));
        searchPanel.setBackground(new Color(240, 248, 255)); 

        cmbCriterioBusqueda = new JComboBox<>(new String[]{"Título", "Autor", "ISBN"}); 
        txtValorBusqueda = new JTextField(25); 
        btnBuscar = new JButton("Buscar");
        btnListarTodo = new JButton("Listar Todo el Catálogo");

        Dimension btnSize = new Dimension(150, 30);
        btnBuscar.setPreferredSize(btnSize);
        btnListarTodo.setPreferredSize(new Dimension(200, 30));
        btnBuscar.setBackground(new Color(100, 180, 230));
        btnBuscar.setForeground(Color.WHITE);
        btnListarTodo.setBackground(new Color(70, 190, 150)); 
        btnListarTodo.setForeground(Color.WHITE);

        searchPanel.add(new JLabel("Buscar por:"));
        searchPanel.add(cmbCriterioBusqueda);
        searchPanel.add(new JLabel("Valor:"));
        searchPanel.add(txtValorBusqueda);
        searchPanel.add(btnBuscar);
        searchPanel.add(btnListarTodo);

        mainPanel.add(searchPanel, BorderLayout.NORTH);

        txtAreaResultados = new JTextArea();
        txtAreaResultados.setEditable(false);
        txtAreaResultados.setFont(new Font("Monospaced", Font.PLAIN, 13)); 
        txtAreaResultados.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
        scrollPane = new JScrollPane(txtAreaResultados);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); 
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        lblMensaje = new JLabel("");
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        lblMensaje.setFont(new Font("SansSerif", Font.ITALIC, 12));
        lblMensaje.setForeground(Color.DARK_GRAY);
        lblMensaje.setBorder(new EmptyBorder(10, 0, 0, 0));
        mainPanel.add(lblMensaje, BorderLayout.SOUTH);

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarBusqueda();
            }
        });

        btnListarTodo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarTodoElCatalogo();
            }
        });
        listarTodoElCatalogo();
    }

    private void realizarBusqueda() {
        String criterio = (String) cmbCriterioBusqueda.getSelectedItem();
        String valor = txtValorBusqueda.getText().trim();

        if (valor.isEmpty()) {
            lblMensaje.setText("Ingrese un valor para la búsqueda.");
            lblMensaje.setForeground(Color.ORANGE);
            txtAreaResultados.setText("");
            return;
        }

        List<Libro> resultados = null;
        String mensajeBusqueda = "";

        switch (criterio) {
            case "Título":
                resultados = libroController.buscarLibrosPorTitulo(valor);
                mensajeBusqueda = "Buscando por título: '" + valor + "'";
                break;
            case "Autor":
                Autor autorEncontrado = autorController.buscarAutorPorNombre(valor); 
                if (autorEncontrado != null) {
                    resultados = libroController.buscarLibrosPorAutor(autorEncontrado.getIdAutor()); 
                    mensajeBusqueda = "Buscando por autor: '" + valor + "'";
                } else {
                    lblMensaje.setText("Autor '" + valor + "' no encontrado.");
                    lblMensaje.setForeground(Color.RED);
                    txtAreaResultados.setText("No se encontraron libros para el autor especificado.");
                    return;
                }
                break;
            case "ISBN": 
                Libro libroPorIsbn = libroController.obtenerLibroPorIsbn(valor);
                if (libroPorIsbn != null) {
                    resultados = java.util.Collections.singletonList(libroPorIsbn);
                    mensajeBusqueda = "Buscando por ISBN: '" + valor + "'";
                } else {
                    resultados = new java.util.ArrayList<>(); 
                }
                break;
            default:
                lblMensaje.setText("Criterio de búsqueda no válido.");
                lblMensaje.setForeground(Color.RED);
                return;
        }

        mostrarResultados(resultados, mensajeBusqueda);
    }

    private void listarTodoElCatalogo() {
        lblMensaje.setText("Cargando catálogo completo...");
        lblMensaje.setForeground(Color.BLUE);
        List<Libro> catalogo = libroController.obtenerCatalogoOrdenadoPorTitulo();
        mostrarResultados(catalogo, "Catálogo Completo de Libros");
    }

    private void mostrarResultados(List<Libro> libros, String titulo) {
        StringBuilder sb = new StringBuilder();
        sb.append("--- ").append(titulo).append(" ---\n\n");

        if (libros == null || libros.isEmpty()) {
            sb.append("No se encontraron resultados.\n");
            lblMensaje.setText("No se encontraron resultados para la búsqueda.");
            lblMensaje.setForeground(Color.ORANGE);
        } else {
            sb.append(String.format("%-15s %-30s %-25s %-20s %-5s %-5s\n",
                    "ISBN", "Título", "Autor", "Editorial", "Stock", "Disp."));
            sb.append("---------------------------------------------------------------------------------------------------------------------------------\n");

            for (Libro libro : libros) {
                Autor autor = autorController.obtenerAutorPorId(libro.getIdAutorPrincipal());
                Editorial editorial = editorialController.obtenerEditorialPorId(libro.getIdEditorial()); 

                String nombreAutor = (autor != null) ? autor.getNombreCompleto() : "Desconocido";
                String nombreEditorial = (editorial != null) ? editorial.getNombreEditorial() : "Desconocida";

                sb.append(String.format("%-15s %-30s %-25s %-20s %-5d %-5d\n",
                        libro.getIsbn(),
                        libro.getTitulo(),
                        nombreAutor,
                        nombreEditorial,
                        libro.getStockTotal(),
                        libro.getStockDisponible()));
            }
            lblMensaje.setText("Se encontraron " + libros.size() + " libro(s).");
            lblMensaje.setForeground(new Color(0, 128, 0)); 
        }
        txtAreaResultados.setText(sb.toString());
        txtAreaResultados.setCaretPosition(0); 
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
            new SearchView().setVisible(true);
        });
    }
}
