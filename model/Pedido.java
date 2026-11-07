package model;

import java.util.List;

public class Pedido {
    private String idPedido;
    private String fecha;
    private String nombreCliente;
    private List<Producto> productos;
    private double total;
    private String metodoPago;
    private String direccionEnvio;

    public Pedido(String idPedido, String fecha, String nombreCliente, List<Producto> productos, double total, String metodoPago, String direccionEnvio) {
        this.idPedido = idPedido;
        this.fecha = fecha;
        this.nombreCliente = nombreCliente;
        this.productos = productos;
        this.total = total;
        this.metodoPago = metodoPago;
        this.direccionEnvio = direccionEnvio;
    }

    //Getters y Setters
    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public String getFecha(){
        return fecha;
    }

    public void setFecha(String fecha){
        this.fecha = fecha;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getDireccionEnvio() {
        return direccionEnvio;
    }

    public void setDireccionEnvio(String direccionEnvio) {
        this.direccionEnvio = direccionEnvio;
    }

    public int getCantidadItems() {
    return productos != null ? productos.size() : 0;
}

}