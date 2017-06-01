package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("terminal.fxml"));
        primaryStage.setTitle("AutoModel");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            exit();
        });
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void exit() {
        int close = JOptionPane.showConfirmDialog(null, "Close AutoModel?", "Close",
                JOptionPane.YES_NO_OPTION);
        if (close == JOptionPane.YES_OPTION){
            Platform.exit();
        }
    }
}
