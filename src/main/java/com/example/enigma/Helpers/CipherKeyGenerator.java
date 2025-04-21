package com.example.enigma.Helpers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class CipherKeyGenerator {

    private final int KEY_LENGTH = 8;
    private final int REAL_KEY_PART_LENGTH = 8;
    private final String ALL_CHARS = generateAllChars();
    private final Path PATH_ALPHABET_JSON = Path.of("src/main/java/com/example/enigma/JSON/alphabetKeys.json");
    private final Path PATH_ENCRYPTION_TABLE_JSON = Path.of("src/main/java/com/example/enigma/JSON/encryptionTable.json");
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Random random = new Random();


    public String createEncryptionKey(String ciphertext, int shift) {
        Map<Character, String> alphabetKeys = generateAlphabetKeys();
        StringBuilder key = new StringBuilder();


        String binary = String.format("%8s", Integer.toBinaryString(shift)).replace(' ', '0');

        for (char bit : binary.toCharArray()) {
            key.append(bit == '0' ? 'X' : 'Y'); // X = 0, Y = 1
        }

        System.out.printf("Key: " + Integer.parseInt(binary) + "\n");
        System.out.printf("Key2: " + key + "\n");


        while (key.length() < KEY_LENGTH) {
            key.append(ALL_CHARS.charAt(random.nextInt(ALL_CHARS.length())));
        }

        StringBuilder encryptedKey = new StringBuilder();
        for (int i = 0; i < KEY_LENGTH; i++) {
            char ch = key.charAt(i);
            encryptedKey.append(alphabetKeys.getOrDefault(ch, String.valueOf(ch)));
        }

        return encryptedKey.toString();
    }


    public String createEncryptionKey(int shift) {
        Map<String, List<String>> encryptionTable = jsonEncoder();
        String binary = String.format("%8s", Integer.toBinaryString(shift)).replace(' ', '0');
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < binary.length(); i++) {

            List<String> binaryKey =  encryptionTable.get(String.valueOf(binary.charAt(i)));



            System.out.printf("Key: " + binaryKey + "\n");
        }



        return key.toString();

    }

    //---------------------------------------------------------------------------------------------------------
    private Map<Character, String> generateAlphabetKeys() {
        Map<Character, String> alphabetKeys = new HashMap<>();

        if (!Files.exists(PATH_ALPHABET_JSON)) {
            Set<String> usedValues = new HashSet<>();

            for (char ch : ALL_CHARS.toCharArray()) {
                String randomMapping;
                do {
                    char first = ALL_CHARS.charAt(random.nextInt(ALL_CHARS.length()));
                    char second = ALL_CHARS.charAt(random.nextInt(ALL_CHARS.length()));
                    randomMapping = "" + first + second;
                } while (usedValues.contains(randomMapping));

                usedValues.add(randomMapping);
                alphabetKeys.put(ch, randomMapping);
            }

            try {
                objectMapper.writeValue(PATH_ALPHABET_JSON.toFile(), alphabetKeys);
            } catch (IOException e) {
                System.err.println("Error saving alphabet JSON: " + e.getMessage());
            }
        } else {
            try {
                alphabetKeys = objectMapper.readValue(PATH_ALPHABET_JSON.toFile(), new TypeReference<>() {
                });
            } catch (IOException e) {
                System.err.println("Error reading alphabet JSON: " + e.getMessage());
            }
        }

        return alphabetKeys;
    }

    //---------------------------------------------------------------------------------------------------------
    public Map<String, Character> getReversedAlphabetMap() {
        Map<Character, String> originalMap = getAlphabetKeys();
        Map<String, Character> reverseMap = new HashMap<>();

        if (originalMap != null) {
            for (Map.Entry<Character, String> entry : originalMap.entrySet()) {
                reverseMap.put(entry.getValue(), entry.getKey());
            }
        }

        return reverseMap;
    }

    //---------------------------------------------------------------------------------------------------------
    public Map<Character, String> getAlphabetKeys() {
        try {
            return objectMapper.readValue(PATH_ALPHABET_JSON.toFile(), new TypeReference<>() {
            });
        } catch (IOException e) {
            System.err.println("Error reading alphabet JSON: " + e.getMessage());
            return null;
        }
    }

    //---------------------------------------------------------------------------------------------------------
    public String extractRealKeyPart(String encryptedKey, Map<String, Character> reverseMap) {
        StringBuilder decrypted = new StringBuilder();
        for (int i = 0; i < REAL_KEY_PART_LENGTH * 2; i += 2) {
            String pair = encryptedKey.substring(i, i + 2);
            Character originalChar = reverseMap.get(pair);
            if (originalChar != null) {
                decrypted.append(originalChar);
            }
        }
        return decrypted.toString();
    }

    //---------------------------------------------------------------------------------------------------------
    public String generateAllChars() {
        StringBuilder builder = new StringBuilder();
        for (char ch = 'A'; ch <= 'Z'; ch++) builder.append(ch);
        for (char ch = 'a'; ch <= 'z'; ch++) builder.append(ch);
        for (char ch = '0'; ch <= '9'; ch++) builder.append(ch);
        builder.append("!@#$%&?");
        return builder.toString();
    }

    public int DecryptKeyToShift(String key) {

        String realKeyPart = extractRealKeyPart(key, getReversedAlphabetMap());

        StringBuilder binary = new StringBuilder();

        for (char ch : realKeyPart.toCharArray()) {
            binary.append(ch == 'X' ? '0' : '1');
        }
        return Integer.parseInt(binary.toString(), 2);
    }

    public Map<String, List<String>> jsonEncoder() {
        Map<String, List<String>> encryptionTable = null;
        try {
            encryptionTable = objectMapper.readValue(PATH_ENCRYPTION_TABLE_JSON.toFile(), new TypeReference<>() {
            });
            // Пример доступа к данным в JSON
            System.out.println("00: " + encryptionTable.get("00"));
            System.out.println("01: " + encryptionTable.get("01"));
        } catch (IOException e) {
            System.err.println("Error JSON: " + e.getMessage());
        }
        return encryptionTable;
    }


}
