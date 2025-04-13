package com.example.enigma.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.enigma.Enums.EWindowType;
import com.example.enigma.Managers.ManagerWindow;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class DecryptController extends BaseController {

    public DecryptController() {
        ownerWindowType = EWindowType.WINDOW_DECRYPT;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button comebackActionButton;

    @FXML
    void initialize() {
        comebackActionButton.setOnAction(e -> {
            ManagerWindow.getInstance().switchTo(this,EWindowType.WINDOW_MAIN);
        });

    }

}
