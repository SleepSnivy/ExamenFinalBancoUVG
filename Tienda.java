public class Tienda {
    private Producto[] productos;

    public Tienda() {
        // Inicialización de al menos 5 productos
        productos = new Producto[5];
        productos[0] = new Producto("0", "Cuaderno UVG", 25.00, 10);
        productos[1] = new Producto("1", "Galleta Nutritiva", 6.00, 20);
        productos[2] = new ProductoEspecial("2", "Termo UVG", 65.00, 5, "Promocional");
        productos[3] = new Producto("3", "Lapicero Azul", 4.50, 50);
        productos[4] = new ProductoEspecial("4", "Playera UVG", 120.00, 8, "Ropa");
    }

    public Producto[] getProductos() {
        return productos;
    }

    public void mostrarCatalogo() {
        System.out.println("\n--- CATÁLOGO DE PRODUCTOS ---");
        imprimirArreglo(productos);
    }

    public void mostrarInventario() {
        System.out.println("\n--- INVENTARIO ACTUALIZADO ---");
        imprimirArreglo(productos);
    }

    /**
     * Método genérico para imprimir cualquier arreglo de objetos (cumple requisito técnico).
     */
    public <T> void imprimirArreglo(T[] arreglo) {
        for (T elemento : arreglo) {
            if (elemento != null) {
                System.out.println(elemento.toString());
            }
        }
    }

    /**
     * Valida y procesa la venta de un producto.
     */
    public boolean procesarVenta(int indice, int cantidad) {
        if (indice < 0 || indice >= productos.length) {
            System.out.println("Error: El producto seleccionado no existe.");
            return false;
        }

        if (cantidad <= 0) {
            System.out.println("Error: La cantidad debe ser mayor a cero.");
            return false;
        }

        Producto producto = productos[indice];

        if (cantidad > producto.getStock()) {
            System.out.println("Error: Stock insuficiente. Stock actual del " + producto.getNombre() + ": " + producto.getStock());
            return false;
        }

        producto.reducirStock(cantidad);
        double total = producto.getPrecio() * cantidad;
        System.out.println("\n¡Venta registrada con éxito!");
        System.out.printf("Producto: %s | Cantidad: %d | Total: Q%.2f\n", producto.getNombre(), cantidad, total);
        System.out.println("Stock restante de " + producto.getNombre() + ": " + producto.getStock());
        return true;
    }
}