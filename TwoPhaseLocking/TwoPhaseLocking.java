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

    public TwoPhaseLocking(Transaccion[] transacciones, Operacion[] operaciones, String[] variables) {
        this.transacciones = transacciones;
        this.operaciones = operaciones;
        this.variables = variables;
        serializable = true;
        tiempo = 1;
    }

    @Override
    public void ejecutar() {
        Operacion opActual;
        Transaccion transaccion;
        int estado;
        int i = 0;
        try{
            while(i < operaciones.length){
                opActual = operaciones[i++];
                transaccion = transacciones[opActual.getTransaccion()];
                estado = transaccion.getEstado();
                switch(estado){
                    case 0:
                    case 1:
                        ejecutarOperacion(opActual);
                        break;
                    case 2:
                        listaEspera.add(opActual);
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
        Funcion funcion = funciones[op.getFuncion()];
        Transaccion transaccion = transacciones[op.getTransaccion()];
        int variable = op.getVariable();
        LinkedList<Integer> b;
        int bx,mtW,mtR;
        if(funcion.isDefineTransaccion()){  //Inicia o termina la transaccion
            if(funcion.isIniciaTransaccion()){  //Inicia transaccion.  
                if(transaccion.getEstado() != 0){
                    throw new Exception("Se intento una transaccion que ya se habia iniciado anteriormente."); 
                }else{
                    transaccion.setEstado(1);
                    transaccion.setTiempo(tiempo++);
                    bitacora.add(op);
                }
            }else{
                if(transaccion.getEstado() != 1){
                    throw new Exception("Se intento una comprometer una transaccion que no se ha iniciado propiamente."); 
                }else{
                    transaccion.setEstado(5);
                    bitacora.add(op);
                    for(int j = 0; j < bloqueos.length; j++){
                        bloqueos[j].deleteBloqueoCompartido(op.getTransaccion());
                        if(bloqueos[j].getBloqueoExclusivo() == op.getTransaccion()){
                            bloqueos[j].setBloqueoExclusivo(-1);
                        }
                    }
                    //ejecutar wait
                }
            }
        }else{
            b = bloqueos[variable].getBloqueoCompartido();
            mtR = bloqueos[variable].getTiempoCompartido();
            bx = bloqueos[variable].getBloqueoExclusivo();
            mtW = bloqueos[variable].getTiempoExclusivo();
            if(funcion.isBloqueoExclusivo()){
                if(bx != -1 && bx != op.getTransaccion()){  //Conflicto por write
                    if(transaccion.getTiempo() > mtW){
                        //rollback 
                        serializable = false;
                    }else{
                        //wait
                    }
                }else if(b.contains(op.getTransaccion())){  //Conflicto por read
                    if(transaccion.getTiempo() > mtR){
                        //rollback 
                        serializable = false;
                    }else{
                        //wait
                    }
                }else{      //Sin conflicto
                    //se ejecuta normal. pero se revisan las marcas de tiempo mtR y mtW
                }
                
            }else if(funcion.isBloqueoCompartido()){
                if(bx != -1 && bx != op.getTransaccion()){  //Conflicto por write
                    if(transaccion.getTiempo() > mtW){
                        //rollback 
                        serializable = false;
                    }else{
                        //wait
                    }
                }else{      //Sin conflicto en este caso READ no es conflicto
                    //se ejecuta normal. pero se revisan las marcas de tiempo mtR y mtW
                }
            }
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