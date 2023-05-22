package eformer.front.eformer_frontend;

import eformer.front.eformer_frontend.connector.ItemsConnector;
import eformer.front.eformer_frontend.connector.RequestsGateway;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    public static Image getImage(String name) {
        var result = Objects.requireNonNull(Main.class.getResource("/images/" + name)).getPath();
        var file = new File(result).getAbsolutePath().replace("%20", " ");
        return new Image(file);
    }

    @Override
    public void start(Stage stage) throws IOException {
        RequestsGateway.authenticate("yolo", "ysd112233");

        if (RequestsGateway.getToken() == null) {
            return;
        }

        /* Patches the illegalAccess issues of jfoenix */
        org.burningwave.core.assembler.StaticComponentContainer.Modules.exportAllToAll();

        stage.getIcons().clear();
        stage.getIcons().add(getImage("cart.png"));
        stage.setFullScreenExitHint("");
        stage.setTitle("eFormer");

        Splash splash = new Splash();
        splash.show();
        stage.setScene(splash.getSplashScene());

        splash.getSequentialTransition().setOnFinished(e -> {
            Timeline timeline = new Timeline();
            KeyFrame key = new KeyFrame(Duration.millis(800),
                    new KeyValue(splash.getSplashScene().getRoot().opacityProperty(), 0));
            timeline.getKeyFrames().add(key);
            timeline.setOnFinished((event) -> {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/views/Dashboard.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());

                    scene.addEventFilter(KeyEvent.KEY_PRESSED, ke -> {
                        if (ke.getCode() == KeyCode.ESCAPE) {
                            System.exit(0);
                        }
                    });

                    stage.setScene(scene);
                    stage.setResizable(true);
                    stage.setMaximized(true);
                }
                catch (IOException ex) {
                    RequestsGateway.displayException(ex);
                }
            });
            timeline.play();
        });

        stage.setWidth(450);
        stage.setHeight(280);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}