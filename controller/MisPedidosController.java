/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Pedido;
import model.Producto;
import model.Usuario;

public class MisPedidosController {

    @FXML
    private Hyperlink linkVolverCuenta;
    @FXML
    private Label lblCantidadPedidos;
    @FXML
    private VBox contenedorPedidos;

    private TiendaManager tiendaManager;

    @FXML
    public void initialize() {
        tiendaManager = TiendaManager.getInstancia();
        cargarPedidosUsuario();
    }

    private void cargarPedidosUsuario() {
        contenedorPedidos.getChildren().clear();

        Usuario usuario = tiendaManager.getUsuarioActual();
        if (usuario == null || usuario.getMisPedidos() == null || usuario.getMisPedidos().isEmpty()) {
            actualizarContador(0);
            Label lblVacio = new Label("Aún no tienes pedidos registrados en tu cuenta.");
            lblVacio.setStyle("-fx-text-fill: #8b949e; -fx-font-size: 14;");
            contenedorPedidos.getChildren().add(lblVacio);
            return;
        }

        List<Pedido> pedidos = usuario.getMisPedidos();
        for (Pedido pedido : pedidos) {
            contenedorPedidos.getChildren().add(crearTarjetaPedido(pedido));
        }

        actualizarContador(pedidos.size());
    }

    private VBox crearTarjetaPedido(Pedido pedido) {
        VBox tarjeta = new VBox(8);
        tarjeta.setStyle("-fx-background-color: #161b22; -fx-background-radius: 10; -fx-padding: 16;");

        HBox encabezado = new HBox(10);
        Label lblCodigo = new Label(pedido.getIdPedido());
        lblCodigo.setStyle("-fx-text-fill: #00d4ff; -fx-font-weight: bold;");

        Label lblFecha = new Label(pedido.getFecha());
        lblFecha.setStyle("-fx-text-fill: #8b949e;");

        Region espacio = new Region();
        HBox.setHgrow(espacio, Priority.ALWAYS);

        Label lblEstado = new Label("Confirmado");
        lblEstado.setStyle("-fx-text-fill: #3fb950; -fx-font-weight: bold;");

        encabezado.getChildren().addAll(lblCodigo, lblFecha, espacio, lblEstado);

        String resumenProductos = construirResumenProductos(pedido.getProductos());
        Label lblProductos = new Label(resumenProductos);
        lblProductos.setStyle("-fx-text-fill: white;");
        lblProductos.setWrapText(true);

        String detallePago = (pedido.getMetodoPago() != null ? pedido.getMetodoPago() : "Pago simulado")
                + (pedido.getDireccionEnvio() != null ? "  |  " + pedido.getDireccionEnvio() : "");
        Label lblDetalle = new Label(detallePago);
        lblDetalle.setStyle("-fx-text-fill: #8b949e; -fx-font-size: 12;");
        lblDetalle.setWrapText(true);

        Label lblTotal = new Label("Total: $" + String.format("%.2f", pedido.getTotal()));
        lblTotal.setStyle("-fx-text-fill: #c9d1d9; -fx-font-weight: bold;");

        tarjeta.getChildren().addAll(encabezado, lblProductos, lblDetalle, lblTotal);
        return tarjeta;
    }

    private String construirResumenProductos(List<Producto> productos) {
        if (productos == null || productos.isEmpty()) {
            return "Sin productos registrados";
        }

        Map<String, Integer> conteo = new LinkedHashMap<>();
        for (Producto p : productos) {
            String nombre = p.getNombre() != null ? p.getNombre() : "Producto";
            conteo.put(nombre, conteo.getOrDefault(nombre, 0) + 1);
        }

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : conteo.entrySet()) {
            if (sb.length() > 0) {
                sb.append(" + ");
            }
            sb.append(entry.getKey());
            if (entry.getValue() > 1) {
                sb.append(" x").append(entry.getValue());
            }
        }
        return sb.toString();
    }

    private void actualizarContador(int cantidad) {
        lblCantidadPedidos.setText(cantidad + (cantidad == 1 ? " pedido" : " pedidos"));
    }

    @FXML
    private void onVolverCuenta(ActionEvent event) {
        cargarVista("/view/MiCuenta.fxml", event);
    }

    private void cargarVista(String rutaFXML, ActionEvent event) {
        try {
            java.net.URL recurso = getClass().getResource(rutaFXML);
            if (recurso == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Vista no disponible");
                alert.setHeaderText(null);
                alert.setContentText("No se encontró la vista: " + rutaFXML);
                alert.showAndWait();
                return;
            }
            FXMLLoader loader = new FXMLLoader(recurso);
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}