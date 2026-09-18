package ni.edu.uam.eventosynavegacion_caso2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistroApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RegistroApplication.class.getResource("/ni/edu/uam/eventosynavegacion_caso2/fxml/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Registro!/");
        stage.setScene(scene);
        stage.show();
    }
}
