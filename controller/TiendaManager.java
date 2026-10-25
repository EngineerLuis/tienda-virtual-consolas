package controller;

import model.*;
import java.util.ArrayList;
import java.util.List;

public class TiendaManager {

    private final ControladorPersistencia persistencia;

    // Catálogos principales basados en Lista Doblemente Enlazada propia
    private ListaDobleEnlazada<Consola> listaConsolas;
    private ListaDobleEnlazada<Juego> listaJuegos;
    private ListaDobleEnlazada<Control> listaControles;
    private ListaDobleEnlazada<Accesorios> listaAccesorios;
    private ListaDobleEnlazada<Usuario> listaUsuarios;

    // Estructuras de la experiencia de usuario (Cliente)
    private Cola<Producto> carritoCompras; // Cola (FIFO) para el carrito
    private ListaDobleEnlazada<Producto> listaDeseos; // Lista de deseos (Wishlist)
    private ListaDobleEnlazada<Pedido> historialPedidos; // Pedidos globales de la tienda
    private ListaDobleEnlazada<Producto> ultimoPedidoConfirmado; // Detalle de la última compra

    // Estructuras del Administrador
    private Pila<String> historialAccionesAdmin; // Pila (LIFO) para el historial y botón "Deshacer" (Undo)

    // Sesión actual
    private Usuario usuarioActual;

    public TiendaManager() {
        this.persistencia = new ControladorPersistencia();

        // Inicializar estructuras de nodos personalizados
        this.listaConsolas = new ListaDobleEnlazada<>();
        this.listaJuegos = new ListaDobleEnlazada<>();
        this.listaControles = new ListaDobleEnlazada<>();
        this.listaAccesorios = new ListaDobleEnlazada<>();
        this.listaUsuarios = new ListaDobleEnlazada<>();

        this.carritoCompras = new Cola<>();
        this.listaDeseos = new ListaDobleEnlazada<>();
        this.historialPedidos = new ListaDobleEnlazada<>();
        this.ultimoPedidoConfirmado = new ListaDobleEnlazada<>();
        this.historialAccionesAdmin = new Pila<>();

        // Cargar datos automáticamente desde los archivos JSON al iniciar
        cargarDatosDesdeDisco();
    }

    // CARGAR DATOS (JSON -> Nodos)
    public void cargarDatosDesdeDisco() {
        List<Consola> consolasJava = persistencia.obtenerConsolas();
        listaConsolas.limpiarLista();
        if (consolasJava != null) {
            for (Consola c : consolasJava)
                listaConsolas.agregarLista(c);
        }

        List<Juego> juegosJava = persistencia.obtenerJuegos();
        listaJuegos.limpiarLista();
        if (juegosJava != null) {
            for (Juego j : juegosJava)
                listaJuegos.agregarLista(j);
        }

        List<Control> controlesJava = persistencia.obtenerControles();
        listaControles.limpiarLista();
        if (controlesJava != null) {
            for (Control co : controlesJava)
                listaControles.agregarLista(co);
        }

        List<Accesorios> accesoriosJava = persistencia.obtenerAccesorios();
        listaAccesorios.limpiarLista();
        if (accesoriosJava != null) {
            for (Accesorios ac : accesoriosJava)
                listaAccesorios.agregarLista(ac);
        }

        List<Usuario> usuariosJava = persistencia.obtenerUsuarios();
        listaUsuarios.limpiarLista();
        if (usuariosJava != null) {
            for (Usuario u : usuariosJava)
                listaUsuarios.agregarLista(u);
        }
    }

    // GUARDAR DATOS (Nodos -> JSON)
    public void guardarConsolasEnDisco() {
        List<Consola> list = new ArrayList<>();
        Nodo<Consola> aux = listaConsolas.getCabeza();
        while (aux != null) {
            list.add(aux.getDato());
            aux = aux.getSiguiente();
        }
        persistencia.guardarConsolas(list);
    }

    public void guardarJuegosEnDisco() {
        List<Juego> list = new ArrayList<>();
        Nodo<Juego> aux = listaJuegos.getCabeza();
        while (aux != null) {
            list.add(aux.getDato());
            aux = aux.getSiguiente();
        }
        persistencia.guardarJuegos(list);
    }

    public void guardarControlesEnDisco() {
        List<Control> list = new ArrayList<>();
        Nodo<Control> aux = listaControles.getCabeza();
        while (aux != null) {
            list.add(aux.getDato());
            aux = aux.getSiguiente();
        }
        persistencia.guardarControles(list);
    }

    public void guardarAccesoriosEnDisco() {
        List<Accesorios> list = new ArrayList<>();
        Nodo<Accesorios> aux = listaAccesorios.getCabeza();
        while (aux != null) {
            list.add(aux.getDato());
            aux = aux.getSiguiente();
        }
        persistencia.guardarAccesorios(list);
    }

    public void guardarUsuariosEnDisco() {
        List<Usuario> list = new ArrayList<>();
        Nodo<Usuario> aux = listaUsuarios.getCabeza();
        while (aux != null) {
            list.add(aux.getDato());
            aux = aux.getSiguiente();
        }
        persistencia.guardarUsuarios(list);
    }

