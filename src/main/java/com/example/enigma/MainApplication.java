package com.example.enigma;

import com.example.enigma.Managers.ManagerWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Encrypt-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
        stage.setTitle("E N I G M A");
        stage.setScene(scene);
        stage.show();
        ManagerWindow.getInstance().setPrimaryStage(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}