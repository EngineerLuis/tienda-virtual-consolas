// Modelo que representa una cuenta y sus pedidos asociados.
// Este archivo forma parte de la aplicación de tienda virtual.

package model;

import java.util.ArrayList;
import java.util.List;
public class Usuario {

    private String nombre;
    private String correo;
    private String contrasena;
    private List<Pedido> misPedidos = new ArrayList<>(); // Lista de pedidos del usuario

    public Usuario(String nombre, String correo, String contrasena) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.misPedidos = new ArrayList<>();
    }

    // Getters and Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    public void agregarPedido(Pedido pedido) {
        if (this.misPedidos == null) {
            this.misPedidos = new ArrayList<>();
        }
        this.misPedidos.add(pedido);
    }

    public List<Pedido> getMisPedidos() {
        if (this.misPedidos == null) {
            this.misPedidos = new ArrayList<>();
        }
        return misPedidos;
    }

    public int getCantidadPedidos() {
        return misPedidos != null ? misPedidos.size() : 0;
    }

}


