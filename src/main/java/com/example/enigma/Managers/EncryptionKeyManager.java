package com.example.enigma.Managers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class EncryptionKeyManager {


    private final String ALL_CHARS = generateAllChars();
    private final Path PATH_ALPHABET_JSON = Path.of("src/main/java/com/example/enigma/JSON/alphabetKeys.json");
    private final Path PATH_ENCRYPTION_TABLE_JSON = Path.of("src/main/java/com/example/enigma/JSON/encryptionTable.json");
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Random random = new Random();

    //---------------------------------------------------------------------------------------------------------
    public String createEncryptionKey(int shift) {

        Map<String, List<String>> encryptionTable = getEncryptionTable();
        Map<Character, String> alphabetKeys = getAlphabetKeys();
        if (alphabetKeys == null || encryptionTable == null) return null;

        StringBuilder key = new StringBuilder();

        String binary = String.format("%8s", Integer.toBinaryString(shift)).replace(' ', '0');

        for (int i = 0; i < binary.length(); i++) {
            int indexCharKey = 0;
            List<String> encryptionTableValuelist = encryptionTable.get(String.valueOf(binary.charAt(i)));

            String ch;

            do {
                if (indexCharKey >= encryptionTableValuelist.size()) {
                    indexCharKey = 0;
                }

                ch = encryptionTableValuelist.get(indexCharKey);
                indexCharKey++;
            } while (key.toString().contains(ch));


            key.append(ch);
        }

        StringBuilder encryptedKey = new StringBuilder();
        for (int i = 0; i < key.length(); i++) {
            char ch = key.charAt(i);
            encryptedKey.append(alphabetKeys.getOrDefault(ch, String.valueOf(ch)));
        }


        return encryptedKey.toString();

    }

    //---------------------------------------------------------------------------------------------------------

    public int DecryptKeyToShift(String encryptedKey) {

        String key = decryptKey(encryptedKey);
        Map<String, List<String>> encryptionTable = getEncryptionTable();
        StringBuilder binary = new StringBuilder();

        for (int i = 0; i < key.length(); i++) {
            char ch = key.charAt(i);
            String bit = null;

            for (Map.Entry<String, List<String>> entry : encryptionTable.entrySet()) {
                if (entry.getValue().contains(String.valueOf(ch))) {
                    bit = entry.getKey();
                    break;
                }
            }

            if (bit != null) {
                binary.append(bit);
            }
        }


        return Integer.parseInt(binary.toString(), 2);

    }

    //---------------------------------------------------------------------------------------------------------
    public String decryptKey(String encryptedKey) {

        Map<Character, String> alphabetKeys = getAlphabetKeys();
        if (alphabetKeys == null || encryptedKey == null) return null;

        Map<String, Character> reverseMap = new HashMap<>();

        for (Map.Entry<Character, String> entry : alphabetKeys.entrySet()) {
            reverseMap.put(entry.getValue(), entry.getKey());
        }

        StringBuilder originalKey = new StringBuilder();

        for (int i = 0; i < encryptedKey.length(); i += 2) {
            String encryptedPair = encryptedKey.substring(i, i + 2);
            Character originalChar = reverseMap.get(encryptedPair);
            originalKey.append(originalChar);
        }


        return originalKey.toString();
    }
    //---------------------------------------------------------------------------------------------------------

    public Map<String, List<String>> getEncryptionTable() {
        Map<String, List<String>> encryptionTable = null;
        try {
            encryptionTable = objectMapper.readValue(PATH_ENCRYPTION_TABLE_JSON.toFile(), new TypeReference<>() {
            });
        } catch (IOException e) {
            System.err.println("Error JSON: " + e.getMessage());
        }
        return encryptionTable;
    }

    //---------------------------------------------------------------------------------------------------------
    public Map<Character, String> getAlphabetKeys() {
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
    public String generateAllChars() {
        StringBuilder builder = new StringBuilder();
        for (char ch = 'A'; ch <= 'Z'; ch++) builder.append(ch);
        for (char ch = 'a'; ch <= 'z'; ch++) builder.append(ch);
        for (char ch = '0'; ch <= '9'; ch++) builder.append(ch);
        builder.append("!@#$%&?");
        return builder.toString();
    }
}
