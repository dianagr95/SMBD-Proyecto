package TwoPhaseLocking;

import java.util.Objects;


/**
 * Simula el comportamiento de una transaccion, esta tiene nombre, estado y tiempo.
 * @author esmeralda
 */
public class Transaccion {

    /**
     * Tiempo en el cual fue activada. 0-SIN TIEMPO.
     */
    private int tiempo;
    
    /**
     * Estado en el cual se encuentra actualmente. 
     */
    private int estado;
    
    /**
     * Nombre de la transaccion. Es un numero entre 1 y 99
     */
    private final String nombre;

    public Transaccion(String nombre) {
        this.tiempo = 0;
        this.estado = 0;
        this.nombre = nombre;
    }

    public int getTiempo() {
        return tiempo;
    }

    public int getEstado() {
        return estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    @Override
    public boolean equals(Object obj) {
        Transaccion t;
        if(obj instanceof Transaccion){
            t = (Transaccion) obj;
            return this.nombre.equals(t.nombre);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.nombre);
        return hash;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
}