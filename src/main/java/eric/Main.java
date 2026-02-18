package eric;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private Eric eric = new Eric();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setTitle("EricBot");
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setEric(eric);
            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("EricBot - Startup Error");
            alert.setHeaderText("Unable to start the application");
            alert.setContentText("The UI resources could not be loaded. Please check the app installation.");
            alert.showAndWait();
            Platform.exit();
        }
    }
}
