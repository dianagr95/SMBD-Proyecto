package TwoPhaseLocking;


import java.util.LinkedList;

/**
 * Clase abstracta que simula el uso de un protocolo para el manejo
 * de la concurrencia en la ejecucion de transacciones.
 * @author esmeralda
 */
public abstract class Protocolo {

    /**
     * Bitacora, registro de los pasos seguidos.
     */
    protected LinkedList<String> bitacora;

    /**
     * Metodo que ejecuta el protocolo.
     */
    public abstract void ejecutar ();

    /**
     * Metodo que obiene la bitacora.
     * @return 
     */
    public abstract LinkedList<String >obtenerBitacora ();
    
}