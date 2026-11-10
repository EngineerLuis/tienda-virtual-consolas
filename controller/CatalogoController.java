/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;

public class CatalogoController {

    @FXML
    private TextField txtBuscar;
    @FXML
    private Hyperlink linkListaDeseos;
    @FXML
    private Button btnCarrito;
    @FXML
    private Button btnMiCuenta;
    @FXML
    private Button btnDestacado;
    @FXML
    private ComboBox<String> cmbCategoria;
    @FXML
    private ComboBox<String> cmbOrdenar;
    @FXML
    private FlowPane flowProductos;

    private TiendaManager tiendaManager;

    @FXML
    public void initialize() {
        tiendaManager = new TiendaManager();

        cmbCategoria.getItems().addAll("Todas", "Consolas", "Juegos", "Accesorios");
        cmbCategoria.setValue("Todas");

        cmbOrdenar.getItems().addAll("Precio: menor a mayor", "Precio: mayor a menor", "Nombre A-Z");

        // Cargar los productos reales desde el inventario del TiendaManager
        filtrarProductos("", "Todas");

        // Opcional: Filtrar en tiempo real al escribir en la barra de búsqueda
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarProductos(newValue, cmbCategoria.getValue());
        });

        // Filtrar al cambiar de categoría
        cmbCategoria.setOnAction(e -> {
            filtrarProductos(txtBuscar.getText(), cmbCategoria.getValue());
        });

        cmbOrdenar.setOnAction(e -> {
            filtrarProductos(txtBuscar.getText(), cmbCategoria.getValue());
        });
    }

    private void filtrarProductos(String busqueda, String categoria) {
        flowProductos.getChildren().clear();

        List<Producto> productosFiltrados = new ArrayList<>();
        ListaDobleEnlazada<Producto> lista = tiendaManager.getListaProductos();
        Nodo<Producto> aux = lista.getCabeza();

        while (aux != null) {
            Producto p = aux.getDato();
            String nombre = p.getNombre() == null ? "" : p.getNombre();
            boolean coincideNombre = busqueda == null || busqueda.isEmpty()
                    || nombre.toLowerCase(Locale.ROOT).contains(busqueda.toLowerCase(Locale.ROOT));
            boolean coincideCategoria = categoria == null || categoria.equals("Todas")
                    || (p.getCategoria() != null && p.getCategoria().equalsIgnoreCase(categoria));

            if (coincideNombre && coincideCategoria) {
                productosFiltrados.add(p);
            }
            aux = aux.getSiguiente();
        }

        String orden = cmbOrdenar.getValue();
        if ("Precio: menor a mayor".equals(orden)) {
            productosFiltrados.sort(Comparator.comparingDouble(Producto::getPrecio));
        } else if ("Precio: mayor a menor".equals(orden)) {
            productosFiltrados.sort(Comparator.comparingDouble(Producto::getPrecio).reversed());
        } else if ("Nombre A-Z".equals(orden)) {
            productosFiltrados.sort(Comparator.comparing(
                    producto -> producto.getNombre() == null ? "" : producto.getNombre(),
                    String.CASE_INSENSITIVE_ORDER));
        }

        for (Producto producto : productosFiltrados) {
            flowProductos.getChildren().add(crearTarjetaProducto(producto));
        }
    }

    // Método auxiliar para generar dinámicamente la tarjeta visual de cada producto
    // en el catálogo
    private VBox crearTarjetaProducto(Producto producto) {
        VBox card = new VBox(8);
        card.setPrefSize(220, 390); // Ajustamos la altura para que entren bien las especificaciones
        card.setStyle(
                "-fx-background-color: #161b22; -fx-background-radius: 10; -fx-padding: 15; -fx-border-color: #30363d; -fx-border-radius: 10;");
        card.setAlignment(Pos.TOP_LEFT);

        // 1. Imagen del producto
        ImageView imageView = new ImageView();
        imageView.setFitWidth(190);
        imageView.setFitHeight(110);
        imageView.setPreserveRatio(true);
        imageView.setStyle("-fx-background-color: #21262d; -fx-background-radius: 6;");

        try {
            if (producto.getRutaImagen() != null && !producto.getRutaImagen().isEmpty()) {
                String nombreImg = producto.getRutaImagen().trim();
                Image img = null;

                // Rutas de prueba (añadimos "images" en inglés)
                String[] rutasPrueba = {
                        "file:data/images/" + nombreImg,
                        "file:data/imagenes/" + nombreImg,
                        "file:data/imagen/" + nombreImg,
                        "file:data/" + nombreImg,
                        "file:" + nombreImg,
                        "data/images/" + nombreImg,
                        "data/imagenes/" + nombreImg,
                        "data/imagen/" + nombreImg,
                        nombreImg
                };

                for (String ruta : rutasPrueba) {
                    try {
                        img = new Image(ruta, true);
                        if (img != null && !img.isError()) {
                            break;
                        }
                    } catch (Exception ignored) {
                    }
                }

                if (img != null && !img.isError()) {
                    imageView.setImage(img);
                }
            }
        } catch (Exception e) {
            System.out.println("Error cargando imagen para: " + producto.getNombre());
        }

        // 2. Información general
        Label lblNombre = new Label(producto.getNombre());
        lblNombre.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");
        lblNombre.setWrapText(true);

        Label lblCat = new Label(
                "Categoría: " + (producto.getCategoria() != null ? producto.getCategoria() : "General"));
        lblCat.setStyle("-fx-text-fill: #8b949e; -fx-font-size: 11px;");

        // 3. Especificaciones dinámicas según el tipo de producto
        StringBuilder specs = new StringBuilder();
        if (producto instanceof Consola) {
            Consola c = (Consola) producto;
            if (c.getProcesador() != null && !c.getProcesador().isEmpty())
                specs.append("• Proc: ").append(c.getProcesador()).append("\n");
            if (c.getAlmacenamiento() != null && !c.getAlmacenamiento().isEmpty())
                specs.append("• Almacén: ").append(c.getAlmacenamiento()).append("\n");
            if (c.getResolucion() != null && !c.getResolucion().isEmpty())
                specs.append("• Res: ").append(c.getResolucion()).append("\n");
        } else if (producto instanceof Juego) {
            Juego j = (Juego) producto;
            if (j.getResolucion() != null && !j.getResolucion().isEmpty())
                specs.append("• Res: ").append(j.getResolucion()).append("\n");
        } else if (producto instanceof Control) {
            Control co = (Control) producto;
            if (co.getConectividad() != null && !co.getConectividad().isEmpty())
                specs.append("• Conectividad: ").append(co.getConectividad()).append("\n");
        } else if (producto instanceof Accesorios) {
            Accesorios ac = (Accesorios) producto;
            if (ac.getConectividad() != null && !ac.getConectividad().isEmpty())
                specs.append("• Conectividad: ").append(ac.getConectividad()).append("\n");
        }

        Label lblSpecs = new Label(specs.toString().trim());
        lblSpecs.setStyle("-fx-text-fill: #8b949e; -fx-font-size: 11px;");
        lblSpecs.setWrapText(true);

        // 4. Precio y Stock
        Label lblPrecio = new Label(String.format("$%.2f", producto.getPrecio()));
        lblPrecio.setStyle("-fx-text-fill: #00d4ff; -fx-font-weight: bold; -fx-font-size: 15px;");

        Label lblStock = new Label("Stock: " + producto.getStock());
        lblStock.setStyle("-fx-text-fill: #c9d1d9; -fx-font-size: 11px;");

        // 5. Botón Agregar
        Button btnAgregar = new Button("Añadir al carrito");
        btnAgregar.setMaxWidth(Double.MAX_VALUE);
        btnAgregar.setStyle(
                "-fx-background-color: #238636; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 6; -fx-cursor: hand;");

        btnAgregar.setOnAction(e -> {
            tiendaManager.agregarAlCarrito(producto);
            System.out.println("Producto añadido al carrito: " + producto.getNombre());
        });

        // Añadir elementos a la tarjeta en orden
        card.getChildren().addAll(imageView, lblNombre, lblCat);
        if (!specs.toString().isEmpty()) {
            card.getChildren().add(lblSpecs);
        }
        card.getChildren().addAll(lblPrecio, lblStock, btnAgregar);

        return card;
    }

    @FXML
    private void onListaDeseos(ActionEvent event) {
        cargarVista("/view/ListaDeseos.fxml", event);
    }

    @FXML
    private void onCarrito(ActionEvent event) {
        cargarVista("/view/Carrito.fxml", event);
    }

    @FXML
    private void onMiCuenta(ActionEvent event) {
        cargarVista("/view/MiCuenta.fxml", event);
    }

    @FXML
    private void onDestacado(ActionEvent event) {
        cargarVista("/view/DetalleProducto.fxml", event);
    }

    private void cargarVista(String rutaFXML, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}