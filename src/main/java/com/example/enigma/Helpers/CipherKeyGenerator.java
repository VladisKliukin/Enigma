package com.example.enigma.Helpers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class CipherKeyGenerator {

    private final int KEY_LENGTH = 8;
    private final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private final String DIGITS = "0123456789";
    private final String SYMBOLS = "!@#$%&?";
    private final String ALL_CHARS = UPPER + LOWER + DIGITS + SYMBOLS;
    private final Path PATH_ALPHABET_JSON = Path.of("src/main/java/com/example/enigma/JSON/alphabetKeys.json");

    private final Random random = new Random();

    //---------------------------------------------------------------------------------------------------------
    public String createEncryptionKey(String ciphertext, int shift) {
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
        ObjectMapper objectMapper = new ObjectMapper();
        Map<Character, String> alphabetKeys = new HashMap<>();

        if (!Files.exists(PATH_ALPHABET_JSON)) {
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


            try {
                objectMapper.writeValue(PATH_ALPHABET_JSON.toFile(), alphabetKeys);
            } catch (IOException e) {
                System.err.println("Error Saving JSON: " + e.getMessage());
            }

        } else {
            try {
                alphabetKeys = objectMapper.readValue(PATH_ALPHABET_JSON.toFile(), new TypeReference<Map<Character, String>>() {
                });

            } catch (IOException e) {
                System.err.println("Error Open To File JSON: " + e.getMessage());
            }

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
