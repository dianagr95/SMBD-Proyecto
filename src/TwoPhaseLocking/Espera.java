package TwoPhaseLocking;


/**
 * Simula el comportamiento de las transacciones en espera.
 * @author esmeralda
 */
public class Espera {
    
    /**
     * El valor asigando a las instancias de espera que no esperan nada n_n
     */
    public final static int NOBLOQUEO = -1;
    
    /**
     * Indice de la transaccion que esta esperando.
     */
    private int transaccionEspera;
    
    /**
     * Indice de la variable dueña que se quiere obtener.
     */
    private int variable;
    
    /**
     * Indice de la transaccion que utiliza dicha variable.
     */
    private int transaccionBloqueo;

    public Espera() {
        this.transaccionEspera = NOBLOQUEO;
        this.variable = NOBLOQUEO;
        this.transaccionBloqueo = NOBLOQUEO;
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

    public void setTransaccionEspera(int transaccionEspera) {
        this.transaccionEspera = transaccionEspera;
    }

    public void setVariable(int variable) {
        this.variable = variable;
    }

    public void setTransaccionBloqueo(int transaccionBloqueo) {
        this.transaccionBloqueo = transaccionBloqueo;
    }
}
