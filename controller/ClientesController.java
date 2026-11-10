/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

public class ClientesController {

    @FXML private Button btnInventario;
    @FXML private Button btnVentas;
    @FXML private Button btnClientes;
    @FXML private Button btnConfiguracion;
    @FXML private Button btnVolverTienda;

    @FXML private Label lblTotalClientes;
    @FXML private Label lblPedidos;
    @FXML private Label lblIngreso;

    @FXML private TableView<Usuario> tablaClientes;
    @FXML private TableColumn<Usuario, String> colNombre;
    @FXML private TableColumn<Usuario, String> colEmail;
    @FXML private TableColumn<Usuario, Integer> colPedidos;
    @FXML private TableColumn<Usuario, String> colEstado;

    private TiendaManager tiendaManager;

    @FXML
    public void initialize() {
        tiendaManager = new TiendaManager();

        // 1. Configurar las columnas con los atributos de la clase Usuario
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colPedidos.setCellValueFactory(new PropertyValueFactory<>("cantidadPedidos"));

        // 2. Cargar los datos desde las listas enlazadas de usuarios
        cargarDatosClientes();
    }

    private void cargarDatosClientes() {
        ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();
        int totalPedidosGlobal = 0;
        double ingresoGlobal = 0;

        Nodo<Usuario> aux = tiendaManager.getListaUsuarios().getCabeza();
        while (aux != null) {
            Usuario u = aux.getDato();
            listaUsuarios.add(u);
            
            // Si el usuario tiene historial de pedidos, sumamos métricas
            if (u.getMisPedidos() != null) {
                totalPedidosGlobal += u.getMisPedidos().size();
                for (Pedido p : u.getMisPedidos()) {
                    ingresoGlobal += p.getTotal();
                }
            }
            aux = aux.getSiguiente();
        }

        tablaClientes.setItems(listaUsuarios);
        lblTotalClientes.setText(String.valueOf(listaUsuarios.size()));
        lblPedidos.setText(String.valueOf(totalPedidosGlobal));
        lblIngreso.setText(String.format("$%.2f", ingresoGlobal));
    }

    @FXML
    private void onInventario(ActionEvent event) {
        cargarVista("/view/Inventario.fxml", event);
    }

    @FXML
    private void onVentas(ActionEvent event) {
        cargarVista("/view/Ventas.fxml", event);
    }

    @FXML
    private void onClientes(ActionEvent event) {
        // Ya estamos en Clientes
    }

    @FXML
    private void onConfiguracion(ActionEvent event) {
        cargarVista("/view/Configuracion.fxml", event);
    }

    @FXML
    private void onVolverTienda(ActionEvent event) {
        cargarVista("/view/Catalogo.fxml", event);
    }

    private void cargarVista(String rutaFXML, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}