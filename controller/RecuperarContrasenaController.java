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
import model.Usuario;
import model.Nodo;

public class RecuperarContrasenaController {

    @FXML private TextField txtEmail;
    @FXML private Button btnEnviar;
    @FXML private Button btnVolverLogin;

    // Instancia del backend para consultar los usuarios guardados
    private final TiendaManager tiendaManager;

    public RecuperarContrasenaController() {
        this.tiendaManager = new TiendaManager();
    }

@FXML
    private void onEnviar(ActionEvent event) {
        String email = txtEmail.getText().trim();

        if (email.isEmpty()) {
            mostrarAlerta("Campo vacío", "Por favor ingresa tu correo electrónico.", Alert.AlertType.WARNING);
            return;
        }

        // Verificar si el correo existe en el sistema
        boolean correoEncontrado = false;
        Nodo<Usuario> aux = tiendaManager.getListaUsuarios().getCabeza();
        while (aux != null) {
            if (aux.getDato().getCorreo().equalsIgnoreCase(email)) {
                correoEncontrado = true;
                break;
            }
            aux = aux.getSiguiente();
        }

        if (correoEncontrado) {
            // Si existe, abrimos la pantalla para cambiar la contraseña y le pasamos el correo
            abrirPantallaCambioContrasena(email);
        } else {
            mostrarAlerta("Correo no encontrado", "El correo ingresado no está registrado en Nexo.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onVolverLogin(ActionEvent event) {
        cargarVista("/view/Login.fxml");
    }

    private void abrirPantallaCambioContrasena(String email) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CambiarContrasena.fxml"));
            Parent root = loader.load();
            
            // Le pasamos el correo al siguiente controlador para saber a quién modificarle la clave
            CambiarContrasenaController controller = loader.getController();
            controller.initData(email);

            Stage stage = (Stage) btnEnviar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la vista de cambio de contraseña.", Alert.AlertType.ERROR);
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
            Stage stage = (Stage) btnEnviar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}