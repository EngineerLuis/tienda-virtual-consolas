// Clase principal que configura y muestra la ventana inicial de JavaFX.
// Este archivo forma parte de la aplicación de tienda virtual.

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));

            primaryStage.setTitle("Nexo - Videojuegos y Tecnología");
            primaryStage.setScene(new Scene(root, 1100, 700));
            primaryStage.setResizable(false);
            primaryStage.centerOnScreen();
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
