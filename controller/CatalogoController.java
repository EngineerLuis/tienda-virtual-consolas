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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class CatalogoController {

    @FXML private TextField txtBuscar;
    @FXML private Hyperlink linkListaDeseos;
    @FXML private Button btnCarrito;
    @FXML private Button btnMiCuenta;
    @FXML private Button btnDestacado;
    @FXML private ComboBox<String> cmbCategoria;
    @FXML private ComboBox<String> cmbOrdenar;
    @FXML private FlowPane flowProductos;

    @FXML
    public void initialize() {
        cmbCategoria.getItems().addAll("Todas", "Consolas", "Juegos", "Accesorios");
        cmbOrdenar.getItems().addAll("Precio: menor a mayor", "Precio: mayor a menor", "Nombre A-Z");
    }

    @FXML
    private void onListaDeseos(ActionEvent event) {
        cargarVista("/view/ListaDeseos.fxml");
    }

    @FXML
    private void onCarrito(ActionEvent event) {
        cargarVista("/view/Carrito.fxml");
    }

    @FXML
    private void onMiCuenta(ActionEvent event) {
        cargarVista("/view/MiCuenta.fxml");
    }

    @FXML
    private void onDestacado(ActionEvent event) {
        cargarVista("/view/DetalleProducto.fxml");
    }

    private void cargarVista(String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();
            Stage stage = (Stage) btnCarrito.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}