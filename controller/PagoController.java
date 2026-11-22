/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Cola;
import model.Pedido;
import model.Producto;

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

    private TiendaManager tiendaManager;
    private double totalCalculado = 0.0;

    @FXML
    public void initialize() {
        tiendaManager = TiendaManager.getInstancia();

        if (tiendaManager.getUsuarioActual() != null && txtNombreTarjeta != null) {
            txtNombreTarjeta.setText(tiendaManager.getUsuarioActual().getNombre());
        }

        cargarResumenDesdeCarrito();
    }

    private List<Producto> obtenerProductosEnCola() {
        List<Producto> listaTemporal = new ArrayList<>();
        Cola<Producto> cola = tiendaManager.getCarritoCompras();

        while (!cola.estaVacia()) {
            listaTemporal.add(cola.desencolar());
        }

        for (Producto p : listaTemporal) {
            cola.agregarCola(p);
        }

        return listaTemporal;
    }

    private void cargarResumenDesdeCarrito() {
        contenedorResumen.getChildren().clear();
        List<Producto> productos = obtenerProductosEnCola();

        Map<String, Integer> cantidadesPorId = new LinkedHashMap<>();
        Map<String, Producto> productosPorId = new LinkedHashMap<>();
        double subtotal = 0.0;

        for (Producto p : productos) {
            String clave = p.getId() != null ? p.getId() : p.getNombre();
            cantidadesPorId.put(clave, cantidadesPorId.getOrDefault(clave, 0) + 1);
            productosPorId.putIfAbsent(clave, p);
            subtotal += p.getPrecio();
        }

        for (String clave : productosPorId.keySet()) {
            Producto p = productosPorId.get(clave);
            int cantidad = cantidadesPorId.get(clave);
            agregarItemResumen(p.getNombre(), cantidad, p.getPrecio());
        }

        totalCalculado = subtotal;
        actualizarTotales(totalCalculado);
    }

    private void agregarItemResumen(String nombre, int cantidad, double precioUnitario) {
        HBox fila = new HBox(10);
        fila.setAlignment(Pos.CENTER_LEFT);
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
        if (tiendaManager.getCarritoCompras().estaVacia()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Carrito vacío", "No hay productos en el carrito para procesar el pago.");
            return;
        }

        String numero = txtNumeroTarjeta.getText() == null ? "" : txtNumeroTarjeta.getText().trim();
        String expiracion = txtExpiracion.getText() == null ? "" : txtExpiracion.getText().trim();
        String cvv = txtCvv.getText() == null ? "" : txtCvv.getText().trim();
        String nombre = txtNombreTarjeta.getText() == null ? "" : txtNombreTarjeta.getText().trim();

        if (numero.isEmpty() || expiracion.isEmpty() || cvv.isEmpty() || nombre.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", "Por favor completa todos los datos de la tarjeta.");
            return;
        }

        String metodoPago = "Tarjeta simulada (" + (numero.length() >= 4 ? numero.substring(numero.length() - 4) : numero) + ")";
        String direccionEnvio = chkMismaDireccion != null && chkMismaDireccion.isSelected()
                ? "Dirección de entrega registrada"
                : "Dirección de facturación";

        Pedido pedidoConfirmado = tiendaManager.confirmarYProcesarPedido(metodoPago, direccionEnvio);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Pago exitoso");
        alert.setHeaderText("Pedido confirmado (" + pedidoConfirmado.getIdPedido() + ")");
        alert.setContentText("Total pagado: $" + String.format("%.2f", pedidoConfirmado.getTotal())
                + "\nTu pedido ya quedó registrado en tu cuenta. ¡Gracias por comprar en Nexo!");
        alert.showAndWait();

        cargarVista("/view/Catalogo.fxml", event);
    }

    @FXML
    private void onVolverCarrito(ActionEvent event) {
        cargarVista("/view/Carrito.fxml", event);
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void cargarVista(String rutaFXML, ActionEvent event) {
        try {
            java.net.URL recurso = getClass().getResource(rutaFXML);
            if (recurso == null) {
                mostrarAlerta(Alert.AlertType.ERROR, "Vista no disponible", "No se encontró la vista: " + rutaFXML);
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