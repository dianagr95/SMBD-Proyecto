package TwoPhaseLocking;


/**
 * Clase que simula la interfaz de esta aplicacion.
 * @author esmeralda
 */
public class Principal {

    /**
     * Atributo con el cual se codifica la entrada del programa.
     */
    private static Tokenizer tokenizer;
    
    /**
     * Funciones definidas las cuales aplican en esta implementacion.
     */
    private static Funcion[] funciones;
    
    /**
     * Codificacion de la entrada en un arreglo de operaciones.
     */
    private static Operacion[] operaciones;
    
    /**
     * Arreglo que contiene el nombre de las variables que se utilizaran en esta
     * implementacion.
     */
    private static String[] variables;
    
    /**
     * Arrelgo que contiene las transacciones disponibles para esta implementacion.
     */
    private static Transaccion[] transacciones;
    
    /**
     * Protocolo que se encarga de la ejecucion de @operaciones.
     */
    private static Protocolo protocolo;
    
    public static void main (String[] args) {
        init();
        for(int i = 0; i < operaciones.length; i++){
            System.out.println(toStringOP(operaciones[i]));
        }
    }
    
    /**
     * Inicializa la aplicacion: define el archivo de entrada, los nombres validos
     * de variables, las funciones disponibles para la aplicacion, la creacion del
     * atributo de codificacion y lectura de dicho archivo, la definicion de las
     * variables, operaciones y transacciones disponibles para la aplicacion y la
     * definicion del protocolo de ejecucion.
     */
    private static void init () {
        String direccion = "./2pl.txt";
        String VAR = "QWERTYUIOPASDFGHJKLÑZXCVBNM";
        funciones = new Funcion[4];
        funciones[0] = new Funcion(true, true, false, false, "b");
        funciones[1] = new Funcion(false, false, true, false, "r");
        funciones[2] = new Funcion(false, false, false, true, "w");
        funciones[3] = new Funcion(true, false, false, false, "e");
        
        tokenizer = new Tokenizer(direccion, funciones, VAR);
        operaciones = tokenizer.getOperaciones();
        variables = tokenizer.getVariables();
        transacciones = tokenizer.getTransacciones();
        protocolo = new TwoPhaseLocking(transacciones, operaciones, variables);
    }

    /**
     * Metodo auxiliar que imprime el valor de una operacion.
     * @param op - Operacion a imprimir.
     * @return String - Valor de la impresion de @op.
     */
    private static String toStringOP(Operacion op) {
        String s;
        s = "" + funciones[op.getFuncion()];
        try {
            s +=  transacciones[op.getTransaccion()] + " (" 
                + variables[op.getVariable()] + ")";
        } catch (Exception e) {
        }
        return s;
    }
}