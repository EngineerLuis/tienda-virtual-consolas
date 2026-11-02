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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private CheckBox chkRecordarme;
    @FXML private Hyperlink linkOlvidar;
    @FXML private Button btnLogin;
    @FXML private Button btnCrearCuenta;
    @FXML private Hyperlink linkExplorar;

    @FXML
    private void onLogin(ActionEvent event) {
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos incompletos");
            alert.setHeaderText(null);
            alert.setContentText("Por favor completa el correo y la contraseña.");
            alert.showAndWait();
            return;
        }

        cargarVista("/view/Catalogo.fxml");
    }

    @FXML
    private void onCrearCuenta(ActionEvent event) {
        cargarVista("/view/CrearCuenta.fxml");
    }

    @FXML
    private void onOlvidar(ActionEvent event) {
        cargarVista("/view/RecuperarContrasena.fxml");
    }

    @FXML
    private void onExplorar(ActionEvent event) {
        cargarVista("/view/Catalogo.fxml");
    }

    private void cargarVista(String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}