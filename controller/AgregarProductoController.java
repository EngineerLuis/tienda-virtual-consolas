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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AgregarProductoController {

    @FXML private Button btnInventario;
    @FXML private Button btnVentas;
    @FXML private Button btnClientes;
    @FXML private Button btnConfiguracion;
    @FXML private Button btnVolverTienda;

    @FXML private TextField txtNombre;
    @FXML private ComboBox<String> cmbCategoria;
    @FXML private TextArea txtDescripcion;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtStock;
    @FXML private Label lblNombreImagen;
    @FXML private Button btnSeleccionarImagen;

    @FXML private TextField txtProcesador;
    @FXML private TextField txtAlmacenamiento;
    @FXML private TextField txtResolucion;
    @FXML private TextField txtConectividad;

    @FXML private Button btnCancelar;
    @FXML private Button btnGuardar;

    @FXML
    public void initialize() {
        cmbCategoria.getItems().addAll("Consolas", "Juegos", "Accesorios", "Periféricos");
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
        cargarVista("/view/Configuracion.fxml");
    }

    @FXML
    private void onVolverTienda(ActionEvent event) {
        cargarVista("/view/Catalogo.fxml");
    }

    @FXML
    private void onSeleccionarImagen(ActionEvent event) {
        // Por ahora solo simulamos
        lblNombreImagen.setText("imagen_producto.png");
    }

    @FXML
    private void onCancelar(ActionEvent event) {
        cargarVista("/view/Inventario.fxml");
    }

    @FXML
    private void onGuardar(ActionEvent event) {
        String nombre = txtNombre.getText().trim();
        String precio = txtPrecio.getText().trim();
        String stock = txtStock.getText().trim();

        if (nombre.isEmpty() || precio.isEmpty() || stock.isEmpty() || cmbCategoria.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos incompletos");
            alert.setHeaderText(null);
            alert.setContentText("Por favor completa los campos obligatorios (*).");
            alert.showAndWait();
            return;
        }

        // Simulación de guardado exitoso
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Producto guardado");
        alert.setHeaderText(null);
        alert.setContentText("El producto se ha agregado correctamente.");
        alert.showAndWait();

        cargarVista("/view/Inventario.fxml");
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