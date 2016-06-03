package TwoPhaseLocking;


/**
 * Simula el comportamiento de las transacciones en espera.
 * @author esmeralda
 */
public class Espera {
    
    /**
     * Indice de la transaccion que esta esperando.
     */
    private final int transaccionEspera;
    
    /**
     * Indice de la variable dueña que se quiere obtener.
     */
    private final int variable;
    
    /**
     * Indice de la transaccion que utiliza dicha variable.
     */
    private final int transaccionBloqueo;

    /**
     * Indica si el bloqueo es exclusivo o compartido. @true si es esclusivo, @false en otro caso.
     */
    private final boolean exclusivo;

    public Espera(int transaccionEspera, int variable, int transaccionBloqueo, boolean exclusivo) {
        this.transaccionEspera = transaccionEspera;
        this.variable = variable;
        this.transaccionBloqueo = transaccionBloqueo;
        this.exclusivo = exclusivo;
    }

    public int getTransaccionEspera() {
        return transaccionEspera;
    }

    public int getVariable() {
        return variable;
    }

    public int getTransaccionBloqueo() {
        return transaccionBloqueo;
    }

    public boolean isExclusivo() {
        return exclusivo;
    }
    

}