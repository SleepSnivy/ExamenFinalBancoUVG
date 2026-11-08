public class Tienda {
    private Producto[] productos;

    public Tienda() {
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
        Utilidades.imprimirArreglo(productos);
    }

    public void mostrarInventario() {
        System.out.println("\n--- INVENTARIO ACTUALIZADO ---");
        Utilidades.imprimirArreglo(productos);
    }

    /**
     * Realiza la venta validando reglas de negocio y lanzando excepciones.
     */
    public double vender(int posicion, int cantidad) throws CantidadInvalidaException, StockInsuficienteException {
        if (posicion < 0 || posicion >= productos.length || productos[posicion] == null) {
            throw new IllegalArgumentException("El producto seleccionado no existe.");
        }

        if (cantidad <= 0) {
            throw new CantidadInvalidaException("La cantidad debe ser mayor a cero.");
        }

        Producto p = productos[posicion];

        if (cantidad > p.getStock()) {
            throw new StockInsuficienteException("Stock insuficiente para " + p.getNombre() + " (Disponibles: " + p.getStock() + ").");
        }

        p.reducirStock(cantidad);
        return p.getPrecio() * cantidad;
    }
}
