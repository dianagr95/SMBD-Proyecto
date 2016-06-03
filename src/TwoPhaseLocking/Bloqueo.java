package TwoPhaseLocking;

import java.util.LinkedList;


/**
 * Simula el comportamiento de un bloqueo, el cual puede ser exclusivo y/o compartido.
 * @author esmeralda
 */
public class Bloqueo {
    
    /**
     * Lista de transacciones que mantienen un bloqueo compartido.
     */
    private LinkedList<Integer> b;

    /**
     * Marca de tiempo de lectura.
     */
    private int mtR;
    
    /**
     * Transaccion que mantiene un bloque exclusivo.
     */
    private int bx;

    /**
     * Marca de tiempo de escritura.
     */
    private int mtW;
    
    public Bloqueo() {
        b = new LinkedList<>();
        bx = -1;
        mtR = 0;
        mtW = 0;
        
    }

    public LinkedList getB() {
        return b;
    }

    public void setB(int b) {
        this.b.add(b);
    }

    public int getMtR() {
        return mtR;
    }

    public void setMtR(int mtR) {
        this.mtR = mtR;
    }

    public int getBx() {
        return bx;
    }

    public void setBx(int bx) {
        this.bx = bx;
    }

    public int getMtW() {
        return mtW;
    }

    public void setMtW(int mtW) {
        this.mtW = mtW;
    }
    
    public boolean contain(int b){
        return this.b.contains(b);
    }
    
    public void deleteFromB(int b){
        this.b.remove(new Integer(b));
    }
    
}