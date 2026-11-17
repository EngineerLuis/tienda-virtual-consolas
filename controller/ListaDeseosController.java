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
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.ListaDobleEnlazada;
import model.Nodo;
import model.Producto;

public class ListaDeseosController {

    @FXML
    private TextField txtBuscar;
    @FXML
    private Hyperlink linkListaDeseos;
    @FXML
    private Button btnCarrito;
    @FXML
    private Button btnMiCuenta;

    @FXML
    private Label lblCantidad;
    @FXML
    private Label lblTotalProductos;
    @FXML
    private Label lblValorTotal;
    @FXML
    private VBox contenedorDeseos;
    @FXML
    private Button btnSeguirExplorando;

    private TiendaManager tiendaManager;

    public ListaDeseosController() {
        this.tiendaManager = TiendaManager.getInstancia();
    }

    @FXML
    public void initialize() {
        cargarListaDeseos();
    }

    private void cargarListaDeseos() {
        contenedorDeseos.getChildren().clear();
        ListaDobleEnlazada<Producto> deseos = tiendaManager.getListaDeseos();

        int cantidad = 0;
        double valorTotal = 0.0;

        Nodo<Producto> aux = deseos.getCabeza();
        while (aux != null) {
            Producto producto = aux.getDato();
            cantidad++;
            valorTotal += producto.getPrecio();

            HBox tarjeta = crearTarjetaDeseo(producto);
            contenedorDeseos.getChildren().add(tarjeta);

            aux = aux.getSiguiente();
        }

        lblCantidad.setText(cantidad + (cantidad == 1 ? " producto" : " productos"));
        lblTotalProductos.setText(String.valueOf(cantidad));
        lblValorTotal.setText(String.format("$%.2f", valorTotal));
    }

    private HBox crearTarjetaDeseo(Producto producto) {
        HBox card = new HBox(20);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setStyle("-fx-background-color: #161b22; -fx-background-radius: 12; -fx-padding: 18;");

        // Caja de categoría
        VBox boxCat = new VBox();
        boxCat.setAlignment(Pos.CENTER);
        boxCat.setPrefWidth(90);
        boxCat.setPrefHeight(70);
        boxCat.setStyle("-fx-background-color: #0d1117; -fx-background-radius: 8;");
        Label lblCat = new Label(producto.getCategoria() != null ? producto.getCategoria() : "Item");
        lblCat.setStyle("-fx-text-fill: #8b949e;");
        boxCat.getChildren().add(lblCat);

        // Información del producto
        VBox boxInfo = new VBox(4);
        HBox.setHgrow(boxInfo, Priority.ALWAYS);

        Label lblNombre = new Label(producto.getNombre());
        lblNombre.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 15px;");

        Label lblStock = new Label("Disponible · " + producto.getStock() + " en stock");
        lblStock.setStyle("-fx-text-fill: #8b949e;");

        Label lblPrecio = new Label(String.format("$%.2f", producto.getPrecio()));
        lblPrecio.setStyle("-fx-text-fill: #00d4ff; -fx-font-weight: bold; -fx-font-size: 16px;");

        boxInfo.getChildren().addAll(lblNombre, lblStock, lblPrecio);

        // Botones de acción
        VBox boxBotones = new VBox(8);
        boxBotones.setAlignment(Pos.CENTER_RIGHT);

        Button btnMover = new Button("Mover a Carrito");
        btnMover.setStyle(
                "-fx-background-color: #00d4ff; -fx-text-fill: #0d1117; -fx-font-weight: bold; -fx-background-radius: 6; -fx-cursor: hand; -fx-padding: 6 12;");
        btnMover.setOnAction(e -> {
            tiendaManager.agregarAlCarrito(producto);
            tiendaManager.eliminarDeListaDeseos(producto);
            cargarListaDeseos(); // Refrescar la vista
        });

        Button btnEliminar = new Button("Eliminar");
        btnEliminar.setStyle("-fx-background-color: transparent; -fx-text-fill: #f85149; -fx-cursor: hand;");
        btnEliminar.setOnAction(e -> {
            tiendaManager.eliminarDeListaDeseos(producto);
            cargarListaDeseos(); // Refrescar la vista
        });

        boxBotones.getChildren().addAll(btnMover, btnEliminar);

        card.getChildren().addAll(boxCat, boxInfo, boxBotones);
        return card;
    }

    @FXML
private void onListaDeseos(ActionEvent event) {
    cargarVista("/view/ListaDeseos.fxml");
}

    @FXML
    private void onCarrito(ActionEvent event) {
        cargarVista("/view/Carrito.fxml");
    }

    @FXML
    private void onMiCuenta(ActionEvent event) {
        cargarVista("/view/MiCuenta.fxml");
    }

    @FXML
    private void onSeguirExplorando(ActionEvent event) {
        cargarVista("/view/Catalogo.fxml");
    }

    private void cargarVista(String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();
            Stage stage = (Stage) btnCarrito.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}