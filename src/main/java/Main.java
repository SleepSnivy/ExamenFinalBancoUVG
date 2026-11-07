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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * INTERFAZ: ventana de JavaFX. Solo muestra datos y lee entradas.
 * Las reglas de venta viven en la clase Tienda.
 */
public class Main extends Application {

    private Tienda tienda = new Tienda();

    private TextArea areaCatalogo = new TextArea();
    private TextField campoPosicion = new TextField();
    private TextField campoCantidad = new TextField();
    private Label etiquetaMensaje = new Label("Bienvenido a UVG Store");

    @Override
    public void start(Stage ventana) {
        // Título
        Label titulo = new Label("UVG Store");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        // Catálogo (solo lectura)
        areaCatalogo.setEditable(false);
        areaCatalogo.setPrefHeight(200);
        actualizarCatalogo();

        // Formulario de compra
        campoPosicion.setPromptText("Posición (ej. 2)");
        campoCantidad.setPromptText("Cantidad");
        Button botonComprar = new Button("Comprar");
        botonComprar.setOnAction(e -> comprar());
        HBox formulario = new HBox(10, campoPosicion, campoCantidad, botonComprar);

        // Botones de inventario y salida
        Button botonInventario = new Button("Ver inventario");
        botonInventario.setOnAction(e -> {
            actualizarCatalogo();
            mostrarMensaje("Inventario actualizado.", false);
        });
        Button botonSalir = new Button("Salir");
        botonSalir.setOnAction(e -> salir());
        HBox botones = new HBox(10, botonInventario, botonSalir);

        VBox raiz = new VBox(10, titulo, new Label("Catálogo:"), areaCatalogo,
                formulario, botones, etiquetaMensaje);
        raiz.setPadding(new Insets(15));

        ventana.setTitle("UVG Store");
        ventana.setScene(new Scene(raiz, 480, 450));
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

    private void mostrarMensaje(String texto, boolean esError) {
        etiquetaMensaje.setText(texto);
        etiquetaMensaje.setStyle(esError ? "-fx-text-fill: red;" : "-fx-text-fill: green;");
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
