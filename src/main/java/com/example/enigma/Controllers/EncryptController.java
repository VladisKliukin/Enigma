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
import org.fxmisc.richtext.StyleClassedTextArea;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

public class EncryptController extends BaseController {
    //---------------------------------------------------------------------------------------------------------
    EncryptionAssistant encryptionAssistant;

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
            flashLabelColor();
            bIsValidOperation = false;
            return;
        }

        if (bIsValidOperation) {
            String ciphertextText = encryptionAssistant.applyEncryption(encryptText, shift);

            ciphertextBox.setText(ciphertextText);
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

    public void flashLabelColor() {
        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
        numberFieldText.setTextFill(Color.RED);
        pause.setOnFinished(e -> numberFieldText.setTextFill(Color.rgb(191, 191, 191)));
        pause.play();
    }

    //---------------------------------------------------------------------------------------------------------
    public void clearEncryptTextBox() {
        encryptTextBox.clear();
    }
    //---------------------------------------------------------------------------------------------------------

}






