package TwoPhaseLocking;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


/**
 * Clase que simula la interfaz de esta aplicacion.
 * @author esmeralda
 */
public class Principal {

    /**
     * Protocolo que se encarga de la ejecucion de @operaciones.
     */
    private static Protocolo protocolo;
    
    public static String[] variables;
    
    public static Transaccion[] transacciones;
    
    public static void main (String[] args) {
        String direccion = "./2pl.txt";
        Tokenizer tokenizer;
        Operacion[] operaciones;
        tokenizer = new Tokenizer(direccion, TwoPhaseLocking.FUNCIONES, TwoPhaseLocking.VAR);
        operaciones = tokenizer.getOperaciones();
        variables = tokenizer.getVariables();
        transacciones = tokenizer.getTransacciones();
        
        protocolo = new TwoPhaseLocking(transacciones, operaciones, variables);
        
        
        LinkedList<Operacion> list = protocolo.obtenerBitacora();
        //List<Operacion> list = Arrays.asList(operaciones);
        
        if(list != null){
            Iterator<Operacion> it = list.iterator();
            while(it.hasNext()){
                printOp(it.next());
            }
        }else{
            System.out.println("null");
        }
    }
    
    private static void printOp(Operacion op){
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
}