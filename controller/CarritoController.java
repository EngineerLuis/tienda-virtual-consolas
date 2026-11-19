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

public class CarritoController {

    @FXML private TextField txtBuscar;
    @FXML private Hyperlink linkListaDeseos;
    @FXML private Button btnCarrito;
    @FXML private Button btnMiCuenta;

    @FXML private Label lblCantidadItems;
    @FXML private VBox contenedorProductos;

    @FXML private Hyperlink linkSeguirComprando;

    @FXML private TextField txtNombre;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtDireccion;

    @FXML private Label lblSubtotal;
    @FXML private Label lblEnvio;
    @FXML private Label lblTotal;
    @FXML private TextField txtCodigo;
    @FXML private Button btnAplicar;

    @FXML private RadioButton rbTarjeta;
    @FXML private RadioButton rbTransferencia;
    @FXML private Button btnProcederPago;

    @FXML
    public void initialize() {
        ToggleGroup grupoPago = new ToggleGroup();
        rbTarjeta.setToggleGroup(grupoPago);
        rbTransferencia.setToggleGroup(grupoPago);

        // Ejemplo de productos (luego se reemplaza por datos reales)
        agregarProducto("PlayStation 5 Slim", "Consola", 499.00, 1);
        agregarProducto("DualSense - Blanco", "Accesorio", 69.00, 2);
        agregarProducto("Astro Bot - PS5", "Juego", 79.00, 1);
    }

    private void agregarProducto(String nombre, String categoria, double precio, int cantidad) {
        HBox tarjeta = new HBox(15);
        tarjeta.setStyle("-fx-background-color: #161b22; -fx-background-radius: 10; -fx-padding: 15;");
        tarjeta.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        Label lblNombre = new Label(nombre);
        lblNombre.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14;");

        Label lblInfo = new Label(categoria + "  |  $" + String.format("%.2f", precio));
        lblInfo.setStyle("-fx-text-fill: #8b949e;");

        VBox info = new VBox(4, lblNombre, lblInfo);
        HBox.setHgrow(info, Priority.ALWAYS);

        Button btnMenos = new Button("-");
        btnMenos.setStyle("-fx-background-color: #21262d; -fx-text-fill: white; -fx-min-width: 30;");
        Label lblCant = new Label(String.valueOf(cantidad));
        lblCant.setStyle("-fx-text-fill: white;");
        Button btnMas = new Button("+");
        btnMas.setStyle("-fx-background-color: #21262d; -fx-text-fill: white; -fx-min-width: 30;");

        HBox controles = new HBox(8, btnMenos, lblCant, btnMas);
        controles.setAlignment(javafx.geometry.Pos.CENTER);

        Label lblSub = new Label("$" + String.format("%.2f", precio * cantidad));
        lblSub.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        lblSub.setPrefWidth(80);

        Button btnEliminar = new Button("X");
        btnEliminar.setStyle("-fx-background-color: transparent; -fx-text-fill: #f85149; -fx-font-weight: bold;");
        btnEliminar.setOnAction(e -> {
            contenedorProductos.getChildren().remove(tarjeta);
            actualizarResumen();
        });

        tarjeta.getChildren().addAll(info, controles, lblSub, btnEliminar);
        contenedorProductos.getChildren().add(tarjeta);

        actualizarResumen();
    }

    private void actualizarResumen() {
        int totalProductos = contenedorProductos.getChildren().size();
        lblCantidadItems.setText(totalProductos + (totalProductos == 1 ? " producto" : " productos"));

        // Aquí más adelante se calculará el total real
        // Por ahora solo actualizamos la cantidad
    }

    @FXML
    private void onListaDeseos(ActionEvent event) {
        cargarVista("/view/ListaDeseos.fxml");
    }

    @FXML
    private void onMiCuenta(ActionEvent event) {
        cargarVista("/view/MiCuenta.fxml");
    }

    @FXML
    private void onSeguirComprando(ActionEvent event) {
        cargarVista("/view/Catalogo.fxml");
    }

    @FXML
    private void onAplicar(ActionEvent event) {
        // Lógica de código promocional
    }

    @FXML
    private void onProcederPago(ActionEvent event) {
        cargarVista("/view/Pago.fxml");
    }

    private void cargarVista(String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();
            Stage stage = (Stage) btnProcederPago.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}