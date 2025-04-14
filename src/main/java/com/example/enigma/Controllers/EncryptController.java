package com.example.enigma.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.enigma.Assistants.EncryptionAssistant;
import com.example.enigma.Enums.EWindowType;
import com.example.enigma.Managers.ManagerWindow;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class EncryptController extends BaseController {
    //---------------------------------------------------------------------------------------------------------
    EncryptionAssistant encryptionAssistant;
    boolean bIsEncryptionCreated = false;

    public EncryptController() {
        ownerWindowType = EWindowType.WINDOW_DECRYPT;
        encryptionAssistant = new EncryptionAssistant();
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
    private TextField keyEncryptText;
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
        keyEncryptBox.setVisible(false);

        comebackActionButton.setOnAction(e -> {
            ManagerWindow.getInstance().switchTo(this, EWindowType.WINDOW_MAIN);
        });

        encryptTextActionButton.setOnAction(e -> handleEventButtonEncrypt());

        numberFieldBox.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                numberFieldBox.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
    //---------------------------------------------------------------------------------------------------------

    private void handleEventButtonEncrypt() {

        String encryptText = encryptTextBox.getText();
        int shift = encryptionAssistant.isShiftValid(numberFieldBox.getText());
        boolean bIsValidOperation = true;


        if (!encryptionAssistant.isEnglishText(encryptText)) {
            hideOrShow(errorSupportLanguage);
            bIsValidOperation = false;
        }

        if (shift == 0) {
            errorSignalNumberFieldText();
            return;
        }

        if (bIsValidOperation && !bIsEncryptionCreated) {
            // bIsEncryptionCreated = true;
            String ciphertextText = encryptionAssistant.applyEncryption(encryptText, shift);

            startActionEncryption(ciphertextText,5);
        }
    }

    //---------------------------------------------------------------------------------------------------------
    public void hideOrShow(Label label) {
        if (label == null) return;

        PauseTransition pause = new PauseTransition(Duration.seconds(2));

        label.setVisible(true);
        pause.setOnFinished(e -> label.setVisible(false));

        pause.play();
    }
    //---------------------------------------------------------------------------------------------------------

    public void errorSignalNumberFieldText() {
        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
        numberFieldText.setTextFill(Color.RED);
        pause.setOnFinished(e -> numberFieldText.setTextFill(Color.rgb(191, 191, 191)));
        pause.play();
    }

    //---------------------------------------------------------------------------------------------------------
    public void handleEncryptionKey() {
        keyEncryptBox.setOpacity(0);
        keyEncryptBox.setVisible(true);
        keyEncryptText.setText(encryptionAssistant.createEncryptionKey());

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(keyEncryptBox.opacityProperty(), 0)),
                new KeyFrame(Duration.seconds(.5), new KeyValue(keyEncryptBox.opacityProperty(), 1))
        );
        timeline.play();
    }
    //---------------------------------------------------------------------------------------------------------

    private void handleCiphertextText(String text,double delay) {

        ciphertextBox.setText(text);

        Timeline timeline = new Timeline();

        StringBuilder currentText = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            int index = i;

            KeyFrame keyFrame = new KeyFrame(
                    Duration.millis(delay).multiply(i),
                    event -> {
                        currentText.append(text.charAt(index));
                        ciphertextBox.setText(currentText.toString());
                    }
            );

            timeline.getKeyFrames().add(keyFrame);
        }

        timeline.play();

    }
    //---------------------------------------------------------------------------------------------------------

    private void startActionEncryption(String text,double delay) {

        progressBarEvent.setProgress(0);
        progressBarEvent.setVisible(true);

        int length = text.length();
        double totalMillis = length * delay;
        Duration duration = Duration.millis(totalMillis);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(progressBarEvent.progressProperty(), 0)),
                new KeyFrame(duration, new KeyValue(progressBarEvent.progressProperty(), 1))
        );
            handleCiphertextText(text,delay);
        timeline.setOnFinished(e -> {

            progressBarEvent.setVisible(false);
            handleEncryptionKey();
        });

        timeline.play();
    }
}







