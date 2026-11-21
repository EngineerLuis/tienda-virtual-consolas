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
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PagoController {

    @FXML private TextField txtNumeroTarjeta;
    @FXML private TextField txtExpiracion;
    @FXML private TextField txtCvv;
    @FXML private TextField txtNombreTarjeta;
    @FXML private CheckBox chkMismaDireccion;

    @FXML private Button btnConfirmarPago;
    @FXML private Hyperlink linkVolverCarrito;

    @FXML private VBox contenedorResumen;
    @FXML private Label lblSubtotal;
    @FXML private Label lblTotal;

    @FXML
    public void initialize() {
        // Ejemplo de resumen (luego se conecta con los datos reales del carrito)
        agregarItemResumen("PlayStation 5 Slim", 1, 499.00);
        agregarItemResumen("DualSense - Blanco", 2, 69.00);
        agregarItemResumen("Astro Bot - PS5", 1, 79.00);

        actualizarTotales(499.00 + (69.00 * 2) + 79.00);
    }

    private void agregarItemResumen(String nombre, int cantidad, double precioUnitario) {
        HBox fila = new HBox(10);
        Label lblNombre = new Label(nombre + " x" + cantidad);
        lblNombre.setStyle("-fx-text-fill: #c9d1d9;");
        Region espacio = new Region();
        HBox.setHgrow(espacio, Priority.ALWAYS);
        Label lblPrecio = new Label("$" + String.format("%.2f", precioUnitario * cantidad));
        lblPrecio.setStyle("-fx-text-fill: white;");

        fila.getChildren().addAll(lblNombre, espacio, lblPrecio);
        contenedorResumen.getChildren().add(fila);
    }

    private void actualizarTotales(double total) {
        lblSubtotal.setText("$" + String.format("%.2f", total));
        lblTotal.setText("$" + String.format("%.2f", total));
    }

    @FXML
    private void onConfirmarPago(ActionEvent event) {
        String numero = txtNumeroTarjeta.getText().trim();
        String expiracion = txtExpiracion.getText().trim();
        String cvv = txtCvv.getText().trim();
        String nombre = txtNombreTarjeta.getText().trim();

        if (numero.isEmpty() || expiracion.isEmpty() || cvv.isEmpty() || nombre.isEmpty()) {
            mostrarAlerta("Campos incompletos", "Por favor completa todos los datos de la tarjeta.");
            return;
        }

        // Simulación de pago exitoso
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Pago exitoso");
        alert.setHeaderText(null);
        alert.setContentText("Tu pedido ha sido confirmado. Gracias por comprar en Nexo.");
        alert.showAndWait();

        // Volver al catálogo después del pago
        cargarVista("/view/Catalogo.fxml");
    }

    @FXML
    private void onVolverCarrito(ActionEvent event) {
        cargarVista("/view/Carrito.fxml");
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void cargarVista(String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();
            Stage stage = (Stage) btnConfirmarPago.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}