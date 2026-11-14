//
 // Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 // Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 //
// Controlador de la vista para crear y editar productos del inventario.
// Este archivo forma parte de la aplicación de tienda virtual.

package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.*;

public class AgregarProductoController {

    @FXML
    private Button btnInventario;
    @FXML
    private Button btnVentas;
    @FXML
    private Button btnClientes;
    @FXML
    private Button btnConfiguracion;
    @FXML
    private Button btnVolverTienda;

    @FXML
    private TextField txtNombre;
    @FXML
    private ComboBox<String> cmbCategoria;
    @FXML
    private TextArea txtDescripcion;
    @FXML
    private TextField txtPrecio;
    @FXML
    private TextField txtStock;
    @FXML
    private Label lblNombreImagen;
    @FXML
    private Button btnSeleccionarImagen;

    @FXML
    private TextField txtProcesador;
    @FXML
    private TextField txtAlmacenamiento;
    @FXML
    private TextField txtResolucion;
    @FXML
    private TextField txtConectividad;

    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGuardar;

    private TiendaManager tiendaManager;
    private String rutaImagenSeleccionada = "default.png";
    private Producto productoEditar;

    @FXML
    public void initialize() {
        tiendaManager = new TiendaManager();
        cmbCategoria.getItems().addAll("Consolas", "Juegos", "Accesorios", "Controles");
    }

    public void cargarProductoParaEditar(Producto producto) {
        productoEditar = producto;
        txtNombre.setText(producto.getNombre());
        cmbCategoria.setValue(producto.getCategoria());
        txtDescripcion.setText(producto.getDescripcion());
        txtPrecio.setText(String.valueOf(producto.getPrecio()));
        txtStock.setText(String.valueOf(producto.getStock()));
        rutaImagenSeleccionada = producto.getRutaImagen();
        lblNombreImagen.setText(rutaImagenSeleccionada);

        if (producto instanceof Consola) {
            Consola consola = (Consola) producto;
            txtProcesador.setText(consola.getProcesador());
            txtAlmacenamiento.setText(consola.getAlmacenamiento());
            txtResolucion.setText(consola.getResolucion());
        } else if (producto instanceof Juego) {
            txtResolucion.setText(((Juego) producto).getResolucion());
        } else if (producto instanceof Control) {
            txtConectividad.setText(((Control) producto).getConectividad());
        } else if (producto instanceof Accesorios) {
            txtConectividad.setText(((Accesorios) producto).getConectividad());
        }

        btnGuardar.setText("Guardar cambios");
    }

    @FXML
    private void onInventario(ActionEvent event) {
        cargarVista("/view/Inventario.fxml", event);
    }

    @FXML
    private void onVentas(ActionEvent event) {
        cargarVista("/view/Ventas.fxml", event);
    }

    @FXML
    private void onClientes(ActionEvent event) {
        cargarVista("/view/Clientes.fxml", event);
    }

    @FXML
    private void onConfiguracion(ActionEvent event) {
        cargarVista("/view/Configuracion.fxml", event);
    }

    @FXML
    private void onVolverTienda(ActionEvent event) {
        cargarVista("/view/Catalogo.fxml", event);
    }

    @FXML
    private void onSeleccionarImagen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen del Producto");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));

        Stage stage = (Stage) btnSeleccionarImagen.getScene().getWindow();
        File archivoSeleccionado = fileChooser.showOpenDialog(stage);

