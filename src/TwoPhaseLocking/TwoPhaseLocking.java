package TwoPhaseLocking;


import java.util.LinkedList;

/**
 * Simula la aplicacion del protocolo TwoPhaseLocking Estricto para el manejo de
 * transacciones concurrentes.
 * @author esmeralda
 */
public class TwoPhaseLocking extends Protocolo {
    
    /**
     * Definicion de los posibles estados de una transaccion.
     */
    public static final String[] estados = {"Inactiva","Activa","Bloqueada","Abortada","Comprometida"};
    
    /**
     * Arreglo que contiene los bloqueos sobre las variables, la variable se define con el indice
     * posicional en el arreglo.
     */
    private Bloqueo[] bloqueos;
    
    /**
     * Lista que contiene las transacciones que esperan a que algun recurso se desbloquee.
     */
    private LinkedList<Espera> listaEspera;

    /**
     * Arreglo con todas las transacciones.
     */
    private final Transaccion[] transacciones;
    
    /**
     * Codificacion de la entrada en operaciones.
     */
    private final Operacion[] operaciones;
    
    /**
     * Arreglo con todas las variables posibles.
     */
    private final String[] variables;
    
    /**
     * Bandera que establece si la actual ejecucion fue o no Serializable. @true
     * si fue serializable, @false si no fue serializable.
     */
    private boolean serializable;

    public TwoPhaseLocking( Transaccion[] transacciones, Operacion[] operaciones, String[] variables) {
        this.serializable = true;
        this.transacciones = transacciones;
        this.operaciones = operaciones;
        this.variables = variables;
        this.bloqueos = new Bloqueo[variables.length];
        listaEspera = new LinkedList<>();
        bitacora = null;
    }
    
    @Override
    public void ejecutar() {
        int i = 0;
        while(!end()){
            try {
                
            } catch (IndexOutOfBoundsException e) {
                i = 0;
            }
        }
    }

    @Override
    public LinkedList<String> obtenerBitacora() {
        if(bitacora == null){
            ejecutar();
        }
        return bitacora;
    }

    /**
     * Revisa si faltan transacciones por terminar (Commit).
     * @return boolean - @true si todas las transacciones realizaron Commit, @false
     * en otro caso.
     */
    private boolean end() {
        for(int i = 0; i < transacciones.length; i++){
            if(transacciones[i].getEstado() != 4){
                return false;
            }
        }
        return true;
    }
}