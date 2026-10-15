package model;

public class Juego extends Producto {

    private String genero;

    public Juego(String id, String nombre, String marca, float precio, int stock, String genero) {
        super(id, nombre, marca, precio, stock);
        this.genero = genero;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
    
}
