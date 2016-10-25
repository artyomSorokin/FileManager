package net.sorokin;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.sorokin.FileManager;

import java.net.UnknownHostException;

public class Main extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {

        stage.setTitle("File Manager");
        Scene scene = new Scene(new Group(), 400, 500);


        Group root = (Group) scene.getRoot();
        try {
            root.getChildren().add(new FileManager(scene).getPane());
        } catch (UnknownHostException e) {
            System.err.print("couldn't get hostname");
            e.printStackTrace();
        }
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
        stage.sizeToScene();
    }
}
