public class Venta {
    private String producto;
    private double precio;

    public Venta(String producto, double precio) {
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0.");
        }
        if (producto == null || producto.trim().isEmpty()) {
            throw new IllegalArgumentException("El producto no puede estar vacío.");
        }
        this.producto = producto;
        this.precio = precio;
    }

    public String getProducto() {
        return producto;
    }

    public double getPrecio() {
        return precio;
    }

    public String obtenerDetalle() {
        return "Venta: " + producto + " - Q" + precio;
    }
}


