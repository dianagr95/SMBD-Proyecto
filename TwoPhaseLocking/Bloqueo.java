package TwoPhaseLocking;


/**
 * Simula el comportamiento de un bloqueo, el cual puede ser exclusivo y/o compartido.
 * @author esmeralda
 */
public class Bloqueo {
    
    /**
     * Transaccion que mantiene un bloqueo compartido.
     */
    private int b;

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
        b = 0;
        bx = 0;
        mtR = 0;
        mtW = 0;
        
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
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
    
    
}