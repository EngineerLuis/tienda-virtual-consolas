package model;

public class Accesorios extends Producto {

    private String conectividad;

    public Accesorios(String id, String nombre, String categoria, int stock, float precio, String descripcion, String rutaImagen, String conectividad) {
        super(id, nombre, categoria, stock, precio, descripcion, rutaImagen);
        this.conectividad = conectividad;
    }
    
    public String getConectividad() {
        return conectividad;
    }

    public void setConectividad(String conectividad) {
        this.conectividad = conectividad;
    }
}
