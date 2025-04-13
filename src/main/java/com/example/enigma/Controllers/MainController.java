package com.example.enigma.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.enigma.Enums.EWindowType;
import com.example.enigma.Managers.ManagerWindow;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainController extends BaseController {

    public MainController() {
ownerWindowType = EWindowType.WINDOW_MAIN;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button comebackActionButton;

    @FXML
    private Button decryptActionButton;

    @FXML
    private Button encryptActionButton;

    @FXML
    void initialize() {
        initOwnerStage(decryptActionButton);
        decryptActionButton.setOnAction(e -> {
            ManagerWindow.getInstance().openWindow(this, EWindowType.WINDOW_DECRYPT);
        });

    }

}
