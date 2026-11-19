/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import model.Cola;
import model.Producto;

public class CarritoController {

    @FXML
    private TextField txtBuscar;
    @FXML
    private Hyperlink linkListaDeseos;
    @FXML
    private Button btnCarrito;
    @FXML
    private Button btnMiCuenta;

    @FXML
    private Label lblCantidadItems;
    @FXML
    private VBox contenedorProductos;

    @FXML
    private Hyperlink linkSeguirComprando;

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtDireccion;

    @FXML
    private Label lblSubtotal;
    @FXML
    private Label lblEnvio;
    @FXML
    private Label lblTotal;
    @FXML
    private TextField txtCodigo;
    @FXML
    private Button btnAplicar;

    @FXML
    private RadioButton rbTarjeta;
    @FXML
    private RadioButton rbTransferencia;
    @FXML
    private Button btnProcederPago;

    private TiendaManager tiendaManager;
    private double descuentoPorcentaje = 0.0; // Porcentaje de descuento aplicado

    @FXML
    public void initialize() {
        tiendaManager = TiendaManager.getInstancia();
        ToggleGroup grupoPago = new ToggleGroup();
        rbTarjeta.setToggleGroup(grupoPago);
        rbTransferencia.setToggleGroup(grupoPago);

        if (tiendaManager.getUsuarioActual() != null && txtNombre != null) {
            txtNombre.setText(tiendaManager.getUsuarioActual().getNombre());
        }
        cargarCarritoDesdeManager();
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

    private void cargarCarritoDesdeManager() {
        contenedorProductos.getChildren().clear();
        List<Producto> productos = obtenerProductosEnCola();

        for (Producto producto : productos) {
            contenedorProductos.getChildren().add(crearTarjetaProducto(producto));
        }

        actualizarResumen(productos);
    }

    private HBox crearTarjetaProducto(Producto producto) {
        HBox tarjeta = new HBox(15);
        tarjeta.setStyle("-fx-background-color: #161b22; -fx-background-radius: 10; -fx-padding: 15;");
        tarjeta.setAlignment(Pos.CENTER_LEFT);

        String categoria = producto.getCategoria() != null ? producto.getCategoria() : "Producto";

        Label lblNombre = new Label(producto.getNombre());
        lblNombre.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14;");

        Label lblInfo = new Label(categoria + "  |  Stock: " + producto.getStock());
        lblInfo.setStyle("-fx-text-fill: #8b949e;");

        VBox info = new VBox(4, lblNombre, lblInfo);
        HBox.setHgrow(info, Priority.ALWAYS);

        Label lblSub = new Label("$" + String.format("%.2f", producto.getPrecio()));
        lblSub.setStyle("-fx-text-fill: #00d4ff; -fx-font-weight: bold; -fx-font-size: 14;");
        lblSub.setPrefWidth(95);

        Button btnEliminar = new Button("X");
        btnEliminar.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: #f85149; -fx-font-weight: bold; -fx-cursor: hand;");
        btnEliminar.setOnAction(e -> eliminarProductoDeCola(producto));

        tarjeta.getChildren().addAll(info, lblSub, btnEliminar);
        return tarjeta;
    }

    private void eliminarProductoDeCola(Producto productoAEliminar) {
        Cola<Producto> cola = tiendaManager.getCarritoCompras();
        List<Producto> restantes = new ArrayList<>();
        boolean eliminado = false;

        while (!cola.estaVacia()) {
            Producto actual = cola.desencolar();
            if (!eliminado && actual.getId() != null && actual.getId().equals(productoAEliminar.getId())) {
                eliminado = true;
            } else {
                restantes.add(actual);
            }
        }

        for (Producto p : restantes) {
            cola.agregarCola(p);
        }

        cargarCarritoDesdeManager();
    }

    private void actualizarResumen(List<Producto> productos) {
        int totalProductos = productos.size();
        lblCantidadItems.setText(totalProductos + (totalProductos == 1 ? " producto" : " productos"));

        double subtotal = 0.0;
        for (Producto p : productos) {
            subtotal += p.getPrecio();
        }

        double montoDescuento = subtotal * descuentoPorcentaje;
        double totalFinal = Math.max(0.0, subtotal - montoDescuento);

        lblSubtotal.setText(String.format("$%.2f", subtotal));
        lblEnvio.setText("Gratis");
        lblTotal.setText(String.format("$%.2f", totalFinal));
    }

    @FXML
    private void onListaDeseos(ActionEvent event) {
        cargarVista("/view/ListaDeseos.fxml", event);
    }

    @FXML
    private void onMiCuenta(ActionEvent event) {
        cargarVista("/view/MiCuenta.fxml", event);
    }

    @FXML
    private void onSeguirComprando(ActionEvent event) {
        cargarVista("/view/Catalogo.fxml", event);
    }

    @FXML
    private void onAplicar(ActionEvent event) {
        String codigo = txtCodigo.getText() == null ? "" : txtCodigo.getText().trim().toUpperCase();
        if ("NEXO10".equals(codigo)) {
            descuentoPorcentaje = 0.10;
            mostrarAlerta(Alert.AlertType.INFORMATION, "Código aplicado", "Se aplicó un 10% de descuento.");
        } else {
            descuentoPorcentaje = 0.0;
            mostrarAlerta(Alert.AlertType.WARNING, "Código no válido",
                    "Ingresa un código promocional válido (prueba NEXO10).");
        }
        actualizarResumen(obtenerProductosEnCola());
    }

    @FXML
    private void onProcederPago(ActionEvent event) {
        if (tiendaManager.getCarritoCompras().estaVacia()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Carrito vacío",
                    "Agrega al menos un producto antes de proceder al pago.");
            return;
        }
        cargarVista("/view/Pago.fxml", event);
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