package htl.sigma;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        HBox root = new HBox();

        root.setPrefSize(340,70);
        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);

        // xButton anlegen
        Button btUntis = new Button();
        Button btMoodle = new Button();
        Button btMail = new Button();
        Button btWiki = new Button();
        Button btExit = new Button();

        root.getChildren().add(btUntis);
        root.getChildren().add(btMoodle);
        root.getChildren().add(btMail);
        root.getChildren().add(btWiki);
        root.getChildren().add(btExit);

        btUntis.setPrefSize(64,64);
        btMoodle.setPrefSize(64,64);
        btMail.setPrefSize(64,64);
        btWiki.setPrefSize(64,64);
        btExit.setPrefSize(64,64);

        btUntis.getStyleClass().add("bt-untis");
        btMoodle.getStyleClass().add("bt-moodle");
        btMail.getStyleClass().add("bt-Mail");
        btWiki.getStyleClass().add("bt-Wiki");
        btExit.getStyleClass().add("bt-Exit");

        btMoodle.setOnAction(e -> {
           getHostServices().showDocument("https://edufs.edu.htl-leonding.ac.at/moodle/");
        });
        btUntis.setOnAction(e -> {
            getHostServices().showDocument("https://mese.webuntis.com/WebUntis/?school=htbla%20linz%20leonding#/basic/login");
        });
        btMail.setOnAction(e -> {
            getHostServices().showDocument("https://outlook.office.com/mail/");
        });
        btWiki.setOnAction(e -> {
            getHostServices().showDocument("https://leowiki.htl-leonding.ac.at/doku.php?id=start");
        });
        btExit.setOnAction(e -> {
            System.exit(0);
        });



        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}