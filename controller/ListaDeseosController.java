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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ListaDeseosController {

    @FXML private TextField txtBuscar;
    @FXML private Hyperlink linkListaDeseos;
    @FXML private Button btnCarrito;
    @FXML private Button btnMiCuenta;

    @FXML private Label lblCantidad;
    @FXML private Label lblTotalProductos;
    @FXML private Label lblValorTotal;

    @FXML private Button btnMover1;
    @FXML private Button btnEliminar1;
    @FXML private Button btnMover2;
    @FXML private Button btnEliminar2;
    @FXML private Button btnMover3;
    @FXML private Button btnEliminar3;

    @FXML private Button btnVerProducto;
    @FXML private Button btnSeguirExplorando;

    @FXML
    private void onCarrito(ActionEvent event) {
        cargarVista("/view/Carrito.fxml");
    }

    @FXML
    private void onMiCuenta(ActionEvent event) {
        cargarVista("/view/MiCuenta.fxml");
    }

    @FXML
    private void onMover1(ActionEvent event) {
        // Simulación
        System.out.println("Producto 1 movido al carrito");
    }

    @FXML
    private void onEliminar1(ActionEvent event) {
        System.out.println("Producto 1 eliminado de la lista");
    }

    @FXML
    private void onMover2(ActionEvent event) {
        System.out.println("Producto 2 movido al carrito");
    }

    @FXML
    private void onEliminar2(ActionEvent event) {
        System.out.println("Producto 2 eliminado de la lista");
    }

    @FXML
    private void onMover3(ActionEvent event) {
        System.out.println("Producto 3 movido al carrito");
    }

    @FXML
    private void onEliminar3(ActionEvent event) {
        System.out.println("Producto 3 eliminado de la lista");
    }

    @FXML
    private void onVerProducto(ActionEvent event) {
        cargarVista("/view/DetalleProducto.fxml");
    }

    @FXML
    private void onSeguirExplorando(ActionEvent event) {
        cargarVista("/view/Catalogo.fxml");
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