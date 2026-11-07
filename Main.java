import java.util.Scanner;

/**
 * Clase principal de UVG Store.
 * Se encarga SOLO de la interfaz: menú, lectura de datos y mensajes.
 * Las reglas de venta están en la clase Tienda.
 */
public class Main {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        Tienda tienda = new Tienda();
        int opcion = -1;

        System.out.println("=== Bienvenido a UVG Store ===");

        while (opcion != 0) {
            mostrarMenu();
            opcion = leerEntero(teclado, "Elija una opción: ");

            switch (opcion) {
                case 1:
                    tienda.mostrarCatalogo();
                    break;
                case 2:
                    realizarVenta(teclado, tienda);
                    break;
                case 3:
                    tienda.mostrarInventario();
                    break;
                case 0:
                    System.out.println("Gracias por visitar UVG Store. ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }

        teclado.close();
    }

    // Muestra las opciones del menú
    private static void mostrarMenu() {
        System.out.println();
        System.out.println("--- MENÚ ---");
        System.out.println("1. Ver catálogo");
        System.out.println("2. Comprar un producto");
        System.out.println("3. Ver inventario actualizado");
        System.out.println("0. Salir");
    }

    // Pide producto y cantidad, y registra la venta
    private static void realizarVenta(Scanner teclado, Tienda tienda) {
        tienda.mostrarCatalogo();

        int posicion = leerEntero(teclado, "Seleccionar producto (número): ");
        int cantidad = leerEntero(teclado, "Cantidad: ");

        try {
            double total = tienda.vender(posicion, cantidad);
            System.out.println("Venta registrada. Total: Q" + total);
        } catch (CantidadInvalidaException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (StockInsuficienteException e) {
            System.out.println("Error: " + e.getMessage() + " No se modificó el inventario.");
        } catch (IllegalArgumentException e) {
            // Por si la posición del producto no existe
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Lee un número entero del teclado.
     * Si el usuario escribe texto, muestra un error y vuelve a pedir el dato
     */
    private static int leerEntero(Scanner teclado, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = teclado.nextLine();
            try {
                return Integer.parseInt(texto.trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida: debe escribir un número entero.");
            }
        }
    }
}
