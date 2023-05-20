package eformer.front.eformer_frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        /* Patches the illegalAccess issues of jfoenix */
        org.burningwave.core.assembler.StaticComponentContainer.Modules.exportAllToAll();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/Dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Eformer");
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setFullScreenExitHint("");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}