    // GESTIÓN DE USUARIOS Y AUTENTICACIÓN
    public boolean registrarUsuario(Usuario nuevoUsuario) {
        // Verificar si ya existe el correo
        Nodo<Usuario> aux = listaUsuarios.getCabeza();
        while (aux != null) {
            if (aux.getDato().getCorreo().equalsIgnoreCase(nuevoUsuario.getCorreo())) {
                return false; // Ya existe
            }
            aux = aux.getSiguiente();
        }
        listaUsuarios.agregarLista(nuevoUsuario);
        guardarUsuariosEnDisco();
        return true;
    }

    public boolean iniciarSesion(String correo, String contrasena) {
        Nodo<Usuario> aux = listaUsuarios.getCabeza();
        while (aux != null) {
            Usuario u = aux.getDato();
            if (u.getCorreo().equalsIgnoreCase(correo) && u.getContrasena().equals(contrasena)) {
                this.usuarioActual = u;
                return true;
            }
            aux = aux.getSiguiente();
        }
        return false;
    }

    public void cerrarSesion() {
        this.usuarioActual = null;
        this.carritoCompras.limpiarCola();
        this.listaDeseos.limpiarLista();
    }

    // CARRITO Y PASARELA DE PAGO (Cola - FIFO)
    public void agregarAlCarrito(Producto p) {
        carritoCompras.agregarCola(p);
    }

    public Pedido confirmarYProcesarPedido(String metodoPago, String direccionEnvio) {
        ultimoPedidoConfirmado.limpiarLista();
        double total = 0;
        List<Producto> productosPedido = new ArrayList<>();

        while (!carritoCompras.estaVacia()) {
            Producto p = carritoCompras.desencolar();
            ultimoPedidoConfirmado.agregarLista(p);
            productosPedido.add(p);
            total += p.getPrecio();
        }

        //Crear objeto Pedido para el historial (visto por Admin y Cliente)
        String idPedido = "DEM-" + (100 + historialPedidos.getTamano() + 1);
        Pedido nuevoPedido = new Pedido(idPedido, usuarioActual != null ? usuarioActual.getNombre() : "Invitado",
                productosPedido, total, metodoPago, direccionEnvio);

        historialPedidos.agregarLista(nuevoPedido);
        if (usuarioActual != null) {
            usuarioActual.agregarPedido(nuevoPedido);
        }

        return nuevoPedido;
    }

    public ListaDobleEnlazada<Producto> getUltimoPedidoConfirmado() {
        return ultimoPedidoConfirmado;
    }

    public ListaDobleEnlazada<Pedido> getHistorialPedidos() {
        return historialPedidos;
    }

    //LISTA DE DESEOS (Wishlist)
    public void agregarAListaDeseos(Producto p) {
        //Evitar duplicados opcionalmente
        Nodo<Producto> aux = listaDeseos.getCabeza();
        while (aux != null) {
            if (aux.getDato().getId().equals(p.getId()))
                return;
            aux = aux.getSiguiente();
        }
        listaDeseos.agregarLista(p);
    }

    public boolean eliminarDeListaDeseos(Producto p) {
        return listaDeseos.eliminarLista(p);
    }

    public ListaDobleEnlazada<Producto> getListaDeseos() {
        return listaDeseos;
    }

    // ADMINISTRACIÓN CRUD DE INVENTARIO E HISTORIAL (Pila - LIFO)
    public void agregarConsola(Consola c) {
        listaConsolas.agregarLista(c);
        guardarConsolasEnDisco();
        historialAccionesAdmin.agregarPila("Se agregó la consola: " + c.getNombre());
    }

    public boolean eliminarConsola(Consola c) {
        boolean eliminado = listaConsolas.eliminarLista(c);
        if (eliminado) {
            guardarConsolasEnDisco();
            historialAccionesAdmin.agregarPila("Se eliminó la consola: " + c.getNombre());
        }
        return eliminado;
    }

    public void agregarJuego(Juego j) {
        listaJuegos.agregarLista(j);
        guardarJuegosEnDisco();
        historialAccionesAdmin.agregarPila("Se agregó el juego: " + j.getNombre());
    }

    public boolean eliminarJuego(Juego j) {
        boolean eliminado = listaJuegos.eliminarLista(j);
        if (eliminado) {
            guardarJuegosEnDisco();
            historialAccionesAdmin.agregarPila("Se eliminó el juego: " + j.getNombre());
        }
        return eliminado;
    }

    public String deshacerAccionAdmin() {
        if (!historialAccionesAdmin.estaVacia()) {
            return historialAccionesAdmin.desapilar();
        }
        return "No hay acciones recientes para deshacer.";
    }

    // GETTERS Y SETTERS GENERALES
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public ListaDobleEnlazada<Consola> getListaConsolas() {
        return listaConsolas;
    }

    public ListaDobleEnlazada<Juego> getListaJuegos() {
        return listaJuegos;
    }

    public ListaDobleEnlazada<Control> getListaControles() {
        return listaControles;
    }

    public ListaDobleEnlazada<Accesorios> getListaAccesorios() {
        return listaAccesorios;
    }

    public ListaDobleEnlazada<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public Cola<Producto> getCarritoCompras() {
        return carritoCompras;
    }

    public Pila<String> getHistorialAccionesAdmin() {
        return historialAccionesAdmin;
    }
}