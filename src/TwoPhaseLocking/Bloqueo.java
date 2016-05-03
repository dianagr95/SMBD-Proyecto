package TwoPhaseLocking;


/**
 * Simula el comportamiento de un bloqueo, el cual puede ser exclusivo y/o compartido.
 * @author esmeralda
 */
public class Bloqueo {
    
    /**
     * Transaccion que mantiene un bloqueo compartido.
     */
    private int bloqueoCompartido;
    
    /**
     * Transaccion que mantiene un bloque exclusivo.
     */
    private int bloqueoExclusivo;

    public Bloqueo() {
        bloqueoCompartido = 0;
        bloqueoExclusivo = 0;
    }

    public int getBloqueoCompartido() {
        return bloqueoCompartido;
    }

    public int getBloqueoExclusivo() {
        return bloqueoExclusivo;
    }

    public void setBloqueoCompartido(int bloqueoCompartido) {
        this.bloqueoCompartido = bloqueoCompartido;
    }

    public void setBloqueoExclusivo(int bloqueoExclusivo) {
        this.bloqueoExclusivo = bloqueoExclusivo;
    }
    
}