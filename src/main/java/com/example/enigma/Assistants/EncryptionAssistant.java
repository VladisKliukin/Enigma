package com.example.enigma.Assistants;


import java.util.*;

public class EncryptionAssistant {
    private static final int KEY_LENGTH = 8;
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "!@#$%&?";
    private static final String ALL_CHARS = UPPER + LOWER + DIGITS + SYMBOLS;
    private final Random random = new Random();
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

        return generateStrongKey(15);


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

    //---------------------------------------------------------------------------------------------------------
    public String createEncryptionKeyNew(String ciphertext, int shift) {
        Map<Character, String> alphabetKeys = generateAlphabetKeys();
        StringBuilder keyWithoutEncryption = new StringBuilder(extractKeyFromCiphertext(ciphertext, shift));

        while (keyWithoutEncryption.length() < KEY_LENGTH) {
            keyWithoutEncryption.append(LOWER.charAt(random.nextInt(LOWER.length())));
        }

        StringBuilder keyWithEncryption = new StringBuilder();
        for (int i = 0; i < KEY_LENGTH; i++) {
            char ch = keyWithoutEncryption.charAt(i);
            keyWithEncryption.append(alphabetKeys.getOrDefault(ch, String.valueOf(ch)));
        }

        return keyWithEncryption.toString();

    }

    //---------------------------------------------------------------------------------------------------------
    private Map<Character, String> generateAlphabetKeys() {
        Map<Character, String> alphabetKeys = new HashMap<>();
        Set<String> usedValues = new HashSet<>();

        for (char ch : ALL_CHARS.toCharArray()) {
            String randomMapping;

            do {
                char firstChar = ALL_CHARS.charAt(random.nextInt(ALL_CHARS.length()));
                char secondChar = ALL_CHARS.charAt(random.nextInt(ALL_CHARS.length()));
                randomMapping = "" + firstChar + secondChar;
            }
            while (usedValues.contains(randomMapping));

            usedValues.add(randomMapping);
            alphabetKeys.put(ch, randomMapping);
        }
        return alphabetKeys;
    }
    //---------------------------------------------------------------------------------------------------------

    private String extractKeyFromCiphertext(String ciphertext, int shift) {
        StringBuilder key = new StringBuilder();
        int index = 1;
        for (int i = 0; i < ciphertext.length(); i++) {
            char currentChar = ciphertext.charAt(i);
            if (Character.isLetter(currentChar)) {
                if (index % shift == 0) {
                    key.append(currentChar);
                }

                index++;
            }

        }
        return key.toString();
    }
    //---------------------------------------------------------------------------------------------------------

}

