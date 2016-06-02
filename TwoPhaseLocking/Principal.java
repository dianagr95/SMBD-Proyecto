package TwoPhaseLocking;


/**
 * Clase que simula la interfaz de esta aplicacion.
 * @author esmeralda
 */
public class Principal {

    /**
     * Protocolo que se encarga de la ejecucion de @operaciones.
     */
    private static Protocolo protocolo;
    
    public static void main (String[] args) {
        String direccion = "./2pl.txt";
        Tokenizer tokenizer;
        Operacion[] operaciones;
        String[] variables;
        Transaccion[] transacciones;
        tokenizer = new Tokenizer(direccion, TwoPhaseLocking.FUNCIONES, TwoPhaseLocking.VAR);
        operaciones = tokenizer.getOperaciones();
        variables = tokenizer.getVariables();
        transacciones = tokenizer.getTransacciones();
        
        protocolo = new TwoPhaseLocking(transacciones, operaciones, variables);
        
    }
}