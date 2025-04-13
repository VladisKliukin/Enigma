package com.example.enigma.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.enigma.Enums.EWindowType;
import com.example.enigma.Managers.ManagerWindow;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

public class EncryptController extends BaseController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea ciphertextBox;

    @FXML
    private Button comebackActionButton;

    @FXML
    private Button createFileNameActionButton;

    @FXML
    private HBox createFileNameBox;

    @FXML
    private Button encryptTextActionButton;

    @FXML
    private TextArea encryptTextBox;

    @FXML
    private Label encryptionErrorText;

    @FXML
    private Label erorSupportLanguage;

    @FXML
    private Label errorSavingToFileText;

    @FXML
    private HBox keyEncryptBox;

    @FXML
    private ProgressBar progressBarEvent;

    @FXML
    private Button saveToFileActionButton;


    public EncryptController() {
        ownerWindowType = EWindowType.WINDOW_DECRYPT;
    }

    @FXML
    void initialize() {
        comebackActionButton.setOnAction(e -> {
            ManagerWindow.getInstance().switchTo(this, EWindowType.WINDOW_MAIN);
        });


    }

}






