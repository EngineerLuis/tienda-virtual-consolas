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
import model.Usuario; 
public class LoginController {

    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private CheckBox chkRecordarme;
    @FXML private Hyperlink linkOlvidar;
    @FXML private Button btnLogin;
    @FXML private Button btnCrearCuenta;
    @FXML private Hyperlink linkExplorar;

    //Instanciar controlador TiendaManager
    private TiendaManager tiendaManager;

    public LoginController() {
        this.tiendaManager = new TiendaManager();
    }

    @FXML
    private void onLogin(ActionEvent event) {
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText().trim();

        if (email.isEmpty() || password.isEmpty()) {
            mostrarAlerta("Campos incompletos", "Por favor completa el correo y la contraseña.", Alert.AlertType.WARNING);
            return;
        }
        
        // Validación usando TiendaManager y los archivos JSON/nodos
        boolean loginExitoso = tiendaManager.iniciarSesion(email, password);

        if (loginExitoso) {
            Usuario usuarioActual = tiendaManager.getUsuarioActual();
            
            // Verificamos si es Administrador o Cliente según el prototipo
            if (usuarioActual.getCorreo().equalsIgnoreCase("admin@tienda.com")) {
                cargarVista("/view/AdminInventario.fxml"); // Panel de administración del prototipo
            } else {
                cargarVista("/view/Catalogo.fxml"); // Tienda / Catálogo del cliente
            }
        } else {
            mostrarAlerta("Credenciales incorrectas", "El correo o la contraseña no coinciden.", Alert.AlertType.ERROR);
        }
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
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}