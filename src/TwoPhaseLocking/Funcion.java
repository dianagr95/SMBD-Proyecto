package TwoPhaseLocking;


/**
 * Clase que simula una definicion de funcion.
 * @author esmeralda
 */
public class Funcion {
    
    /**
     * Bandera que define si esta funcion acota una transaccion.
     */
    private final boolean defineTransaccion;
    
    /**
     * Bandera que define si esta funcion marca el inicio o el fin de la transaccio
     * que acota. Requiere @defineTransaccion = true para que tenga significado.
     */
    private final boolean iniciaTransaccion;
    
    /**
     * Bandera que define si esta funcion aplica un bloqueo Compartido.
     */
    private final boolean bloqueoCompartido;
    
    /**
     * Bandera que define si esta funcion aplica un bloqueo Exclusivo.
     */
    private final boolean bloqueoExclusivo;
    
    /**
     * Nombre de la funcion, se utiliza una letra minuscula.
     */
    private final String nombre;

    
    public Funcion(boolean defineTransaccion, boolean iniciaTransaccion, boolean bloqueoCompartido, boolean bloqueoExclusivo, String nombre) {
        this.defineTransaccion = defineTransaccion;
        this.iniciaTransaccion = iniciaTransaccion;
        this.bloqueoCompartido = bloqueoCompartido;
        this.bloqueoExclusivo = bloqueoExclusivo;
        this.nombre = nombre;
    }

    public boolean isDefineTransaccion() {
        return defineTransaccion;
    }

    public boolean isIniciaTransaccion() {
        return iniciaTransaccion;
    }

    public boolean isBloqueoCompartido() {
        return bloqueoCompartido;
    }

    public boolean isBloqueoExclusivo() {
        return bloqueoExclusivo;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }

}