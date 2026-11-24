/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MisPedidosController {

    @FXML private Hyperlink linkVolverCuenta;
    @FXML private Label lblCantidadPedidos;
    @FXML private VBox contenedorPedidos;

    @FXML
    public void initialize() {
        // Ejemplos de pedidos (luego se conectan con datos reales)
        agregarPedido("ORD-1001", "15 Nov 2026", "PlayStation 5 Slim + DualSense", 637.00, "Entregado");
        agregarPedido("ORD-1002", "10 Nov 2026", "Astro Bot - PS5", 79.00, "En camino");
        agregarPedido("ORD-1003", "02 Nov 2026", "HyperX Cloud III", 89.00, "Entregado");
    }

    private void agregarPedido(String codigo, String fecha, String productos, double total, String estado) {
        VBox tarjeta = new VBox(8);
        tarjeta.setStyle("-fx-background-color: #161b22; -fx-background-radius: 10; -fx-padding: 16;");

        HBox encabezado = new HBox(10);
        Label lblCodigo = new Label(codigo);
        lblCodigo.setStyle("-fx-text-fill: #00d4ff; -fx-font-weight: bold;");

        Label lblFecha = new Label(fecha);
        lblFecha.setStyle("-fx-text-fill: #8b949e;");

        Region espacio = new Region();
        HBox.setHgrow(espacio, Priority.ALWAYS);

        Label lblEstado = new Label(estado);
        if (estado.equals("Entregado")) {
            lblEstado.setStyle("-fx-text-fill: #3fb950; -fx-font-weight: bold;");
        } else {
            lblEstado.setStyle("-fx-text-fill: #d29922; -fx-font-weight: bold;");
        }

        encabezado.getChildren().addAll(lblCodigo, lblFecha, espacio, lblEstado);

        Label lblProductos = new Label(productos);
        lblProductos.setStyle("-fx-text-fill: white;");

        Label lblTotal = new Label("Total: $" + String.format("%.2f", total));
        lblTotal.setStyle("-fx-text-fill: #c9d1d9;");

        tarjeta.getChildren().addAll(encabezado, lblProductos, lblTotal);
        contenedorPedidos.getChildren().add(tarjeta);

        actualizarContador();
    }

    private void actualizarContador() {
        int cantidad = contenedorPedidos.getChildren().size();
        lblCantidadPedidos.setText(cantidad + (cantidad == 1 ? " pedido" : " pedidos"));
    }

    @FXML
    private void onVolverCuenta(ActionEvent event) {
        cargarVista("/view/MiCuenta.fxml");
    }

    private void cargarVista(String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();
            Stage stage = (Stage) linkVolverCuenta.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}