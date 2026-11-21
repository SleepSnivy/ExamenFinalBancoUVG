public class Tienda {
    private Producto[] productos;

    public Tienda() {
        productos = new Producto[5];
        productos[0] = new Producto("0", "Cuaderno", 25, 10);
        productos[1] = new Producto("1", "Galleta", 6, 20);
        productos[2] = new ProductoEspecial("2", "Termo UVG", 65, 5, "Promocional");
        productos[3] = new Producto("3", "Lapicero", 5, 30);
        productos[4] = new ProductoEspecial("4", "Sudadera UVG", 150, 8, "Ropa");
    }

    public Producto[] getProductos() {
        return productos;
    }

    public void mostrarCatalogo() {
        System.out.println("\nCatálogo");
        Utilidades.imprimirArreglo(productos);
    }

    public void mostrarInventario() {
        System.out.println("\nInventario actualizado");
        Utilidades.imprimirArreglo(productos);
    }

    public double vender(int posicion, int cantidad)
            throws CantidadInvalidaException, StockInsuficienteException {
        if (posicion < 0 || posicion >= productos.length || productos[posicion] == null) {
            throw new IllegalArgumentException("El producto seleccionado no existe.");
        }

        if (cantidad <= 0) {
            throw new CantidadInvalidaException("La cantidad debe ser mayor a cero.");
        }

        Producto producto = productos[posicion];

        if (!producto.tieneStockSuficiente(cantidad)) {
            throw new StockInsuficienteException(
                "Stock insuficiente para " + producto.getNombre() + "; no se modifica inventario."
            );
        }

        producto.reducirStock(cantidad);
        return producto.getPrecio() * cantidad;
    }
}
