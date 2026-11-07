/**
 * Métodos reutilizables del programa.
 */
public class Utilidades {

    /**
     * Método genérico: imprime cualquier arreglo (de Producto, String, etc.)
     * mostrando la posición y el contenido de cada elemento.
     * Usa el toString() de cada objeto.
     */
    public static <T> void imprimirArreglo(T[] datos) {
        for (int i = 0; i < datos.length; i++) {
            if (datos[i] != null) {
                System.out.println("[" + i + "] " + datos[i]);
            }
        }
    }
}
