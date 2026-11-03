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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RecuperarContrasenaController {

    @FXML private TextField txtEmail;
    @FXML private Button btnEnviar;
    @FXML private Button btnVolverLogin;

    @FXML
    private void onEnviar(ActionEvent event) {
        String email = txtEmail.getText().trim();

        if (email.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campo vacío");
            alert.setHeaderText(null);
            alert.setContentText("Por favor ingresa tu correo electrónico.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Instrucciones enviadas");
        alert.setHeaderText(null);
        alert.setContentText("Si el correo está registrado, recibirás las instrucciones de recuperación.");
        alert.showAndWait();
    }

    @FXML
    private void onVolverLogin(ActionEvent event) {
        cargarVista("/view/Login.fxml");
    }

    private void cargarVista(String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();
            Stage stage = (Stage) btnEnviar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}