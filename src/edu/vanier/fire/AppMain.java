package edu.vanier.fire;

import edu.vanier.fire.ctrl.WelcomePageController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AppMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        String version = System.getProperties().get("javafx.runtime.version").toString();
        int parsedVersion = Integer.parseInt(version.split("\\.")[0]);
        int subVersion = Integer.parseInt(version.split("\\.")[2].split("-")[0]);
        if (parsedVersion < 8 || (subVersion < 221) && parsedVersion == 8) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR!");
            alert.setHeaderText(null);
            alert.setContentText("You have a non compatible version of JavaFX\n"
                    + "This program requires a version 8.0.221 or higher\n"
                    + "You currently have version: " + version);

            alert.showAndWait();
            Platform.exit();
        }

        Loader loader = new Loader();
        loader.setAsWelcomePage();
        Parent root = loader.getLoader().load();
        ((WelcomePageController) loader.getLoader().getController()).setStage(stage);

        Scene scene = new Scene(root);

        stage.setTitle("Fire Watcher");
        Image icon = new Image(getClass().getResourceAsStream("fxml/data/fireIcon.png"));
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
