// Modelo de productos de tipo juego.
// Este archivo forma parte de la aplicación de tienda virtual.

package model;

public class Juego extends Producto {

    private String resolucion;

    public Juego(String id, String nombre, String categoria, int stock, float precio, String descripcion,
            String rutaImagen, String resolucion) {
        super(id, nombre, categoria, stock, precio, descripcion, rutaImagen);
        this.resolucion = resolucion;
    }

    public String getResolucion() {
        return resolucion;
    }

    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

}
