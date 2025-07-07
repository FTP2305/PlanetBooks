
package Vista;

/**
 *
 * @author felix
 */
import Conexion.ConexionBD;
import Controlador.*;
import Modelo.Autor;
import Modelo.Categoria;
import Modelo.Editorial;
import Modelo.Libro;
import Modelo.Prestamo;
import Modelo.Usuario;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Set; 

public class AppMenu {

    // Instancias de los controladores
    private static AutorController autorController = new AutorController();
    private static EditorialController editorialController = new EditorialController();
    private static CategoriaController categoriaController = new CategoriaController();
    private static LibroController libroController = new LibroController();
    private static UsuarioController usuarioController = new UsuarioController();
    private static PrestamoController prestamoController = new PrestamoController();
    private static RecomendacionController recomendacionController = new RecomendacionController();
    private static LibroCategoriaController libroCategoriaController = new LibroCategoriaController();

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Iniciando el sistema de gestión de biblioteca...");

        try {
            ConexionBD.getConnection();
            System.out.println("Conexión a la base de datos establecida exitosamente.");
            System.out.println("--------------------------------------------------");
        } catch (Exception e) {
            System.err.println("Error FATAL al conectar a la base de datos: " + e.getMessage());
            System.err.println("Por favor, asegúrate de que el servidor MySQL esté corriendo y los datos de conexión en ConexionBD sean correctos.");
            System.err.println("La aplicación no puede continuar sin conexión a la base de datos.");
            return; // Termina la aplicación si no hay conexión
        }

