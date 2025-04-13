package com.example.enigma.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.enigma.Assistants.EncryptionAssistant;
import com.example.enigma.Enums.EWindowType;
import com.example.enigma.Managers.ManagerWindow;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class EncryptController extends BaseController {
    //---------------------------------------------------------------------------------------------------------
    EncryptionAssistant encryptionAssistant;

    public EncryptController() {
        ownerWindowType = EWindowType.WINDOW_DECRYPT;
        encryptionAssistant = new EncryptionAssistant(this);
    }

    //---------------------------------------------------------------------------------------------------------
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
    private Label errorSupportLanguage;

    @FXML
    private Label errorSavingToFileText;

    @FXML
    private HBox keyEncryptBox;

    @FXML
    private ProgressBar progressBarEvent;

    @FXML
    private Button saveToFileActionButton;

    @FXML
    private TextField numberFieldBox;
    @FXML
    private Label numberFieldText;

    //---------------------------------------------------------------------------------------------------------
    @FXML
    void initialize() {
        comebackActionButton.setOnAction(e -> {
            ManagerWindow.getInstance().switchTo(this, EWindowType.WINDOW_MAIN);
        });

        encryptTextActionButton.setOnAction(e -> handleTransferText());

        numberFieldBox.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                numberFieldBox.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
    //---------------------------------------------------------------------------------------------------------

    private void handleTransferText() {

        String text = encryptionAssistant.applyEncryption(encryptTextBox.getText(), numberFieldBox.getText());
        if (text == null) {
            encryptTextBox.clear();
            return;
        }
        ciphertextBox.setText(text);
    }

    //---------------------------------------------------------------------------------------------------------
    public void hideOrShow(Label label, boolean bisVisible) {
        if (label == null) return;

        PauseTransition pause = new PauseTransition(Duration.seconds(2));

        label.setVisible(!bisVisible);
        pause.setOnFinished(e -> label.setVisible(bisVisible));

        pause.play();
    }

    //---------------------------------------------------------------------------------------------------------

    public Label getErrorSupportLanguage() {
        return errorSupportLanguage;
    }
    //---------------------------------------------------------------------------------------------------------

    public void flashLabelColor() {
        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
        numberFieldText.setTextFill(Color.RED);
        pause.setOnFinished(e -> numberFieldText.setTextFill(Color.rgb(191, 191, 191)));
        pause.play();
    }
    //---------------------------------------------------------------------------------------------------------

}






