package com.example.enigma.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.enigma.Assistants.DecryptAssistant;
import com.example.enigma.Enums.EWindowType;
import com.example.enigma.Managers.ManagerWindow;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class DecryptController extends BaseController {
    DecryptAssistant decryptAssistant;

    public DecryptController() {
        ownerWindowType = EWindowType.WINDOW_DECRYPT;
        decryptAssistant = new DecryptAssistant();
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button comebackActionButton;

    @FXML
    private Button createFileNameActionButton;

    @FXML
    private HBox createFileNameBox;

    @FXML
    private Button decryptTextActionButton;

    @FXML
    private TextArea encryptedTextBox;

    @FXML
    private TextArea decryptedTextBox;

    @FXML
    private Label errorSavingToFileText;

    @FXML
    private Label errorSupportLanguage;

    @FXML
    private TextField keyDecryptionText;

    @FXML
    private HBox keyEncryptBox;

    @FXML
    private Button openFileActionButton;

    @FXML
    private ProgressBar progressBarEvent;

    @FXML
    void initialize() {
        comebackActionButton.setOnAction(e -> ManagerWindow.getInstance().switchTo(this, EWindowType.WINDOW_MAIN));

        decryptTextActionButton.setOnAction(e -> handleEventButtonDecrypt());
    }

    private void handleEventButtonDecrypt() {
        String encryptedText = encryptedTextBox.getText();
        String encryptedKey = keyDecryptionText.getText();
        String decryptedText = decryptAssistant.applyDecryptionText(encryptedText, encryptedKey);
        decryptedTextBox.setText(decryptedText);
    }

}
