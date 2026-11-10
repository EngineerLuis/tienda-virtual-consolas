package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class CambiarContrasenaController {

    @FXML private PasswordField txtNuevaPassword;
    @FXML private PasswordField txtConfirmarPassword;
    @FXML private Button btnActualizar;

    private String correoUsuario;
    private final TiendaManager tiendaManager;

    public CambiarContrasenaController() {
        this.tiendaManager = new TiendaManager();
    }

    // Método para recibir el correo desde la ventana anterior
    public void initData(String correo) {
        this.correoUsuario = correo;
    }

    @FXML
    private void onActualizar(ActionEvent event) {
        String nuevaPass = txtNuevaPassword.getText().trim();
        String confirmarPass = txtConfirmarPassword.getText().trim();

        if (nuevaPass.isEmpty() || confirmarPass.isEmpty()) {
            mostrarAlerta("Campos incompletos", "Por favor completa ambos campos.", Alert.AlertType.WARNING);
            return;
        }

        if (!nuevaPass.equals(confirmarPass)) {
            mostrarAlerta("Contraseñas no coinciden", "La nueva contraseña y la confirmación deben ser iguales.", Alert.AlertType.ERROR);
            return;
        }

        // Actualizamos la contraseña en el TiendaManager (y en el JSON)
        boolean actualizado = tiendaManager.actualizarContrasena(correoUsuario, nuevaPass);

        if (actualizado) {
            mostrarAlerta("¡Éxito!", "Tu contraseña ha sido actualizada correctamente. Inicia sesión con tu nueva clave.", Alert.AlertType.INFORMATION);
            cargarVista("/view/Login.fxml");
        } else {
            mostrarAlerta("Error", "No se pudo actualizar la contraseña.", Alert.AlertType.ERROR);
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
            Stage stage = (Stage) btnActualizar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
