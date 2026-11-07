public class Producto {
    private String id;
    private String nombre;
    private double precio;
    private int stock;

    public Producto(String id, String nombre, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean reducirStock(int cantidad) {
        if (cantidad > 0 && cantidad <= stock) {
            this.stock -= cantidad;
            return true;
        }
        return false;
    }

    public String obtenerDetalle() {
        return String.format("[%s] %s · Q%.2f · Stock: %d", id, nombre, precio, stock);
    }

    @Override
    public String toString() {
        return obtenerDetalle();
    }
}