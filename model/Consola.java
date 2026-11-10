package model;

public  class Consola extends Producto {

    private String procesador;
    private String almacenamiento;
    private String resolucion;



    public Consola(String id, String nombre, String marca, int stock, float precio, String descripcion, String rutaImagen, String procesador, String almacenamiento, String resolucion) {
        super(id, nombre, marca, stock, precio, descripcion, rutaImagen);
        this.procesador = procesador;
        this.almacenamiento = almacenamiento;
        this.resolucion = resolucion;
    }

    public String getProcesador() {
        return procesador;
    }

    public String getAlmacenamiento() {
        return almacenamiento;
    }

    public String getResolucion() {
        return resolucion;
    }

    public void setProcesador(String procesador){
        this.procesador = procesador;
    }

    public void setAlmacenamiento(String almacenamiento){
        this.almacenamiento = almacenamiento;
    }

    public void setResolucion(String resolucion){
        this.resolucion = resolucion;
    }
}
