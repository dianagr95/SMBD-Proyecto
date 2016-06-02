package TwoPhaseLocking;


/**
 * Simula el comportamiento de una operacion, la cual contiene una funcion, una
 * transaccion asociada, y una variable.
 * @author esmeralda
 */
public class Operacion {

    /**
     * Funcion que aplica esta operacion.
     */
    private final int funcion;
    
    /**
     * Transaccion dueña de esta operacion.
     */
    private final int transaccion;
    
    /**
     * Variable a la cual se le puede aplicar. -1 significa no que tiene variable.
     */
    private final int variable;

    public Operacion(int funcion, int transaccion, int variable) {
        this.funcion = funcion;
        this.transaccion = transaccion;
        this.variable = variable;
    }

    public int getFuncion() {
        return funcion;
    }

    public int getTransaccion() {
        return transaccion;
    }

    public int getVariable() {
        return variable;
    }
    
}