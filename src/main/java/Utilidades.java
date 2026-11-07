/**
 * Métodos reutilizables del proyecto.
 */
public class Utilidades {

    /**
     * Método genérico: convierte cualquier arreglo en texto, una línea por elemento.
     * Ejemplo: [0] Cuaderno · Q25.00 · stock 10
     * Se omiten las posiciones vacías (null).
     */
    public static <T> String arregloATexto(T[] datos) {
        StringBuilder texto = new StringBuilder();
        for (int i = 0; i < datos.length; i++) {
            if (datos[i] != null) {
                texto.append("[").append(i).append("] ")
                     .append(datos[i]).append("\n");
            }
        }
        return texto.toString();
    }

    /**
     * Método genérico: imprime un arreglo en la consola (útil para pruebas).
     */
    public static <T> void imprimirArreglo(T[] datos) {
        System.out.print(arregloATexto(datos));
    }
}