        mostrarMenuPrincipal();
        System.out.println("Gracias por usar el sistema de gestión de biblioteca. ¡Hasta pronto!");
    }

    private static void mostrarMenuPrincipal() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Gestión de Usuarios");
            System.out.println("2. Gestión de Libros");
            System.out.println("3. Gestión de Préstamos");
            System.out.println("4. Gestión de Recomendaciones");
            System.out.println("5. Ver Catálogo Completo (Ordenado por Título)");
            System.out.println("6. Mantenimiento de Datos (Autores, Editoriales, Categorías)"); // Nuevo para agregar datos base
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1:
                    menuGestionUsuarios();
                    break;
                case 2:
                    menuGestionLibros();
                    break;
                case 3:
                    menuGestionPrestamos();
                    break;
                case 4:
                    menuGestionRecomendaciones();
                    break;
                case 5:
                    mostrarCatalogoCompleto();
                    break;
                case 6:
                    menuMantenimientoDatos();
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }

    // --- Menús de Gestión ---

    private static void menuGestionUsuarios() {
        int opcion;
        do {
            System.out.println("\n--- GESTIÓN DE USUARIOS ---");
            System.out.println("1. Registrar nuevo usuario");
            System.out.println("2. Listar todos los usuarios");
            System.out.println("3. Actualizar datos de usuario");
            System.out.println("4. Eliminar usuario");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1:
                    registrarNuevoUsuario();
                    break;
                case 2:
                    listarUsuarios();
                    break;
                case 3:
                    actualizarUsuario();
                    break;
                case 4:
                    eliminarUsuario();
                    break;
                case 0:
                    System.out.println("Volviendo al Menú Principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }

    private static void menuGestionLibros() {
        int opcion;
        do {
            System.out.println("\n--- GESTIÓN DE LIBROS ---");
            System.out.println("1. Registrar nuevo libro");
            System.out.println("2. Buscar libro por título");
            System.out.println("3. Buscar libro por autor");
            System.out.println("4. Buscar libro por categoría");
            System.out.println("5. Actualizar stock de libro");
            System.out.println("6. Eliminar libro");
            System.out.println("7. Asociar libro a categoría");
            System.out.println("8. Desasociar libro de categoría");
            System.out.println("9. Ver categorías de un libro");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1:
                    registrarNuevoLibro();
                    break;
                case 2:
                    buscarLibroPorTitulo();
                    break;
                case 3:
                    buscarLibroPorAutor();
                    break;
                case 4:
                    buscarLibroPorCategoria();
                    break;
                case 5:
                    actualizarStockLibro();
                    break;
                case 6:
                    eliminarLibro();
                    break;
                case 7:
                    asociarLibroACategoria();
                    break;
                case 8:
                    desasociarLibroDeCategoria();
                    break;
                case 9:
                    verCategoriasDeLibro();
                    break;
                case 0:
                    System.out.println("Volviendo al Menú Principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }

    private static void menuGestionPrestamos() {
        int opcion;
        do {
            System.out.println("\n--- GESTIÓN DE PRÉSTAMOS ---");
            System.out.println("1. Registrar nuevo préstamo");
            System.out.println("2. Registrar devolución de préstamo");
            System.out.println("3. Listar préstamos activos por usuario");
            System.out.println("4. Listar todos los préstamos");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1:
                    registrarNuevoPrestamo();
                    break;
                case 2:
                    registrarDevolucion();
                    break;
                case 3:
                    listarPrestamosActivosPorUsuario();
                    break;
                case 4:
                    listarTodosLosPrestamos();
                    break;
                case 0:
                    System.out.println("Volviendo al Menú Principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }

    private static void menuGestionRecomendaciones() {
        int opcion;
        do {
            System.out.println("\n--- GESTIÓN DE RECOMENDACIONES ---");
            System.out.println("1. Recomendar libros por libro similar");
            System.out.println("2. Recomendar libros para un usuario");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1:
                    recomendarLibrosPorLibro();
                    break;
                case 2:
                    recomendarLibrosParaUsuario();
                    break;
                case 0:
                    System.out.println("Volviendo al Menú Principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }

    private static void menuMantenimientoDatos() {
        int opcion;
        do {
            System.out.println("\n--- MANTENIMIENTO DE DATOS ---");
            System.out.println("1. Gestión de Autores");
            System.out.println("2. Gestión de Editoriales");
            System.out.println("3. Gestión de Categorías");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1:
                    menuGestionAutores();
                    break;
                case 2:
                    menuGestionEditoriales();
                    break;
                case 3:
                    menuGestionCategorias();
                    break;
                case 0:
                    System.out.println("Volviendo al Menú Principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }

    private static void menuGestionAutores() {
        int opcion;
        do {
            System.out.println("\n--- GESTIÓN DE AUTORES ---");
            System.out.println("1. Registrar nuevo autor");
            System.out.println("2. Listar todos los autores");
            System.out.println("3. Actualizar autor");
            System.out.println("4. Eliminar autor");
            System.out.println("0. Volver al Menú de Mantenimiento");
            System.out.print("Seleccione una opción: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1:
                    registrarAutor();
                    break;
                case 2:
                    listarAutores();
                    break;
                case 3:
                    actualizarAutor();
                    break;
                case 4:
                    eliminarAutor();
                    break;
                case 0:
                    System.out.println("Volviendo al Menú de Mantenimiento...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }

    private static void menuGestionEditoriales() {
        int opcion;
        do {
            System.out.println("\n--- GESTIÓN DE EDITORIALES ---");
            System.out.println("1. Registrar nueva editorial");
            System.out.println("2. Listar todas las editoriales");
            System.out.println("3. Actualizar editorial");
            System.out.println("4. Eliminar editorial");
            System.out.println("0. Volver al Menú de Mantenimiento");
            System.out.print("Seleccione una opción: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1:
                    registrarEditorial();
                    break;
                case 2:
                    listarEditoriales();
                    break;
                case 3:
                    actualizarEditorial();
                    break;
                case 4:
                    eliminarEditorial();
                    break;
                case 0:
                    System.out.println("Volviendo al Menú de Mantenimiento...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }

    private static void menuGestionCategorias() {
        int opcion;
        do {
            System.out.println("\n--- GESTIÓN DE CATEGORÍAS ---");
            System.out.println("1. Registrar nueva categoría");
            System.out.println("2. Listar todas las categorías");
            System.out.println("3. Actualizar categoría");
            System.out.println("4. Eliminar categoría");
            System.out.println("0. Volver al Menú de Mantenimiento");
            System.out.print("Seleccione una opción: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1:
                    registrarCategoria();
                    break;
                case 2:
                    listarCategorias();
                    break;
                case 3:
                    actualizarCategoria();
                    break;
                case 4:
                    eliminarCategoria();
                    break;
                case 0:
                    System.out.println("Volviendo al Menú de Mantenimiento...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }

    // --- Implementaciones de los Requerimientos Funcionales y CRUDs ---

    // REGISTRO DE USUARIOS
    private static void registrarNuevoUsuario() {
        System.out.println("\n--- Registrar Nuevo Usuario ---");
        System.out.print("Ingrese nombre del usuario: ");
        scanner.nextLine(); // Consumir el newline pendiente
        String nombre = scanner.nextLine();
        System.out.print("Ingrese apellido del usuario: ");
        String apellido = scanner.nextLine();
        System.out.print("Ingrese email del usuario: ");
        String email = scanner.nextLine();

        Usuario nuevoUsuario = new Usuario(0, nombre, apellido, email); // ID 0 para nuevo
        String resultado = usuarioController.crearUsuario(nuevoUsuario);
        System.out.println(resultado);
    }

    private static void listarUsuarios() {
        System.out.println("\n--- Listado de Usuarios ---");
        List<Usuario> usuarios = usuarioController.obtenerTodosUsuarios();
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return;
        }
        usuarios.forEach(System.out::println);
    }

    private static void actualizarUsuario() {
        System.out.println("\n--- Actualizar Usuario ---");
        System.out.print("Ingrese el ID del usuario a actualizar: ");
        int idUsuario = leerEntero();
        scanner.nextLine(); // Consumir el newline

        Usuario usuarioExistente = usuarioController.obtenerUsuarioPorId(idUsuario);
        if (usuarioExistente == null) {
            System.out.println("Usuario no encontrado con ID: " + idUsuario);
            return;
        }

        System.out.println("Usuario actual: " + usuarioExistente);
        System.out.print("Ingrese nuevo nombre (dejar vacío para mantener '" + usuarioExistente.getNombre() + "'): ");
        String nombre = scanner.nextLine();
        if (!nombre.isEmpty()) {
            usuarioExistente.setNombre(nombre);
        }

        System.out.print("Ingrese nuevo apellido (dejar vacío para mantener '" + usuarioExistente.getApellido() + "'): ");
        String apellido = scanner.nextLine();
        if (!apellido.isEmpty()) {
            usuarioExistente.setApellido(apellido);
        }

        System.out.print("Ingrese nuevo email (dejar vacío para mantener '" + usuarioExistente.getEmail() + "'): ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) {
            usuarioExistente.setEmail(email);
        }

        String resultado = usuarioController.actualizarUsuario(usuarioExistente);
        System.out.println(resultado);
    }

    private static void eliminarUsuario() {
        System.out.println("\n--- Eliminar Usuario ---");
        System.out.print("Ingrese el ID del usuario a eliminar: ");
        int idUsuario = leerEntero();

        String resultado = usuarioController.eliminarUsuario(idUsuario);
        System.out.println(resultado);
    }


    // REGISTRO DE LIBROS (con Autor, Editorial, Categorías)
    private static void registrarNuevoLibro() {
        System.out.println("\n--- Registrar Nuevo Libro ---");
        scanner.nextLine(); 
        System.out.print("Ingrese ISBN del libro (ej. 978-XXXXXXXXXX): ");
        String isbn = scanner.nextLine();
        System.out.print("Ingrese título del libro: ");
        String titulo = scanner.nextLine();

        listarAutores();
        System.out.print("Ingrese ID del autor principal: ");
        int idAutor = leerEntero();

        listarEditoriales();
        System.out.print("Ingrese ID de la editorial: ");
        int idEditorial = leerEntero();

        System.out.print("Ingrese año de publicación: ");
        int anioPublicacion = leerEntero();
        System.out.print("Ingrese stock total: ");
        int stockTotal = leerEntero();
        int stockDisponible = stockTotal;

        Libro nuevoLibro = new Libro(isbn, titulo, idAutor, idEditorial, anioPublicacion, stockTotal, stockDisponible);
        String resultado = libroController.crearLibro(nuevoLibro);
        System.out.println(resultado);

        if (resultado.contains("exitosa")) {
            System.out.print("¿Desea asociar categorías a este libro ahora? (s/n): ");
            scanner.nextLine(); // Consumir newline
            String respuesta = scanner.nextLine();
            if (respuesta.equalsIgnoreCase("s")) {
                asociarMultiplesCategoriasALibro(isbn);
            }
        }
    }

    private static void buscarLibroPorTitulo() {
        System.out.println("\n--- Buscar Libro por Título ---");
        scanner.nextLine(); 
        System.out.print("Ingrese parte del título a buscar: ");
        String titulo = scanner.nextLine();

        List<Libro> libros = libroController.buscarLibrosPorTitulo(titulo);
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros con ese título.");
        } else {
            System.out.println("Libros encontrados:");
            libros.forEach(System.out::println);
        }
    }

    private static void buscarLibroPorAutor() {
        System.out.println("\n--- Buscar Libro por Autor ---");
        scanner.nextLine(); 
        System.out.print("Ingrese parte del nombre del autor a buscar: ");
        String nombreAutor = scanner.nextLine();

        List<Libro> libros = libroController.buscarLibrosPorNombreAutor(nombreAutor);
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros de ese autor.");
        } else {
            System.out.println("Libros encontrados:");
            libros.forEach(System.out::println);
        }
    }

    private static void buscarLibroPorCategoria() {
        System.out.println("\n--- Buscar Libro por Categoría ---");
        listarCategorias();
        System.out.print("Ingrese el ID de la categoría (se incluirán subcategorías): ");
        int idCategoria = leerEntero();

        Set<Libro> libros = categoriaController.buscarLibrosEnCategoriaConSubcategorias(idCategoria);
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en esa categoría o sus subcategorías.");
        } else {
            System.out.println("Libros encontrados en la categoría y subcategorías:");
            libros.forEach(System.out::println);
        }
    }

    private static void actualizarStockLibro() {
        System.out.println("\n--- Actualizar Stock de Libro ---");
        scanner.nextLine(); // Consumir newline
        System.out.print("Ingrese ISBN del libro a actualizar: ");
        String isbn = scanner.nextLine();

        Libro libro = libroController.obtenerLibroPorIsbn(isbn);
        if (libro == null) {
            System.out.println("Libro no encontrado con ISBN: " + isbn);
            return;
        }

        System.out.println("Stock actual para '" + libro.getTitulo() + "': Total " + libro.getStockTotal() + ", Disponible " + libro.getStockDisponible());
        System.out.print("Ingrese nuevo stock total (o -1 para no cambiar): ");
        int nuevoStockTotal = leerEntero();
        System.out.print("Ingrese nuevo stock disponible (o -1 para no cambiar): ");
        int nuevoStockDisponible = leerEntero();

        if (nuevoStockTotal != -1) {
            libro.setStockTotal(nuevoStockTotal);
        }
        if (nuevoStockDisponible != -1) {
            libro.setStockDisponible(nuevoStockDisponible);
        }

        String resultado = libroController.actualizarLibro(libro);
        System.out.println(resultado);
    }

    private static void eliminarLibro() {
        System.out.println("\n--- Eliminar Libro ---");
        scanner.nextLine(); 
        System.out.print("Ingrese ISBN del libro a eliminar: ");
        String isbn = scanner.nextLine();

        String resultado = libroController.eliminarLibro(isbn);
        System.out.println(resultado);
    }

    private static void asociarLibroACategoria() {
        System.out.println("\n--- Asociar Libro a Categoría ---");
        scanner.nextLine(); 
        System.out.print("Ingrese ISBN del libro: ");
        String isbn = scanner.nextLine();

        listarCategorias();
        System.out.print("Ingrese ID de la categoría a asociar: ");
        int idCategoria = leerEntero();

        String resultado = libroCategoriaController.asociarLibroConCategoria(isbn, idCategoria);
        System.out.println(resultado);
    }

    private static void asociarMultiplesCategoriasALibro(String isbn) {
        String opcion;
        do {
            listarCategorias();
            System.out.print("Ingrese ID de la categoría a asociar (0 para terminar): ");
            int idCategoria = leerEntero();
            if (idCategoria == 0) {
                break;
            }
            String resultado = libroCategoriaController.asociarLibroConCategoria(isbn, idCategoria);
            System.out.println(resultado);
            System.out.print("¿Asociar otra categoría? (s/n): ");
            scanner.nextLine(); // Consumir newline
            opcion = scanner.nextLine();
        } while (opcion.equalsIgnoreCase("s"));
    }


    private static void desasociarLibroDeCategoria() {
        System.out.println("\n--- Desasociar Libro de Categoría ---");
        scanner.nextLine(); 
        System.out.print("Ingrese ISBN del libro: ");
        String isbn = scanner.nextLine();

        List<Categoria> categoriasActuales = libroCategoriaController.obtenerCategoriasDeLibro(isbn);
        if (categoriasActuales.isEmpty()) {
            System.out.println("El libro no tiene categorías asociadas.");
            return;
        }
        System.out.println("Categorías actuales de este libro:");
        categoriasActuales.forEach(System.out::println);

        System.out.print("Ingrese ID de la categoría a desasociar: ");
        int idCategoria = leerEntero();

        String resultado = libroCategoriaController.desasociarLibroDeCategoria(isbn, idCategoria);
        System.out.println(resultado);
    }

    private static void verCategoriasDeLibro() {
        System.out.println("\n--- Ver Categorías de Libro ---");
        scanner.nextLine(); 
        System.out.print("Ingrese ISBN del libro: ");
        String isbn = scanner.nextLine();

        List<Categoria> categorias = libroCategoriaController.obtenerCategoriasDeLibro(isbn);
        if (categorias.isEmpty()) {
            System.out.println("El libro '" + isbn + "' no tiene categorías asociadas.");
        } else {
            System.out.println("Categorías asociadas al libro '" + isbn + "':");
            categorias.forEach(System.out::println);
        }
    }


    // GESTIÓN DE PRÉSTAMOS
    private static void registrarNuevoPrestamo() {
        System.out.println("\n--- Registrar Nuevo Préstamo ---");
        listarUsuarios();
        System.out.print("Ingrese ID del usuario: ");
        int idUsuario = leerEntero();
        scanner.nextLine(); // Consumir newline

        System.out.print("Ingrese ISBN del libro a prestar: ");
        String isbnLibro = scanner.nextLine();

        System.out.print("Ingrese duración del préstamo en días: ");
        int duracionDias = leerEntero();

        String resultado = prestamoController.registrarNuevoPrestamo(idUsuario, isbnLibro, duracionDias);
        System.out.println(resultado);
    }

    private static void registrarDevolucion() {
        System.out.println("\n--- Registrar Devolución ---");
        System.out.print("Ingrese el ID del préstamo a devolver: ");
        int idPrestamo = leerEntero();

        String resultado = prestamoController.registrarDevolucion(idPrestamo);
        System.out.println(resultado);
    }

    private static void listarPrestamosActivosPorUsuario() {
        System.out.println("\n--- Listar Préstamos Activos por Usuario ---");
        System.out.print("Ingrese el ID del usuario: ");
        int idUsuario = leerEntero();

        List<Prestamo> prestamos = prestamoController.obtenerPrestamosActivosPorUsuario(idUsuario);
        if (prestamos.isEmpty()) {
            System.out.println("El usuario no tiene préstamos activos.");
        } else {
            System.out.println("Préstamos activos del usuario " + idUsuario + ":");
            prestamos.forEach(System.out::println);
        }
    }

    private static void listarTodosLosPrestamos() {
        System.out.println("\n--- Listar Todos los Préstamos ---");
        List<Prestamo> prestamos = prestamoController.obtenerTodosLosPrestamos();
        if (prestamos.isEmpty()) {
            System.out.println("No hay préstamos registrados.");
        } else {
            prestamos.forEach(System.out::println);
        }
    }

    // GESTIÓN DE RECOMENDACIONES
    private static void recomendarLibrosPorLibro() {
        System.out.println("\n--- Recomendar Libros por Libro Similar ---");
        scanner.nextLine(); // Consumir newline
        System.out.print("Ingrese ISBN del libro base para la recomendación: ");
        String isbnBase = scanner.nextLine();
        System.out.print("¿Cuántas recomendaciones desea? ");
        int limite = leerEntero();

        List<Libro> recomendaciones = recomendacionController.recomendarLibrosPorLibro(isbnBase, limite);
        if (recomendaciones.isEmpty()) {
            System.out.println("No se encontraron recomendaciones basadas en este libro.");
        } else {
            System.out.println("Libros recomendados basados en '" + isbnBase + "':");
            recomendaciones.forEach(System.out::println);
        }
    }

    private static void recomendarLibrosParaUsuario() {
        System.out.println("\n--- Recomendar Libros para un Usuario ---");
        listarUsuarios();
        System.out.print("Ingrese ID del usuario para la recomendación: ");
        int idUsuario = leerEntero();
        System.out.print("¿Cuántas recomendaciones desea? ");
        int limite = leerEntero();

        List<Libro> recomendaciones = recomendacionController.recomendarLibrosPorUsuario(idUsuario, limite);
        if (recomendaciones.isEmpty()) {
            System.out.println("No se encontraron recomendaciones para este usuario.");
        } else {
            System.out.println("Libros recomendados para el usuario " + idUsuario + ":");
            recomendaciones.forEach(System.out::println);
        }
    }

    // CATÁLOGO COMPLETO
    private static void mostrarCatalogoCompleto() {
        System.out.println("\n--- Catálogo Completo de Libros (Ordenado por Título) ---");
        List<Libro> catalogo = libroController.obtenerCatalogoOrdenadoPorTitulo();
        if (catalogo.isEmpty()) {
            System.out.println("El catálogo está vacío. No hay libros registrados.");
        } else {
            catalogo.forEach(System.out::println);
        }
    }

    // MANTENIMIENTO DE AUTORES, EDITORIALES, CATEGORÍAS

    // AUTORES
    private static void registrarAutor() {
        System.out.println("\n--- Registrar Nuevo Autor ---");
        scanner.nextLine(); 
        System.out.print("Ingrese nombre completo del autor: ");
        String nombre = scanner.nextLine();
        Autor nuevoAutor = new Autor(0, nombre); 
        System.out.println(autorController.crearAutor(nuevoAutor));
    }

    private static void listarAutores() {
        System.out.println("\n--- Listado de Autores ---");
        List<Autor> autores = autorController.obtenerTodosLosAutores();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
            return;
        }
        autores.forEach(System.out::println);
    }

    private static void actualizarAutor() {
        System.out.println("\n--- Actualizar Autor ---");
        System.out.print("Ingrese el ID del autor a actualizar: ");
        int idAutor = leerEntero();
        scanner.nextLine(); 

        Autor autorExistente = autorController.obtenerAutorPorId(idAutor);
        if (autorExistente == null) {
            System.out.println("Autor no encontrado con ID: " + idAutor);
            return;
        }

        System.out.println("Autor actual: " + autorExistente);
        System.out.print("Ingrese nuevo nombre completo (dejar vacío para mantener '" + autorExistente.getNombreCompleto() + "'): ");
        String nombre = scanner.nextLine();
        if (!nombre.isEmpty()) {
            autorExistente.setNombreCompleto(nombre);
        }

        System.out.println(autorController.actualizarAutor(autorExistente));
    }

    private static void eliminarAutor() {
        System.out.println("\n--- Eliminar Autor ---");
        System.out.print("Ingrese el ID del autor a eliminar: ");
        int idAutor = leerEntero();
        System.out.println(autorController.eliminarAutor(idAutor));
    }

    // EDITORIALES
    private static void registrarEditorial() {
        System.out.println("\n--- Registrar Nueva Editorial ---");
        scanner.nextLine(); 
        System.out.print("Ingrese nombre de la editorial: ");
        String nombre = scanner.nextLine();
        Editorial nuevaEditorial = new Editorial(0, nombre); 
        System.out.println(editorialController.crearEditorial(nuevaEditorial));
    }

    private static void listarEditoriales() {
        System.out.println("\n--- Listado de Editoriales ---");
        List<Editorial> editoriales = editorialController.obtenerTodasEditoriales();
        if (editoriales.isEmpty()) {
            System.out.println("No hay editoriales registradas.");
            return;
        }
        editoriales.forEach(System.out::println);
    }

    private static void actualizarEditorial() {
        System.out.println("\n--- Actualizar Editorial ---");
        System.out.print("Ingrese el ID de la editorial a actualizar: ");
        int idEditorial = leerEntero();
        scanner.nextLine(); 

        Editorial editorialExistente = editorialController.obtenerEditorialPorId(idEditorial);
        if (editorialExistente == null) {
            System.out.println("Editorial no encontrada con ID: " + idEditorial);
            return;
        }

        System.out.println("Editorial actual: " + editorialExistente);
        System.out.print("Ingrese nuevo nombre (dejar vacío para mantener '" + editorialExistente.getNombreEditorial() + "'): ");
        String nombre = scanner.nextLine();
        if (!nombre.isEmpty()) {
            editorialExistente.setNombreEditorial(nombre);
        }

        System.out.println(editorialController.actualizarEditorial(editorialExistente));
    }

    private static void eliminarEditorial() {
        System.out.println("\n--- Eliminar Editorial ---");
        System.out.print("Ingrese el ID de la editorial a eliminar: ");
        int idEditorial = leerEntero();
        System.out.println(editorialController.eliminarEditorial(idEditorial));
    }

    // CATEGORÍAS
    private static void registrarCategoria() {
        System.out.println("\n--- Registrar Nueva Categoría ---");
        scanner.nextLine(); 
        System.out.print("Ingrese nombre de la categoría: ");
        String nombre = scanner.nextLine();

        listarCategorias(); 
        System.out.print("Ingrese ID de la categoría padre (0 si no tiene padre): ");
        int idPadre = leerEntero();
        Integer idCategoriaPadre = (idPadre == 0) ? null : idPadre;

        Categoria nuevaCategoria = new Categoria(0, nombre, idCategoriaPadre); 
        System.out.println(categoriaController.crearCategoria(nuevaCategoria));
    }

    private static void listarCategorias() {
        System.out.println("\n--- Listado de Categorías ---");
        List<Categoria> categorias = categoriaController.obtenerTodasCategorias();
        if (categorias.isEmpty()) {
            System.out.println("No hay categorías registradas.");
            return;
        }
        categorias.forEach(System.out::println);
    }

    private static void actualizarCategoria() {
        System.out.println("\n--- Actualizar Categoría ---");
        System.out.print("Ingrese el ID de la categoría a actualizar: ");
        int idCategoria = leerEntero();
        scanner.nextLine();

        Categoria categoriaExistente = categoriaController.obtenerCategoriaPorId(idCategoria);
        if (categoriaExistente == null) {
            System.out.println("Categoría no encontrada con ID: " + idCategoria);
            return;
        }

        System.out.println("Categoría actual: " + categoriaExistente);
        System.out.print("Ingrese nuevo nombre (dejar vacío para mantener '" + categoriaExistente.getNombreCategoria() + "'): ");
        String nombre = scanner.nextLine();
        if (!nombre.isEmpty()) {
            categoriaExistente.setNombreCategoria(nombre);
        }

        System.out.print("Ingrese nuevo ID de categoría padre (0 para sin padre, -1 para no cambiar): ");
        int nuevoIdPadre = leerEntero();
        if (nuevoIdPadre == 0) {
            categoriaExistente.setIdCategoriaPadre(null);
        } else if (nuevoIdPadre != -1) {
            categoriaExistente.setIdCategoriaPadre(nuevoIdPadre);
        }

        System.out.println(categoriaController.actualizarCategoria(categoriaExistente));
    }

    private static void eliminarCategoria() {
        System.out.println("\n--- Eliminar Categoría ---");
        System.out.print("Ingrese el ID de la categoría a eliminar: ");
        int idCategoria = leerEntero();
        System.out.println(categoriaController.eliminarCategoria(idCategoria));
    }

    private static int leerEntero() {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número entero.");
                scanner.next(); // Descartar la entrada incorrecta
                System.out.print("Ingrese nuevamente: ");
            }
        }
    }

    private static String leerLinea() {
        scanner.nextLine(); 
        return scanner.nextLine();
    }
}
