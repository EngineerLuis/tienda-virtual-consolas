/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

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

    @FXML private TableView<?> tablaVentas;
    @FXML private TableColumn<?, ?> colPedido;
    @FXML private TableColumn<?, ?> colFecha;
    @FXML private TableColumn<?, ?> colCliente;
    @FXML private TableColumn<?, ?> colItems;
    @FXML private TableColumn<?, ?> colTotal;
    @FXML private TableColumn<?, ?> colEstado;

    @FXML
    private void onInventario(ActionEvent event) {
        cargarVista("/view/Inventario.fxml");
    }

    @FXML
    private void onVentas(ActionEvent event) {
        // Ya estamos en Ventas
    }

    @FXML
    private void onClientes(ActionEvent event) {
        cargarVista("/view/Clientes.fxml");
    }

    @FXML
    private void onConfiguracion(ActionEvent event) {
        cargarVista("/view/Configuracion.fxml");
    }

    @FXML
    private void onVolverTienda(ActionEvent event) {
        cargarVista("/view/Catalogo.fxml");
    }

    private void cargarVista(String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();
            Stage stage = (Stage) btnVentas.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}