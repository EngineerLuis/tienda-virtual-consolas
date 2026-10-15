package controller;

import model.Accesorios;
import model.Consola;
import model.Control;
import model.Juego;
import model.Usuario;
import java.util.List;

public class ControladorPersistencia {
    
    private final GestorJson<Usuario> gestorUsuario;
    private final GestorJson<Consola> gestorConsola;
    private final GestorJson<Control> gestorControl;
    private final GestorJson<Juego> gestorJuego;
    private final GestorJson<Accesorios> gestorAccesorios;

    public ControladorPersistencia() {
        this.gestorUsuario = new GestorJson<>("data/usuarios.json", Usuario[].class);
        this.gestorConsola = new GestorJson<>("data/consolas.json", Consola[].class);
        this.gestorControl = new GestorJson<>("data/controles.json", Control[].class);
        this.gestorJuego = new GestorJson<>("data/juegos.json", Juego[].class);
        this.gestorAccesorios = new GestorJson<>("data/accesorios.json", Accesorios[].class);
    }

    //gestion de usuarios
    public List<Usuario> obtenerUsuarios() {
        return gestorUsuario.leerDatos();
    }

    public void guardarUsuarios(List<Usuario> usuarios) {
        gestorUsuario.escribirDatos(usuarios);
    }

    //gestion de consolas
    public List<Consola> obtenerConsolas() {
        return gestorConsola.leerDatos();
    }

    public void guardarConsolas(List<Consola> consolas) {
        gestorConsola.escribirDatos(consolas);
    }

    //gestion de controles
    public List<Control> obtenerControles() {
        return gestorControl.leerDatos();
    }

    public void guardarControles(List<Control> controles) {
        gestorControl.escribirDatos(controles);
    }

    //gestion de juegos
    public List<Juego> obtenerJuegos() {
        return gestorJuego.leerDatos();
    }

    public void guardarJuegos(List<Juego> juegos) {
        gestorJuego.escribirDatos(juegos);
    }
    
    //gestion de accesorios
    public List<Accesorios> obtenerAccesorios() {
        return gestorAccesorios.leerDatos();
    }

    public void guardarAccesorios(List<Accesorios> accesorios) {
        gestorAccesorios.escribirDatos(accesorios);
    }


}
