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

public class VentasController {

    @FXML private Button btnInventario;
    @FXML private Button btnVentas;
    @FXML private Button btnClientes;
    @FXML private Button btnConfiguracion;
    @FXML private Button btnVolverTienda;

    @FXML private Label lblIngresos;
    @FXML private Label lblPedidos;
    @FXML private Label lblUnidades;
    @FXML private Label lblTicket;

    @FXML private TableView<Pedido> tablaVentas;
    @FXML private TableColumn<Pedido, String> colPedido;
    @FXML private TableColumn<Pedido, String> colFecha;
    @FXML private TableColumn<Pedido, String> colCliente;
    @FXML private TableColumn<Pedido, Integer> colItems;
    @FXML private TableColumn<Pedido, Float> colTotal;
    @FXML private TableColumn<Pedido, String> colMetodoPago;
    @FXML private TableColumn<Pedido, String> colDireccionEnvio;

    private TiendaManager tiendaManager;

    @FXML
    public void initialize() {
        tiendaManager = new TiendaManager();

        // 1. Configurar las columnas con los atributos de la clase Pedido
        colPedido.setCellValueFactory(new PropertyValueFactory<>("idPedido"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        colItems.setCellValueFactory(new PropertyValueFactory<>("cantidadItems")); 
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colMetodoPago.setCellValueFactory(new PropertyValueFactory<>("metodoPago"));
        colDireccionEnvio.setCellValueFactory(new PropertyValueFactory<>("direccionEnvio"));

        // 2. Cargar los datos reales desde el historial de pedidos
        cargarDatosVentas();
    }

    private void cargarDatosVentas() {
        ObservableList<Pedido> listaPedidos = FXCollections.observableArrayList();
        double ingresosTotales = 0;
        int unidadesVendidas = 0;

        // Recorrer el historial de pedidos usando la estructura de lista doblemente enlazada
        ListaDobleEnlazada<Pedido> historial = tiendaManager.getHistorialPedidos();
        Nodo<Pedido> aux = historial.getCabeza();
        
        while (aux != null) {
            Pedido pedido = aux.getDato();
            listaPedidos.add(pedido);
            
            ingresosTotales += pedido.getTotal();
            if (pedido.getProductos() != null) {
                unidadesVendidas += pedido.getProductos().size();
            }
            
            aux = aux.getSiguiente();
        }

        tablaVentas.setItems(listaPedidos);
        
        int totalPedidos = historial.getTamano();
        lblPedidos.setText(String.valueOf(totalPedidos));
        lblIngresos.setText(String.format("$%.2f", ingresosTotales));
        lblUnidades.setText(String.valueOf(unidadesVendidas));

        if (totalPedidos > 0) {
            double ticketPromedio = ingresosTotales / totalPedidos;
            lblTicket.setText(String.format("$%.2f", ticketPromedio));
        } else {
            lblTicket.setText("$0.00");
        }
    }

    @FXML
    private void onInventario(ActionEvent event) {
        cargarVista("/view/Inventario.fxml", event);
    }

    @FXML
    private void onVentas(ActionEvent event) {
        // Ya estamos en Ventas
    }

    @FXML
    private void onClientes(ActionEvent event) {
        cargarVista("/view/Clientes.fxml", event);
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