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
import javafx.stage.Stage;

public class MiCuentaController {

    @FXML private Button btnInformacion;
    @FXML private Button btnCarrito;
    @FXML private Button btnListaDeseos;
    @FXML private Button btnMisPedidos;
    @FXML private Button btnCerrarSesion;
    @FXML private Hyperlink linkVolver;

    @FXML
    private void onInformacion(ActionEvent event) {
        // Por ahora solo muestra mensaje. Luego se puede crear la vista.
        System.out.println("Ir a Informacion personal");
    }

    @FXML
    private void onCarrito(ActionEvent event) {
        cargarVista("/view/Carrito.fxml");
    }

    @FXML
    private void onListaDeseos(ActionEvent event) {
        cargarVista("/view/ListaDeseos.fxml");
    }

    @FXML
    private void onMisPedidos(ActionEvent event) {
        cargarVista("/view/MisPedidos.fxml");
    }

    @FXML
    private void onCerrarSesion(ActionEvent event) {
        cargarVista("/view/Login.fxml");
    }

    @FXML
    private void onVolver(ActionEvent event) {
        cargarVista("/view/Catalogo.fxml");
    }

    private void cargarVista(String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();
            
            // Usamos cualquier control que esté en la escena
            Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}