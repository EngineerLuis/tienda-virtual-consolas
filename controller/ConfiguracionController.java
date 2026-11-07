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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConfiguracionController {

    @FXML private Button btnInventario;
    @FXML private Button btnVentas;
    @FXML private Button btnClientes;
    @FXML private Button btnConfiguracion;
    @FXML private Button btnVolverTienda;

    @FXML private TextField txtNombreTienda;
    @FXML private TextField txtCorreo;
    @FXML private TextArea txtDescripcion;
    @FXML private ComboBox<String> cmbMoneda;
    @FXML private ComboBox<String> cmbIdioma;

    @FXML private CheckBox chkAvisos;
    @FXML private CheckBox chkSimular;
    @FXML private CheckBox chkNotificaciones;

    @FXML private Button btnCancelar;
    @FXML private Button btnGuardar;

    @FXML
    public void initialize() {
        cmbMoneda.getItems().addAll("USD", "EUR", "COP", "MXN");
        cmbMoneda.setValue("USD");

        cmbIdioma.getItems().addAll("Español", "English");
        cmbIdioma.setValue("Español");
    }

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
        cargarVista("/view/Clientes.fxml");
    }

    @FXML
    private void onConfiguracion(ActionEvent event) {
        // Ya estamos en Configuración
    }

    @FXML
    private void onVolverTienda(ActionEvent event) {
        cargarVista("/view/Catalogo.fxml");
    }

    @FXML
    private void onCancelar(ActionEvent event) {
        cargarVista("/view/Inventario.fxml");
    }

    @FXML
    private void onGuardar(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cambios guardados");
        alert.setHeaderText(null);
        alert.setContentText("La configuración se ha actualizado correctamente.");
        alert.showAndWait();
    }

    private void cargarVista(String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();
            Stage stage = (Stage) btnGuardar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}