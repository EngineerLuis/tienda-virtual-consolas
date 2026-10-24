package model;

public class Cola<T> {
    private Nodo<T> frente;
    private Nodo<T> finalCola;
    private int tamano;

    public Cola() {
        this.frente = null;
        this.finalCola = null;
        this.tamano = 0;
    }

    public boolean estaVacia() {
        return frente == null;
    }

    public int getTamano() {
        return tamano;
    }

    public Nodo<T> getFrente() {
        return frente;
    }

    public Nodo<T> getFinalCola() {
        return finalCola;
    }

    public void agregarCola(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        if (estaVacia()) {
            frente = nuevoNodo;
            finalCola = nuevoNodo;
        } else {
            finalCola.setSiguiente(nuevoNodo);
            nuevoNodo.setAnterior(finalCola);
            finalCola = nuevoNodo;
        }
        tamano++;
    }

    public void eliminarCola(T dato) {
        if (estaVacia()) {
            return;
        }

        Nodo<T> actual = frente;
        while (actual != null) {
            if (actual.getDato().equals(dato)) {
                if (actual == frente) {
                    frente = actual.getSiguiente();
                    if (frente != null) {
                        frente.setAnterior(null);
                    } else {
                        finalCola = null; // La cola está vacía ahora
                    }
                } else if (actual == finalCola) {
                    finalCola = actual.getAnterior();
                    if (finalCola != null) {
                        finalCola.setSiguiente(null);
                    } else {
                        frente = null; // La cola está vacía ahora
                    }
                } else {
                    actual.getAnterior().setSiguiente(actual.getSiguiente());
                    actual.getSiguiente().setAnterior(actual.getAnterior());
                }
                tamano--;
                return;
            }
            actual = actual.getSiguiente();
        }
    }

    public T buscarCola(T dato) {
        Nodo<T> actual = frente;
        while (actual != null) {
            if (actual.getDato().equals(dato)) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
        }
        return null; // No se encontró el dato
    }

    public void limpiarCola() {
        frente = null;
        finalCola = null;
        tamano = 0;
    }
}
