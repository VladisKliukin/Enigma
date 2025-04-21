package com.example.enigma.Assistants;


import com.example.enigma.Managers.EncryptionKeyManager;

public class EncryptionAssistant {

    //---------------------------------------------------------------------------------------------------------

    public int isShiftValid(String text) {
        int shift = 0;
        if (!text.isEmpty()) {
            shift = Integer.parseInt(text);
        }

        return shift;
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
    public String applyEncryption(String text, int shift) {


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
    public String createEncryptionKey(String text, int shift) {
        EncryptionKeyManager generator = new EncryptionKeyManager();
        return generator.createEncryptionKey(shift);
    }
    //---------------------------------------------------------------------------------------------------------


}

