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

    public Espera(int transaccionEspera, int variable, int transaccionBloqueo) {
        this.transaccionEspera = transaccionEspera;
        this.variable = variable;
        this.transaccionBloqueo = transaccionBloqueo;
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

}