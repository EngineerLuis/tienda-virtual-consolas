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
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import model.Usuario;

public class MiCuentaController {

    @FXML
    private Button btnInformacion;
    @FXML
    private Button btnCarrito;
    @FXML
    private Button btnListaDeseos;
    @FXML
    private Button btnMisPedidos;
    @FXML
    private Button btnCerrarSesion;
    @FXML
    private Hyperlink linkVolver;

    private TiendaManager tiendaManager;

    @FXML
    public void initialize() {
        tiendaManager = TiendaManager.getInstancia();
    }

    @FXML
    private void onInformacion(ActionEvent event) {
        Usuario usuario = tiendaManager.getUsuarioActual();
        if (usuario == null) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Información personal", "No hay una sesión activa.");
            return;
        }

        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Información Personal - Nexo");
        dialog.initOwner(((javafx.scene.Node) event.getSource()).getScene().getWindow());

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.setStyle("-fx-background-color: #0d1117; -fx-border-color: #30363d; -fx-border-width: 1;");
        dialogPane.getButtonTypes().add(ButtonType.CLOSE);

        Button btnCerrar = (Button) dialogPane.lookupButton(ButtonType.CLOSE);
        btnCerrar.setText("Cerrar");
        btnCerrar.setStyle("-fx-background-color: #00d4ff; -fx-text-fill: #0d1117; -fx-font-weight: bold; -fx-background-radius: 6; -fx-cursor: hand; -fx-padding: 6 18;");

        // Contenedor principal tipo tarjeta
        VBox tarjeta = new VBox(18);
        tarjeta.setPrefWidth(360);
        tarjeta.setPadding(new Insets(22));
        tarjeta.setStyle("-fx-background-color: #161b22; -fx-background-radius: 12; -fx-border-color: #30363d; -fx-border-radius: 12;");

        // Encabezado con avatar circular (inicial del usuario)
        String inicial = (usuario.getNombre() != null && !usuario.getNombre().isEmpty())
                ? usuario.getNombre().substring(0, 1).toUpperCase()
                : "U";

        Label lblInicial = new Label(inicial);
        lblInicial.setStyle("-fx-text-fill: #0d1117; -fx-font-weight: bold; -fx-font-size: 20;");

        StackPane avatar = new StackPane(new Circle(26, javafx.scene.paint.Color.web("#00d4ff")), lblInicial);

        Label lblNombreHeader = new Label(usuario.getNombre());
        lblNombreHeader.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16;");

        Label lblRol = new Label(usuario.getCorreo().equalsIgnoreCase("admin@tienda.com") ? "Administrador" : "Cliente Nexo");
        lblRol.setStyle("-fx-text-fill: #00d4ff; -fx-font-size: 12;");

        VBox datosHeader = new VBox(3, lblNombreHeader, lblRol);
        datosHeader.setAlignment(Pos.CENTER_LEFT);

        HBox header = new HBox(14, avatar, datosHeader);
        header.setAlignment(Pos.CENTER_LEFT);

        Separator separador = new Separator();
        separador.setStyle("-fx-opacity: 0.2;");

        // Filas de datos
        VBox listaDatos = new VBox(12);
        listaDatos.getChildren().addAll(
            crearFilaDato("Nombre completo", usuario.getNombre()),
            crearFilaDato("Correo electrónico", usuario.getCorreo()),
            crearFilaDato("Pedidos realizados", String.valueOf(usuario.getCantidadPedidos())),
            crearFilaDato("Estado de la cuenta", "Activa")
        );

        tarjeta.getChildren().addAll(header, separador, listaDatos);
        StackPane contenidoCentrado = new StackPane(tarjeta);
        contenidoCentrado.setAlignment(Pos.CENTER);
        dialogPane.setContent(contenidoCentrado);

        dialog.showAndWait();
    }

    private HBox crearFilaDato(String etiqueta, String valor) {
        HBox fila = new HBox(10);
        fila.setAlignment(Pos.CENTER_LEFT);
        fila.setStyle("-fx-background-color: #0d1117; -fx-background-radius: 8; -fx-padding: 10 14;");

        Label lblEtiqueta = new Label(etiqueta);
        lblEtiqueta.setStyle("-fx-text-fill: #8b949e; -fx-font-size: 12;");

        Region espacio = new Region();
        HBox.setHgrow(espacio, Priority.ALWAYS);

        Label lblValor = new Label(valor != null ? valor : "-");
        lblValor.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13;");
        if ("Activa".equals(valor)) {
            lblValor.setStyle("-fx-text-fill: #3fb950; -fx-font-weight: bold; -fx-font-size: 13;");
        }

        fila.getChildren().addAll(lblEtiqueta, espacio, lblValor);
        return fila;
    }

    @FXML
    private void onCarrito(ActionEvent event) {
        cargarVista("/view/Carrito.fxml", event);
    }

    @FXML
    private void onListaDeseos(ActionEvent event) {
        cargarVista("/view/ListaDeseos.fxml", event);
    }

    @FXML
    private void onMisPedidos(ActionEvent event) {
        cargarVista("/view/MisPedidos.fxml", event);
    }

    @FXML
    private void onCerrarSesion(ActionEvent event) {
        tiendaManager.cerrarSesion();
        cargarVista("/view/Login.fxml", event);
    }

    @FXML
    private void onVolver(ActionEvent event) {
        cargarVista("/view/Catalogo.fxml", event);
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