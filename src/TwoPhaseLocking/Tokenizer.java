package TwoPhaseLocking;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Clase que codifica la entrada de un .txt a un flujo de operaciones valido.
 * @author esmeralda
 */
public class Tokenizer {
    
    /**
     * Direccion del archivo de entrada. Ej "./2pl.txt"
     */
    private final String direccion;
    
    /**
     * Son todas las variables obtenidas de leer el archivo.
     */
    private String[] variables;
    
    /**
     * Son la codificacion lineal del archivo de entrada en operaciones validas.
     */
    private Operacion[] operaciones;
    
    /**
     * Son todas las transacciones disponibles tras leer el archivo. Se inicializan
     * en "inactivas"
     */
    private Transaccion[] transacciones;
    
    /**
     * Son todas las variables validas para el archivo leido.
     */
    private final Funcion[] funciones;
    
    /**
     * Son todas las letras que pueden servir como nombre de variable.
     */
    private final String VAR;
    
    public Tokenizer (String direccion, Funcion[] funciones, String VAR) {
        this.direccion = direccion;
        this.funciones = funciones;
        this.VAR = VAR;
        readArchivo();
    }

    public String[] getVariables() {
        return variables;
    }

    public Operacion[] getOperaciones() {
        return operaciones;
    }

    public Transaccion[] getTransacciones() {
        return transacciones;
    }
    
    /**
     * Revisa si la funcion dada @s esta dentro de los nombres de funcion validos.
     * @param s - Posible funcion.
     * @return int - Devuelve el indice en el arreglo de funciones correspondiente
     * a la la funcion evaluada. Si la funcion no existe es una funcion invalida y 
     * devuelve -1.
     */
    private int checkFuncion (String s) {
        for(int i = 0; i < funciones.length; i++){
            if(s.equals(funciones[i].getNombre())){
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Revisa que los posibles nombres de variable sean validos con respecto al 
     * criterio dado en VAR. Ademas deben de ser una sola letra.
     * @param s - Posible nombre de variable.
     * @return boolean - @true si s es adecuada como nombre de variable. @false
     * en otro caso.
     */
    private boolean checkVariable (String s) {
        if(s.length() == 1){
            return VAR.contains(s);
        }
        return false;
    }
    
    /**
     * Revisa si el posible nombre de transaccion es valido, esto es si es un numero
     * entre 1 y 99.
     * @param aux - Posible nombre transaccion.
     * @return boolean - @true si aux es adecuada como nombre de Transaccion. @false
     * en otro caso.
     */
    private boolean checkTransaccion(String aux) {
        Integer i;
        try{
            i = Integer.valueOf(aux);
            return i>0 && i<100;
        }catch(Exception e){
            return false;
        }
    }
    
    /**
     * Metodo auxiliar para "extraer" el posible nombre de variable de una cadena.
     * @param s - Cadena a la cual se le extraera ese posible nombre de variable.
     * @return String - El posible nombre de variable.
     */
    private String getVariable(String s) {
        s = s.replace("(", "");
        s = s.replace(")", "");
        s = s.replace(" ", "");
        s = s.replace(";", "");
        return s;
    }
    
    /**
     * Lee el archivo en el cual se encuentra la entrada de ejecucion.
     */
    private void readArchivo () {
        String[] lines;
        LinkedList<Transaccion> t = new LinkedList<>();
        LinkedList<String> s = new LinkedList<>();
        String aux;
        int idF,idT,idS;
        Transaccion tr;
        Operacion op;
        try {
            lines = readLines();
            operaciones = new Operacion[lines.length];
            for(int i = 0; i < lines.length; i++){
                aux = lines[i].substring(0, 1);
                if((idF = checkFuncion(aux)) != -1){
                    aux = lines[i].substring(1, 2);
                    if(checkTransaccion(aux)){
                        tr = new Transaccion(aux);
                        if((idT = t.indexOf(tr)) == -1){
                            t.addLast(tr);
                            idT = t.size()-1;
                        }
                        aux = getVariable(lines[i].substring(2));
                        if(aux != null){
                            if(aux.equals("")){
                                idS = -1;
                            }else if(checkVariable(aux)){
                                if((idS = s.indexOf(aux)) == -1){
                                    s.addLast(aux);
                                    idS = s.size()-1;
                                }
                            }else{
                                throw new Error("El archivo no es valido.");
                            }
                            op = new Operacion(idF, idT, idS);
                            operaciones[i] = op;
                        }
                    }else{
                        throw new Error("El archivo no es valido.");
                    }
                }else{
                    throw new Error("El archivo no es valido.");
                }
            }
            variables = s.toArray(new String[s.size()]);
            transacciones =  t.toArray(new Transaccion[t.size()]);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    /**
     * Obtiene en un arreglo las lineas del archivo @direccion.
     * @return String[] - La codificacion a arreglo obtenido por leer el archivo.
     * @throws IOException - En caso de que exista algun error por intentar leer
     * el archivo.
     */
    private String[] readLines() throws IOException {
        FileReader fileReader = new FileReader(direccion);
        LinkedList<String> lines = new LinkedList<>();
        String line;
        try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return lines.toArray(new String[lines.size()]);
    }

}