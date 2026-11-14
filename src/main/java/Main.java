import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * INTERFAZ: ventana de JavaFX. Solo muestra datos y lee entradas.
 * Las reglas de venta viven en la clase Tienda.
 * Los colores y estilos están en src/main/resources/estilos.css
 */
public class Main extends Application {

    private Tienda tienda = new Tienda();

    private TextArea areaCatalogo = new TextArea();
    private TextField campoPosicion = new TextField();
    private TextField campoCantidad = new TextField();
    private Label etiquetaMensaje = new Label("Bienvenido a UVG Store");

    @Override
    public void start(Stage ventana) {
        // ----- Encabezado -----
        Label titulo = new Label("UVG Store");
        titulo.getStyleClass().add("titulo");
        Label subtitulo = new Label("Simulación de ventas de una tienda universitaria");
        subtitulo.getStyleClass().add("subtitulo");
        VBox encabezado = new VBox(2, titulo, subtitulo);
        encabezado.getStyleClass().add("encabezado");

        // ----- Tarjeta del catálogo -----
        Label tituloCatalogo = new Label("Catálogo");
        tituloCatalogo.getStyleClass().add("seccion");
        areaCatalogo.setEditable(false);
        areaCatalogo.setPrefHeight(190);
        actualizarCatalogo();
        VBox tarjetaCatalogo = new VBox(10, tituloCatalogo, areaCatalogo);
        tarjetaCatalogo.getStyleClass().add("tarjeta");

        // ----- Tarjeta de compra -----
        Label tituloCompra = new Label("Registrar compra");
        tituloCompra.getStyleClass().add("seccion");
        campoPosicion.setPromptText("Posición (ej. 2)");
        campoCantidad.setPromptText("Cantidad");
        HBox.setHgrow(campoPosicion, Priority.ALWAYS);
        HBox.setHgrow(campoCantidad, Priority.ALWAYS);

        Button botonComprar = new Button("Comprar");
        botonComprar.getStyleClass().add("boton-primario");
        botonComprar.setOnAction(e -> comprar());

        HBox formulario = new HBox(10, campoPosicion, campoCantidad, botonComprar);
        VBox tarjetaCompra = new VBox(10, tituloCompra, formulario);
        tarjetaCompra.getStyleClass().add("tarjeta");

        // ----- Mensajes -----
        etiquetaMensaje.getStyleClass().addAll("mensaje", "mensaje-info");
        etiquetaMensaje.setMaxWidth(Double.MAX_VALUE);
        etiquetaMensaje.setWrapText(true);

        // ----- Botones inferiores -----
        Button botonInventario = new Button("Ver inventario");
        botonInventario.getStyleClass().add("boton-secundario");
        botonInventario.setOnAction(e -> {
            actualizarCatalogo();
            mostrarMensaje("Inventario actualizado.", false);
        });

        Button botonSalir = new Button("Salir");
        botonSalir.getStyleClass().add("boton-salir");
        botonSalir.setOnAction(e -> salir());

        HBox botones = new HBox(10, botonInventario, botonSalir);

        // ----- Contenido y escena -----
        VBox contenido = new VBox(14, tarjetaCatalogo, tarjetaCompra, etiquetaMensaje, botones);
        contenido.setPadding(new Insets(18));

        VBox raiz = new VBox(encabezado, contenido);

        Scene escena = new Scene(raiz, 540, 600);
        var css = getClass().getResource("/estilos.css");
        if (css != null) {
            escena.getStylesheets().add(css.toExternalForm());
        }

        ventana.setTitle("UVG Store");
        ventana.setScene(escena);
        ventana.show();
    }

    /** Lee los campos, valida el formato y pide la venta a la Tienda. */
    private void comprar() {
        try {
            // Si el texto no es un número, parseInt lanza NumberFormatException
            int posicion = Integer.parseInt(campoPosicion.getText().trim());
            int cantidad = Integer.parseInt(campoCantidad.getText().trim());

            double total = tienda.vender(posicion, cantidad);

            actualizarCatalogo();
            mostrarMensaje("Venta registrada · Total Q" + total, false);
            campoPosicion.clear();
            campoCantidad.clear();

        } catch (NumberFormatException e) {
            mostrarMensaje("Error: escriba solo números enteros.", true);
        } catch (CantidadInvalidaException | StockInsuficienteException e) {
            mostrarMensaje("Error: " + e.getMessage(), true);
        } catch (IllegalArgumentException e) {
            // Posición que no existe en el catálogo
            mostrarMensaje("Error: " + e.getMessage(), true);
        }
    }

    /** Muestra el arreglo de productos usando el método genérico. */
    private void actualizarCatalogo() {
        areaCatalogo.setText(Utilidades.arregloATexto(tienda.getProductos()));
    }

    /** Cambia el texto y el color del mensaje (verde si salió bien, rojo si hubo error). */
    private void mostrarMensaje(String texto, boolean esError) {
        etiquetaMensaje.setText(texto);
        etiquetaMensaje.getStyleClass().removeAll("mensaje-info", "mensaje-ok", "mensaje-error");
        etiquetaMensaje.getStyleClass().add(esError ? "mensaje-error" : "mensaje-ok");
    }

    /** Salida controlada: pide confirmación antes de cerrar. */
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
