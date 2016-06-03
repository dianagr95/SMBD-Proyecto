package TwoPhaseLocking;


import java.util.Iterator;
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
    
    private final LinkedList<Operacion>[] opEspera;
    
    private final Bloqueo[] bloqueos;
    
    private final LinkedList<Espera> listaEspera;

    public TwoPhaseLocking(Transaccion[] transacciones, Operacion[] operaciones, String[] variables) {
        this.transacciones = transacciones;
        this.operaciones = operaciones;
        this.variables = variables;
        serializable = true;
        tiempo = 1;
        opEspera = new LinkedList[transacciones.length];
        for(int i = 0; i < opEspera.length; i++){
            opEspera[i] = new LinkedList<>();
        }
        bloqueos = new Bloqueo[variables.length];
        for(int i = 0; i < bloqueos.length; i++){
            bloqueos[i] = new Bloqueo();
        }
        listaEspera = new LinkedList<>();
        
    }

    @Override
    public void ejecutar() {
        Operacion opActual;
        int T;
        Transaccion transaccion;
        int estado;
        int i = 0;
        bitacora = new LinkedList<>();
        try{
            while(i < operaciones.length){
                opActual = operaciones[i++];
                T = opActual.getTransaccion();
                transaccion = transacciones[T];
                estado = transaccion.getEstado();
                switch(estado){
                    case 0:
                    case 1:
                        ejecutarOperacion(opActual,i);
                        break;
                    case 2:
                        opEspera[T].add(opActual);
                        break;
                    case 5:
                        throw new Error("Error al ejecutar operacion de una transaccion ya comprometida.");
                }
                printBitacora();
            }
        }catch(Exception e){
            bitacora = null;
            tiempo = 1;
            System.out.println(e.getLocalizedMessage());
        }
    }

    private void ejecutarOperacion(Operacion op,int posBitacora) {
        int funcion = op.getFuncion();
        Transaccion transaccion = transacciones[op.getTransaccion()];
        int variable = op.getVariable();
        int estado = transaccion.getEstado();
        int t = transaccion.getTiempo();
        int T = op.getTransaccion();
        Espera espera;
        LinkedList<Integer> b;
        int bx,mtW,mtR;
        
        switch(funcion){
            case 0:     //BEGIN
                if(estado != 0){
                    throw new Error("Se intento iniciar una transaccion previamente iniciada.");
                }else{
                    estado = 1;
                    transaccion.setEstado(estado);
                    t = this.tiempo++;
                    transaccion.setTiempo(t);
                    bitacora.addLast(op);
                }
                break;
            case 1:     //READ
                b = bloqueos[variable].getB();
                bx = bloqueos[variable].getBx();
                mtR = bloqueos[variable].getMtR();
                mtW = bloqueos[variable].getMtW();
                if(bx != -1 && bx != T){  //BLOQUEO EXCLUSIVO
                    espera = new Espera(T, variable, bx,true);
                    listaEspera.addLast(espera);
                    if(t < mtW){       //WAIT
                        estado = 2;
                        transaccion.setEstado(estado);
                        opEspera[T].addLast(op);
                    }else{                  //DIE   
                        estado = 3;
                        transaccion.setEstado(estado);
                        rollback(T,posBitacora);
                    }
                }else{      //NO IMPORTA SI HAY BLOQUEO COMPARTIDO 
                    if(t < mtW){       //TimeStamp invalida. --El registro se modifico.
                        estado = 4;
                        transaccion.setEstado(estado);
                        rollback(T,posBitacora);
                        ejecutarTransaccion(T, posBitacora);
                    }else{                  //SI SE PUEDE leer
                        if(bx != T && !b.contains(T)){
                            bloqueos[variable].setB(T);
                        }
                        if(mtR < t){
                            bloqueos[variable].setMtR(t);
                        }
                        bitacora.addLast(op);
                    }
                }
                break;
            case 2:     //WRITE
                b = bloqueos[variable].getB();
                bx = bloqueos[variable].getBx();
                mtR = bloqueos[variable].getMtR();
                mtW = bloqueos[variable].getMtW();
                if(bx != -1 && bx != T){  //BLOQUEO EXCLUSIVO
                    if(t < mtW){       //WAIT-- se omite por regla de THOMAS?
                        
                    }else{                  //DIE
                        espera = new Espera(T, variable, bx,true);
                        listaEspera.addLast(espera);
                        estado = 3;
                        transaccion.setEstado(estado);
                        rollback(T,posBitacora);
                    }
                }else {      //BLOQUEO COMPARTIDO 
                    b.remove(new Integer(T));       //upgrade de bloqueo
                    if(b.isEmpty()){        //Sin bloqueo compartido
                        if(t < mtR){       //TimeStamp invalida. --El registro se leyo antes de modificarse.
                            estado = 4;
                            transaccion.setEstado(estado);
                            rollback(T,posBitacora);
                            ejecutarTransaccion(T, posBitacora);
                        }else if(t < mtW){                  //Regla de thomas
                            
                        }else{
                            bloqueos[variable].setBx(T);
                            bloqueos[variable].setMtW(t);
                            bitacora.addLast(op);
                        }
                    }else{
                        Iterator<Integer> it;
                        it = b.descendingIterator();
                        if(t < mtR){       //WAIT
                            if(t < mtW){   //Regla Thomas

                            }else{
                                while(it.hasNext()){        //Crea espera para cada una de los bloqueos compartidos.
                                    espera = new Espera(T, variable, it.next(),false);
                                    listaEspera.addLast(espera);
                                }
                                estado = 2;
                                transaccion.setEstado(estado);
                                opEspera[T].addLast(op);
                            }
                        }else{                  //DIE
                            while(it.hasNext()){        //Crea espera para cada una de los bloqueos compartidos.
                                espera = new Espera(T, variable, it.next(),false);
                                listaEspera.addLast(espera);
                            }
                            estado = 3;
                            transaccion.setEstado(estado);
                            rollback(T,posBitacora);
                        }
                    }
                }
                break;
            case 3:     //END
                if(estado != 1){
                    throw new Error("Se intento finalizar una transaccion no activa.");
                }else{
                    estado = 5;
                    transaccion.setEstado(estado);
                    bitacora.addLast(op);
                    liberarRecursos(T, posBitacora);
                }
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
        for (Transaccion transaccion : transacciones) {
            if (transaccion.getEstado() != 5) {
                return false;
            }
        }
        return true;
    }

    public boolean isSerializable() {
        return serializable;
    }

    private void rollback(int transaccion, int posBitacora) {
        Iterator<Operacion> it = bitacora.listIterator();
        Operacion op;
        while(it.hasNext()){
            op = it.next();
            if(op.getTransaccion() == transaccion){
                it.remove();
            }
        }
        liberarRecursos(transaccion,posBitacora);
    }

    private void liberarRecursos(int T, int posBitacora) {
        int estado;
        Bloqueo bloqueo;
        Iterator<Espera> it;
        Espera espera;
        Operacion op;
        Transaccion transaccion;
        LinkedList<Transaccion> transaccionesEspera;
        boolean flag;
        Iterator<Transaccion> eIT;
        transaccionesEspera = new LinkedList<>();
        for (Transaccion tr : transacciones) {
            estado = tr.getEstado();
            if (estado == 2 || estado == 3) {
                transaccionesEspera.add(tr);
            }
        }
        it = listaEspera.descendingIterator();
        
        for(int i = 0; i < bloqueos.length; i++){
                bloqueo = bloqueos[i];
                if(bloqueo.getBx() == T){
                    bloqueo.setBx(-1);
                }
                bloqueo.deleteFromB(T);
        }
        while(it.hasNext()){
            espera = it.next();
            if(espera.getTransaccionBloqueo() == T){  //libera bloqueo.
                
                it.remove();
            }
        }
        eIT = transaccionesEspera.descendingIterator();
        while(eIT.hasNext()){
            transaccion = eIT.next();
            if(transaccion.getEstado() == 3 || transaccion.getEstado() == 4){
                T = getTransaccion(transaccion);
                it = listaEspera.descendingIterator();
                flag = true;
                while(it.hasNext() && flag){
                    espera = it.next();
                    if(espera.getTransaccionEspera() == T){
                        flag = false;       // Aun esta bloqueada la transaccion
                    }
                }
                if(flag){   //Ya no hay bloqueos sobre la transaccion.
                    estado = transaccion.getEstado();
                    if(estado == 2){    //Esta esperando
                        transaccion.setEstado(1);
                        while(!opEspera[T].isEmpty()){
                            op = opEspera[T].pollFirst();
                            ejecutarOperacion(op, posBitacora);
                        }
                    }else{              //Se reinicia
                        transaccion.setEstado(0);
                        ejecutarTransaccion(T, posBitacora);
                    }
                }
            }
        }
    }
    
    private void ejecutarTransaccion(int T, int posBitacora){
        Transaccion transaccion = transacciones[T];
        transaccion.setEstado(0);
        Operacion op;
        int estado;
        for(int i = 0; i < posBitacora; i++){
            op = operaciones[i];
            if(op.getTransaccion() == T){
                estado = transaccion.getEstado();
                switch(estado){
                    case 0:
                    case 1:
                        ejecutarOperacion(op,i);
                        break;
                    case 2:
                        opEspera[T].add(op);
                        break;
                    case 3:
                    case 4:
                        return;
                    case 5:
                        throw new Error("Error al ejecutar operacion de una transaccion ya comprometida.");
                }
                printBitacora();
            }
            
        }
    }

    private void printBitacora() {
        Iterator<Operacion> it;
        it = bitacora.iterator();
        while(it.hasNext()){
            printOp(it.next());
        }
        System.out.println("XXXXXXXXXXX");
    }
    
    private void printOp(Operacion op){
        String v,f,t,s;
        f = TwoPhaseLocking.FUNCIONES[op.getFuncion()];
        t = transacciones[op.getTransaccion()].toString();
        if(op.getVariable() != -1){
            v = variables[op.getVariable()];
            s = f + t + "(" + v + ")";
        }else{
            s = f + t;
        }
        System.out.println(s);
    }

    private int getTransaccion(Transaccion transaccion) {
        for(int i = 0; i < transacciones.length;i++){
            if(transaccion.equals(transacciones[i])){
                return i;
            }
        }
        throw new Error("Transaccion invalida.");
    }
}