public class CantidadInvalidaException extends RuntimeException{
    //el string mensaje atrapa el texto que se mande
    public CantidadInvalidaException(String mensaje) {
        //envia el texto del error a java para mostrarlo en consola
        super(mensaje);
    }
}
