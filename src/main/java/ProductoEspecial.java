/Clase hija/
public class ProductoEspecial extends Producto {
    private String categoria;

    public ProductoEspecial(String id, String nombre, double precio, int stock, String categoria) {
        super(id, nombre, precio, stock);
        this.categoria = categoria;
    }

    public String getCategoria() { return categoria; }

    @Override
    public String obtenerDetalle() {
        return super.obtenerDetalle() + " (" + categoria + ")";
    }
}