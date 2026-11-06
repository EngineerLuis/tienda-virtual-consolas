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

public class ClientesController {

    @FXML private Button btnInventario;
    @FXML private Button btnVentas;
    @FXML private Button btnClientes;
    @FXML private Button btnConfiguracion;
    @FXML private Button btnVolverTienda;

    @FXML private Label lblTotalClientes;
    @FXML private Label lblPedidos;
    @FXML private Label lblIngreso;

    @FXML private TableView<?> tablaClientes;
    @FXML private TableColumn<?, ?> colNombre;
    @FXML private TableColumn<?, ?> colEmail;
    @FXML private TableColumn<?, ?> colPedidos;
    @FXML private TableColumn<?, ?> colEstado;

    @FXML
    private void onInventario(ActionEvent event) {
        cargarVista("/view/Inventario.fxml");
    }

    @FXML
    private void onVentas(ActionEvent event) {
        cargarVista("/view/Ventas.fxml");
    }

    @FXML
    private void onClientes(ActionEvent event) {
        // Ya estamos en Clientes
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
            Stage stage = (Stage) btnClientes.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}