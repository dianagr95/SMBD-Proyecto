package TwoPhaseLocking;


import java.util.LinkedList;

/**
 * Simula la aplicacion del protocolo TwoPhaseLocking Estricto para el manejo de
 * transacciones concurrentes.
 * @author esmeralda
 */
public class TwoPhaseLocking extends Protocolo {
                                            // 0        1       2               3           4           5
    /**
     * Define los posibles estados de una transaccion.
     */
    public static final String[] ESTADOS = {"Inactiva","Activa","Esperando","Bloqueada","Abortada","Comprometida"};
    
    /**
     * Define los caracteres posibles para las variables.
     */
    public static final String VAR = "QWERTYUIOPASDFGHJKLÑZXCVBNM";
    
    /**
     * Define la lista de funciones disponibles.
     */
    public static final String[] FUNCIONES = new String[]{"b","r","w","e"};
    
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
    
    /**
     * Tiempo actual en el que se encuentra el protocolo. Inicial = 1.
     */
    private int tiempo;
    
    private LinkedList<Operacion>[] listaEspera;

    public TwoPhaseLocking(Transaccion[] transacciones, Operacion[] operaciones, String[] variables) {
        this.transacciones = transacciones;
        this.operaciones = operaciones;
        this.variables = variables;
        serializable = true;
        tiempo = 1;
        listaEspera = new LinkedList[transacciones.length];
        for(int i = 0; i < listaEspera.length; i++){
            listaEspera[i] = new LinkedList<>();
        }
    }

    @Override
    public void ejecutar() {
        Operacion opActual;
        int T;
        Transaccion transaccion;
        int estado;
        int i = 0;
        try{
            while(i < operaciones.length){
                opActual = operaciones[i++];
                T = opActual.getTransaccion();
                transaccion = transacciones[T];
                estado = transaccion.getEstado();
                switch(estado){
                    case 0:
                    case 1:
                        ejecutarOperacion(opActual);
                        break;
                    case 2:
                        listaEspera[T].add(opActual);
                        break;
                    case 5:
                        throw new Error("Error al ejecutar operacion de una transaccion ya comprometida.");
                }
            }
        }catch(Exception e){
            bitacora = null;
            tiempo = 1;
            System.out.println(e.getLocalizedMessage());
        }
    }

    private void ejecutarOperacion(Operacion op) {
        int funcion = op.getFuncion();
        Transaccion transaccion = transacciones[op.getTransaccion()];
        int variable = op.getVariable();
        LinkedList<Integer> b;
        int bx,mtW,mtR;
        switch(funcion){
            case 0:     //BEGIN
                break;
            case 1:     //READ
                break;
            case 2:     //WRITE
                break;
            case 3:     //END
                break;
        }
    }
    
    @Override
    public LinkedList<Operacion> obtenerBitacora() {
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
            if(transacciones[i].getEstado() != 5){
                return false;
            }
        }
        return true;
    }

    public boolean isSerializable() {
        return serializable;
    }
    
}