package model;

public class ListaDobleEnlazada<T> {
    private Nodo<T> cabeza;
    private Nodo<T> cola;
    private int tamano;

    public ListaDobleEnlazada() {
        this.cabeza = null;
        this.cola = null;
        this.tamano = 0;
    }

    public boolean estaVacia() {
        return cabeza == null;
    }

    public int getTamano() {
        return tamano;
    }

    public Nodo<T> getCabeza() {
        return cabeza;
    }

    public Nodo<T> getCola() {
        return cola;
    }

    public void agregarLista(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        if (estaVacia()) {
            cabeza = nuevoNodo;
            cola = nuevoNodo;
        } else {
            cola.setSiguiente(nuevoNodo);
            nuevoNodo.setAnterior(cola);
            cola = nuevoNodo;
        }
        tamano++;
    }

    public void eliminarLista(T dato) {
        if (estaVacia()) {
            return;
        }

        Nodo<T> actual = cabeza;
        while (actual != null) {
            if (actual.getDato().equals(dato)) {
                if (actual == cabeza) {
                    cabeza = actual.getSiguiente();
                    if (cabeza != null) {
                        cabeza.setAnterior(null);
                    } else {
                        cola = null; // La lista está vacía ahora
                    }
                } else if (actual == cola) {
                    cola = actual.getAnterior();
                    if (cola != null) {
                        cola.setSiguiente(null);
                    } else {
                        cabeza = null; // La lista está vacía ahora
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

    public T buscarLista(T dato) {
        Nodo<T> actual = cabeza;
        while (actual != null) {
            if (actual.getDato().equals(dato)) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
        }
        return null; // No se encontró el dato
    }

    public void limpiarLista() {
        cabeza = null;
        cola = null;
        tamano = 0;
    }
}
