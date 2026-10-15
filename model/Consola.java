package model;

public  class Consola extends Producto {

    private String plataforma;

    public Consola(String id, String nombre, String marca, float precio, int stock, String plataforma) {
        super(id, nombre, marca, precio, stock);
        this.plataforma = plataforma;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }
}
