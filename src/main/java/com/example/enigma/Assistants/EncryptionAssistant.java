package com.example.enigma.Assistants;


import java.util.Random;

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
    public String createEncryptionKey() {

        return  generateStrongKey(15);


    }
    //---------------------------------------------------------------------------------------------------------
    private String generateStrongKey(int length) {
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String symbols = "!@#$%&?";

        String allChars = upper + lower + digits + symbols;
        StringBuilder key = new StringBuilder(length);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            key.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        return key.toString();
    }
}

