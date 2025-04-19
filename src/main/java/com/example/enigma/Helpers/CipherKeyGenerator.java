package com.example.enigma.Helpers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CipherKeyGenerator {
    private  final int KEY_LENGTH = 8;
    private  final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private  final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private  final String DIGITS = "0123456789";
    private  final String SYMBOLS = "!@#$%&?";
    private  final String ALL_CHARS = UPPER + LOWER + DIGITS + SYMBOLS;
    private  final Random random = new Random();

    //---------------------------------------------------------------------------------------------------------
    public  String createEncryptionKey(String ciphertext, int shift) {
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
    private  Map<Character, String> generateAlphabetKeys() {
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
        ObjectMapper objectMapper = new ObjectMapper();

        // Сохранение карты в файл
        try {

            File file = new File("src/main/java/com/example/enigma/JSON/alphabetKeys.json");
            objectMapper.writeValue(file, alphabetKeys);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return alphabetKeys;
    }
    //---------------------------------------------------------------------------------------------------------

    private  String extractKeyFromCiphertext(String ciphertext, int shift) {
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
