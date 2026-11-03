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
import model.Usuario;
/**
 *
 * @author 74ND
 */
public class CrearCuentaController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtConfirmarPassword;
    @FXML private CheckBox chkTerminos;
    @FXML private Button btnCrearCuenta;
    @FXML private Button btnVolverLogin;

    // Instancia del backend TiendaManager
    private final TiendaManager tiendaManager;

    public CrearCuentaController() {
        this.tiendaManager = new TiendaManager();
    }

    @FXML
    private void onCrearCuenta(ActionEvent event) {
        String nombre = txtNombre.getText().trim();
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText().trim();
        String confirmarPassword = txtConfirmarPassword.getText().trim();

        // 1. Validar campos vacíos
        if (nombre.isEmpty() || email.isEmpty() || password.isEmpty() || confirmarPassword.isEmpty()) {
            mostrarAlerta("Campos incompletos", "Por favor completa todos los campos del formulario.", Alert.AlertType.WARNING);
            return;
        }

        // 2. Validar coincidencia de contraseñas
        if (!password.equals(confirmarPassword)) {
            mostrarAlerta("Contraseñas no coinciden", "La contraseña y la confirmación deben ser iguales.", Alert.AlertType.ERROR);
            return;
        }

        // 3. Validar aceptación de términos
        if (!chkTerminos.isSelected()) {
            mostrarAlerta("Términos y condiciones", "Debes aceptar los términos y condiciones para crear una cuenta.", Alert.AlertType.WARNING);
            return;
        }

        // 4. Crear el objeto Usuario (Asegúrate de ajustar el constructor según los atributos de tu clase Usuario)
        // Por lo general recibe: (nombre, correo, contrasena) o similar
        Usuario nuevoUsuario = new Usuario(nombre, email, password);

        // 5. Intentar registrar en el TiendaManager (valida si ya existe el correo y guarda en JSON)
        boolean registradoExitoso = tiendaManager.registrarUsuario(nuevoUsuario);

        if (registradoExitoso) {
            mostrarAlerta("¡Cuenta creada!", "Tu cuenta ha sido registrada con éxito. Ya puedes iniciar sesión.", Alert.AlertType.INFORMATION);
            cargarVista("/view/Login.fxml");
        } else {
            mostrarAlerta("Error de registro", "El correo electrónico ya se encuentra registrado en el sistema.", Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
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
}