        if (archivoSeleccionado != null) {
            try {
                Path carpetaImagenes = Paths.get("data", "images");
                Files.createDirectories(carpetaImagenes);

                Path origen = archivoSeleccionado.toPath().toAbsolutePath().normalize();
                Path destino = carpetaImagenes.resolve(archivoSeleccionado.getName()).toAbsolutePath().normalize();
                if (!origen.equals(destino)) {
                    Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
                }

                rutaImagenSeleccionada = archivoSeleccionado.getName();
                lblNombreImagen.setText(rutaImagenSeleccionada);
            } catch (IOException e) {
                mostrarAlerta(Alert.AlertType.ERROR, "No se pudo copiar la imagen",
                        "La imagen no pudo guardarse en la carpeta de imágenes.");
            }
        }
    }

    @FXML
    private void onCancelar(ActionEvent event) {
        cargarVista("/view/Inventario.fxml", event);
    }

    @FXML
    private void onGuardar(ActionEvent event) {
        String nombre = txtNombre.getText().trim();
        String categoria = cmbCategoria.getValue();
        String descripcion = txtDescripcion.getText().trim();
        String precioStr = txtPrecio.getText().trim();
        String stockStr = txtStock.getText().trim();

        // Validar campos obligatorios
        if (nombre.isEmpty() || precioStr.isEmpty() || stockStr.isEmpty() || categoria == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos",
                    "Por favor completa los campos obligatorios (*).");
            return;
        }

        float precio;
        int stock;
        try {
            precio = Float.parseFloat(precioStr);
            stock = Integer.parseInt(stockStr);
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Formato inválido",
                    "El precio y el stock deben ser valores numéricos válidos.");
            return;
        }

        if (precio <= 0 || stock < 0) {
            mostrarAlerta(Alert.AlertType.WARNING, "Valores inválidos",
                    "El precio debe ser mayor que cero y el stock no puede ser negativo.");
            return;
        }

        if (productoEditar != null) {
            if (!productoEditar.getCategoria().equals(categoria)) {
                mostrarAlerta(Alert.AlertType.WARNING, "Categoría no modificable",
                        "La categoría no puede cambiarse al editar un producto.");
                return;
            }

            Producto actualizado = crearProductoConDatos(productoEditar.getId(), nombre, categoria, stock, precio,
                    descripcion);
            if (tiendaManager.actualizarProducto(actualizado)) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito",
                        "El producto se ha actualizado y guardado correctamente.");
                cargarVista("/view/Inventario.fxml", event);
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "No se pudo actualizar",
                        "No se encontró el producto seleccionado.");
            }
            return;
        }

        String id = "PROD-" + System.currentTimeMillis() % 10000;

        // Crear y agregar el producto según la categoría seleccionada.
        switch (categoria) {
            case "Consolas":
                Consola nuevaConsola = new Consola(id, nombre, categoria, stock, precio, descripcion,
                        rutaImagenSeleccionada,
                        txtProcesador.getText(), txtAlmacenamiento.getText(), txtResolucion.getText());
                tiendaManager.agregarConsola(nuevaConsola);
                break;

            case "Juegos":
                Juego nuevoJuego = new Juego(id, nombre, categoria, stock, precio, descripcion, rutaImagenSeleccionada,
                        txtResolucion.getText()); // Usando resolución como plataforma/formato o género si aplica
                tiendaManager.agregarJuego(nuevoJuego);
                break;

            case "Controles":
                Control nuevoControl = new Control(id, nombre, categoria, stock, precio, descripcion,
                        rutaImagenSeleccionada,
                        txtConectividad.getText());
                tiendaManager.agregarControl(nuevoControl);
                break;

            case "Accesorios":
                Accesorios nuevoAccesorio = new Accesorios(id, nombre, categoria, stock, precio, descripcion,
                        rutaImagenSeleccionada,
                        txtConectividad.getText());
                tiendaManager.agregarAccesorio(nuevoAccesorio);
                break;
            default:
                break;
        }

        mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito",
                "El producto se ha agregado y guardado correctamente en el inventario.");
        cargarVista("/view/Inventario.fxml", event);
    }

    private Producto crearProductoConDatos(String id, String nombre, String categoria, int stock, float precio,
            String descripcion) {
        if (productoEditar instanceof Consola) {
            return new Consola(id, nombre, categoria, stock, precio, descripcion, rutaImagenSeleccionada,
                    txtProcesador.getText(), txtAlmacenamiento.getText(), txtResolucion.getText());
        }
        if (productoEditar instanceof Juego) {
            return new Juego(id, nombre, categoria, stock, precio, descripcion, rutaImagenSeleccionada,
                    txtResolucion.getText());
        }
        if (productoEditar instanceof Control) {
            return new Control(id, nombre, categoria, stock, precio, descripcion, rutaImagenSeleccionada,
                    txtConectividad.getText());
        }
        return new Accesorios(id, nombre, categoria, stock, precio, descripcion, rutaImagenSeleccionada,
                txtConectividad.getText());
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
