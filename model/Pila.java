package model;

public class Pila<T> {
    private Nodo<T> tope;
    private int tamano;

    public Pila() {
        this.tope = null;
        this.tamano = 0;
    }

    public boolean estaVacia() {
        return tope == null;
    }

    public int getTamano() {
        return tamano;
    }

    public Nodo<T> getTope() {
        return tope;
    }

    public void agregarPila(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        if (estaVacia()) {
            tope = nuevoNodo;
        } else {
            nuevoNodo.setSiguiente(tope);
            tope.setAnterior(nuevoNodo);
            tope = nuevoNodo;
        }
        tamano++;
    }

    public void eliminarPila(T dato) {
        if (estaVacia()) {
            return;
        }

        Nodo<T> actual = tope;
        while (actual != null) {
            if (actual.getDato().equals(dato)) {
                if (actual == tope) {
                    tope = actual.getSiguiente();
                    if (tope != null) {
                        tope.setAnterior(null);
                    }
                } else {
                    Nodo<T> anterior = actual.getAnterior();
                    Nodo<T> siguiente = actual.getSiguiente();
                    if (anterior != null) {
                        anterior.setSiguiente(siguiente);
                    }
                    if (siguiente != null) {
                        siguiente.setAnterior(anterior);
                    }
                }
                tamano--;
                return;
            }
            actual = actual.getSiguiente();
        }
    }

    public T limpiarPila() {
        if (estaVacia()) {
            return null;
        }
        T dato = tope.getDato();
        tope = tope.getSiguiente();
        if (tope != null) {
            tope.setAnterior(null);
        }
        tamano--;
        return dato;
    }
}
