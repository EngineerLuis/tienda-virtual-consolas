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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CrearCuentaController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtConfirmarPassword;
    @FXML private CheckBox chkTerminos;
    @FXML private Button btnCrearCuenta;
    @FXML private Button btnVolverLogin;

    @FXML
    private void onCrearCuenta(ActionEvent event) {
        String nombre = txtNombre.getText().trim();
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText().trim();
        String confirmar = txtConfirmarPassword.getText().trim();

        if (nombre.isEmpty() || email.isEmpty() || password.isEmpty() || confirmar.isEmpty()) {
            mostrarAlerta("Campos incompletos", "Por favor completa todos los campos.");
            return;
        }

        if (!password.equals(confirmar)) {
            mostrarAlerta("Contraseñas no coinciden", "Las contraseñas deben ser iguales.");
            return;
        }

        if (!chkTerminos.isSelected()) {
            mostrarAlerta("Términos no aceptados", "Debes aceptar los términos y condiciones.");
            return;
        }

        // Si todo está bien, vuelve al Login (simulación de registro exitoso)
        cargarVista("/view/Login.fxml");
    }

    @FXML
    private void onVolverLogin(ActionEvent event) {
        cargarVista("/view/Login.fxml");
    }

    private void cargarVista(String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();
            Stage stage = (Stage) btnCrearCuenta.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}