package com.example.enigma.Assistants;

import com.example.enigma.Controllers.EncryptController;

public class EncryptionAssistant {
    EncryptController controller;

    public EncryptionAssistant(EncryptController controller) {
        this.controller = controller;
    }
    //---------------------------------------------------------------------------------------------------------

    public boolean isEnglishText(String text) {
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                if (!((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))) {
                    return false;
                }
            }
        }
        return true;
    }

    //---------------------------------------------------------------------------------------------------------
    public String applyEncryption(String text, String shiftText) {

        int shift = isShiftValid(shiftText);
        if (shift == 0) {
            controller.flashLabelColor();
            return null;
        }

        if (!isEnglishText(text)) {
            controller.hideOrShow(controller.getErrorSupportLanguage(), isEnglishText(text));
            return null;
        }


        StringBuilder encryptedText = new StringBuilder();

        for (char currentChar : text.toCharArray()) {
            if (Character.isLetter(currentChar)) {
                char baseChar = Character.isUpperCase(currentChar) ? 'A' : 'a';
                encryptedText.append((char) ((currentChar - baseChar + shift) % 26 + baseChar));
            } else {
                encryptedText.append(currentChar);
            }
        }

        return encryptedText.toString();

    }
    //---------------------------------------------------------------------------------------------------------

    public int isShiftValid(String text) {
        int number = 0;

        if (!text.isEmpty()) {
            number = Integer.parseInt(text);
        }

        return number;
    }
    //---------------------------------------------------------------------------------------------------------

}

