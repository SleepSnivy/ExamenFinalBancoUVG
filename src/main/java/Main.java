import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Las reglas de venta viven en la clase Tienda.
 * Los colores y estilos se definen en estilos.css.
 */
public class Main extends Application {

    private Tienda tienda = new Tienda();

    // Un botón por producto
    private ToggleButton[] botonesProductos;
    private ToggleGroup grupoProductos = new ToggleGroup();

    // Historial de ventas (máximo 50 ventas)
    private String[] historial = new String[50];
    private int totalVentas = 0;

    private TextField campoCantidad = new TextField();
    private Label etiquetaMensaje = new Label("Seleccione un producto para comenzar");

    @Override
    public void start(Stage ventana) {
        Label titulo = new Label("UVG Store");
        titulo.getStyleClass().add("titulo");
        Label subtitulo = new Label("Simulación de ventas de una tienda universitaria");
        subtitulo.getStyleClass().add("subtitulo");
        VBox encabezado = new VBox(2, titulo, subtitulo);
        encabezado.getStyleClass().add("encabezado");

        //Tarjeta de productos (un botón por producto
        Label tituloCatalogo = new Label("Elija un producto");
        tituloCatalogo.getStyleClass().add("seccion");
        VBox listaProductos = crearBotonesProductos();
        VBox tarjetaCatalogo = new VBox(10, tituloCatalogo, listaProductos);
        tarjetaCatalogo.getStyleClass().add("tarjeta");

        //Tarjeta de compra
        Label tituloCompra = new Label("Registrar compra");
        tituloCompra.getStyleClass().add("seccion");
        campoCantidad.setPromptText("Cantidad");
        HBox.setHgrow(campoCantidad, Priority.ALWAYS);

        Button botonComprar = new Button("Comprar");
        botonComprar.getStyleClass().add("boton-primario");
        botonComprar.setOnAction(e -> comprar());

        HBox formulario = new HBox(10, campoCantidad, botonComprar);
        VBox tarjetaCompra = new VBox(10, tituloCompra, formulario);
        tarjetaCompra.getStyleClass().add("tarjeta");

        //Mensajes
        etiquetaMensaje.getStyleClass().addAll("mensaje", "mensaje-info");
        etiquetaMensaje.setMaxWidth(Double.MAX_VALUE);
        etiquetaMensaje.setWrapText(true);

        //Botones inferiores
        Button botonHistorial = new Button("Historial");
        botonHistorial.getStyleClass().add("boton-secundario");
        botonHistorial.setOnAction(e -> verHistorial());

        Button botonSalir = new Button("Salir");
        botonSalir.getStyleClass().add("boton-salir");
        botonSalir.setOnAction(e -> salir());

        HBox botones = new HBox(10, botonHistorial, botonSalir);

        //Contenido y escena
        VBox contenido = new VBox(14, tarjetaCatalogo, tarjetaCompra, etiquetaMensaje, botones);
        contenido.setPadding(new Insets(18));

        VBox raiz = new VBox(encabezado, contenido);

        Scene escena = new Scene(raiz, 540, 700);
        escena.getStylesheets().add(getClass().getResource("/estilos.css").toExternalForm());

        ventana.setTitle("UVG Store");
        ventana.setScene(escena);
        ventana.show();
    }

    //Crea un botón por cada producto del arreglo
    private VBox crearBotonesProductos() {
        Producto[] productos = tienda.getProductos();
        botonesProductos = new ToggleButton[productos.length];
        VBox caja = new VBox(8);

        for (int i = 0; i < productos.length; i++) {
            if (productos[i] != null) {
                ToggleButton boton = new ToggleButton(productos[i].toString());
                boton.setToggleGroup(grupoProductos);
                boton.setUserData(i); // guarda la posición del producto
                boton.setMaxWidth(Double.MAX_VALUE);
                boton.getStyleClass().add("boton-producto");
                botonesProductos[i] = boton;
                caja.getChildren().add(boton);
            }
        }
        return caja;
    }

    //Lee el producto seleccionado y la cantidad, y pide la venta a la tienda
    private void comprar() {
        Toggle seleccionado = grupoProductos.getSelectedToggle();
        if (seleccionado == null) {
            mostrarMensaje("Error: seleccione un producto.", true);
            return;
        }

        try {
            int posicion = (int) seleccionado.getUserData();
            // Si el texto no es un número, parseInt lanza NumberFormatException
            int cantidad = Integer.parseInt(campoCantidad.getText().trim());

            String nombre = tienda.getProductos()[posicion].getNombre();
            double total = tienda.vender(posicion, cantidad);
            registrarVenta(nombre, cantidad, total);

            actualizarBotones();
            Utilidades.imprimirArreglo(tienda.getProductos()); // método genérico (consola)
            mostrarMensaje("Venta registrada · Total Q" + total, false);
            campoCantidad.clear();

        } catch (NumberFormatException e) {
            mostrarMensaje("Error: escriba la cantidad con números enteros.", true);
        } catch (CantidadInvalidaException | StockInsuficienteException e) {
            mostrarMensaje("Error: " + e.getMessage(), true);
        } catch (IllegalArgumentException e) {
            mostrarMensaje("Error: " + e.getMessage(), true);
        }
    }

    //Guarda una venta exitosa en el arreglo del historial
    private void registrarVenta(String nombre, int cantidad, double total) {
        if (totalVentas < historial.length) {
            historial[totalVentas] = nombre + " x" + cantidad + " · Q" + total;
            totalVentas++;
        }
    }

    //Muestra las ventas registradas usando el método genérico
    private void verHistorial() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Historial");
        alerta.setHeaderText("Historial de ventas");
        if (totalVentas == 0) {
            alerta.setContentText("Aún no hay ventas registradas.");
        } else {
            alerta.setContentText(Utilidades.arregloATexto(historial));
        }
        alerta.showAndWait();
    }

    //Actualiza el texto de cada botón para ver el stock nuevo
    private void actualizarBotones() {
        Producto[] productos = tienda.getProductos();
        for (int i = 0; i < productos.length; i++) {
            if (botonesProductos[i] != null) {
                botonesProductos[i].setText(productos[i].toString());
            }
        }
    }

    //Cambia el texto y el color del mensaje, verde si salió bien, rojo si hubo error
    private void mostrarMensaje(String texto, boolean esError) {
        etiquetaMensaje.setText(texto);
        etiquetaMensaje.getStyleClass().removeAll("mensaje-info", "mensaje-ok", "mensaje-error");
        etiquetaMensaje.getStyleClass().add(esError ? "mensaje-error" : "mensaje-ok");
    }

    //Salida controlada
    private void salir() {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Desea salir del programa?", ButtonType.YES, ButtonType.NO);
        alerta.setHeaderText(null);
        alerta.showAndWait().ifPresent(respuesta -> {
            if (respuesta == ButtonType.YES) {
                Platform.exit();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
