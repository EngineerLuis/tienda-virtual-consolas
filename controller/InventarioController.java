package controller;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.*;

public class InventarioController {

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
    private Button btnAgregarProducto;

    @FXML
    private Label lblIngresos;
    @FXML
    private Label lblPedidos;
    @FXML
    private Label lblTicket;
    @FXML
    private Label lblProductos;

    @FXML
    private TableView<Producto> tablaProductos;
    @FXML
    private TableColumn<Producto, String> colId;
    @FXML
    private TableColumn<Producto, String> colProducto;
    @FXML
    private TableColumn<Producto, String> colCategoria;
    @FXML
    private TableColumn<Producto, Integer> colStock;
    @FXML
    private TableColumn<Producto, Float> colPrecio;
    @FXML
    private TableColumn<Producto, Void> colAcciones;

    private TiendaManager tiendaManager;

    @FXML
    public void initialize() {
        tiendaManager = new TiendaManager();

        // 1. Configurar las columnas de la tabla con los atributos de Producto
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProducto.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        // 2. Cargar los datos reales desde las listas enlazadas del gestor
        cargarDatosInventario();

        // 3. Configurar los botones de acción (Eliminar) en cada fila
        configurarBotonAcciones();
    }

    private void cargarDatosInventario() {
        ObservableList<Producto> listaObservable = FXCollections.observableArrayList();

        // Recorrer Consolas
        Nodo<Consola> auxC = tiendaManager.getListaConsolas().getCabeza();
        while (auxC != null) {
            listaObservable.add(auxC.getDato());
            auxC = auxC.getSiguiente();
        }

        // Recorrer Juegos
        Nodo<Juego> auxJ = tiendaManager.getListaJuegos().getCabeza();
        while (auxJ != null) {
            listaObservable.add(auxJ.getDato());
            auxJ = auxJ.getSiguiente();
        }

        // Recorrer Controles
        Nodo<Control> auxCo = tiendaManager.getListaControles().getCabeza();
        while (auxCo != null) {
            listaObservable.add(auxCo.getDato());
            auxCo = auxCo.getSiguiente();
        }

        // Recorrer Accesorios
        Nodo<Accesorios> auxA = tiendaManager.getListaAccesorios().getCabeza();
        while (auxA != null) {
            listaObservable.add(auxA.getDato());
            auxA = auxA.getSiguiente();
        }

        tablaProductos.setItems(listaObservable);
        lblProductos.setText(String.valueOf(listaObservable.size()));

        // Actualizar métricas del panel con datos reales del historial
        int totalPedidos = tiendaManager.getHistorialPedidos().getTamano();
        lblPedidos.setText(String.valueOf(totalPedidos));
    }

    private void configurarBotonAcciones() {
        colAcciones.setCellFactory(param -> new TableCell<>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnEliminar = new Button("Eliminar");

            {
                btnEditar.setStyle(
                        "-fx-background-color: #238636; -fx-text-fill: white; -fx-cursor: hand; -fx-font-weight: bold; -fx-background-radius: 4; -fx-padding: 4 8;");
                btnEditar.setOnAction(event -> {
                    Producto producto = getTableView().getItems().get(getIndex());
                    abrirFormularioEdicion(producto, event);
                });

                btnEliminar.setStyle(
                        "-fx-background-color: #da3633; -fx-text-fill: white; -fx-cursor: hand; -fx-font-weight: bold; -fx-background-radius: 4; -fx-padding: 4 8;");
                btnEliminar.setOnAction(event -> {
                    Producto producto = getTableView().getItems().get(getIndex());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmar eliminación");
                    alert.setHeaderText("¿Estás seguro de eliminar este producto?");
                    alert.setContentText(producto.getNombre() + " dejará de aparecer en el inventario.");

                    if (alert.showAndWait().get() == ButtonType.OK) {
                        if (producto instanceof Consola) {
                            tiendaManager.eliminarConsola((Consola) producto);
                        } else if (producto instanceof Juego) {
                            tiendaManager.eliminarJuego((Juego) producto);
                        } else if (producto instanceof Control) {
                            tiendaManager.eliminarControl((Control) producto);
                        } else if (producto instanceof Accesorios) {
                            tiendaManager.eliminarAccesorio((Accesorios) producto);
                        }
                        cargarDatosInventario(); // Refrescar tabla
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox container = new HBox(10, btnEditar, btnEliminar);
                    setGraphic(container);
                }
            }
        });
    }

    private void abrirFormularioEdicion(Producto producto, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AgregarProducto.fxml"));
            Parent root = loader.load();
            AgregarProductoController controller = loader.getController();
            controller.cargarProductoParaEditar(producto);

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "No se pudo abrir el formulario",
                    "Ocurrió un error al cargar el formulario de edición.");
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private void onInventario(ActionEvent event) {
        // Ya nos encontramos en la vista de Inventario
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
    private void onAgregarProducto(ActionEvent event) {
        cargarVista("/view/AgregarProducto.fxml", event);
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