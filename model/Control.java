package model;

public class Control extends Producto {

    private String conectividad;

    public Control(String id, String nombre, String marca, int stock, float precio, String descripcion, String rutaImagen, String conectividad) {
        super(id, nombre, marca, stock, precio, descripcion, rutaImagen);
        this.conectividad = conectividad;
    }

    public String getConectividad() {
        return conectividad;
    }

    public void setConectividad(String conectividad){
        this.conectividad = conectividad;
    }

}