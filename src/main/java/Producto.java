public class Producto {
    private String id;
    private String nombre;
    private double precio;
    private int stock;

    public Producto(String id, String nombre, double precio, int stock) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del producto no puede estar vacío.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío.");
        }
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0.");
        }
        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }

        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public boolean tieneStockSuficiente(int cantidad) {
        return cantidad > 0 && cantidad <= this.stock;
    }

    public void reducirStock(int cantidad) {
        if (cantidad <= 0) {
            throw new CantidadInvalidaException("La cantidad debe ser mayor a cero.");
        }
        if (cantidad > this.stock) {
            throw new StockInsuficienteException(
                "Stock insuficiente para " + nombre + " (Disponibles: " + stock + ")."
            );
        }
        this.stock -= cantidad;
    }

    public String obtenerDetalle() {
        String precioTexto = (precio % 1 == 0)
                ? String.valueOf((int) precio)
                : String.format("%.2f", precio);
        return nombre + " · Q" + precioTexto + " · stock " + stock;
    }

    @Override
    public String toString() {
        return obtenerDetalle();
    }
}