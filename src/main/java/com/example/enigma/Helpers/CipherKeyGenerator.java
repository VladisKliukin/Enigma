package com.example.enigma.Helpers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class CipherKeyGenerator {

    private final int KEY_LENGTH = 8;
    private final int REAL_KEY_PART_LENGTH = 3;
    private final String ALL_CHARS = generateAllChars();
    private final Path PATH_ALPHABET_JSON = Path.of("src/main/java/com/example/enigma/JSON/alphabetKeys.json");
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Random random = new Random();

    // Генерация полного ключа (3 символа из шифротекста, остальные случайные)
    public String createEncryptionKey(String ciphertext, int shift) {
        Map<Character, String> alphabetKeys = generateAlphabetKeys();


        // Извлекаем "реальные" символы из текста
        StringBuilder keyFromText = new StringBuilder();
        int index = 0;  // Начинаем с первого символа
        while (keyFromText.length() < REAL_KEY_PART_LENGTH) {
            char currentChar = ciphertext.charAt(index);
            if (Character.isLetter(currentChar)) {
                keyFromText.append(currentChar);
            }
            index = (index + shift) % ciphertext.length();  // Переход к следующему символу по кругу
        }

        // Добавляем случайные символы
        while (keyFromText.length() < KEY_LENGTH) {
            keyFromText.append(ALL_CHARS.charAt(random.nextInt(ALL_CHARS.length())));
        }

        // Шифруем
        StringBuilder encryptedKey = new StringBuilder();
        for (int i = 0; i < KEY_LENGTH; i++) {
            char ch = keyFromText.charAt(i);
            encryptedKey.append(alphabetKeys.getOrDefault(ch, String.valueOf(ch)));
        }

        return encryptedKey.toString();
    }

    // Генерация карты символов
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
                alphabetKeys = objectMapper.readValue(PATH_ALPHABET_JSON.toFile(), new TypeReference<>() {});
            } catch (IOException e) {
                System.err.println("Error reading alphabet JSON: " + e.getMessage());
            }
        }

        return alphabetKeys;
    }

    // Обратная карта для расшифровки
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

    // Получение оригинальной карты
    public Map<Character, String> getAlphabetKeys() {
        try {
            return objectMapper.readValue(PATH_ALPHABET_JSON.toFile(), new TypeReference<>() {});
        } catch (IOException e) {
            System.err.println("Error reading alphabet JSON: " + e.getMessage());
            return null;
        }
    }

    // Расшифровка первых 3 символов ключа
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

    // Генерация всех допустимых символов
    public static String generateAllChars() {
        StringBuilder builder = new StringBuilder();
        for (char ch = 'A'; ch <= 'Z'; ch++) builder.append(ch);
        for (char ch = 'a'; ch <= 'z'; ch++) builder.append(ch);
        for (char ch = '0'; ch <= '9'; ch++) builder.append(ch);
        builder.append("!@#$%&?");
        return builder.toString();
    }
}
