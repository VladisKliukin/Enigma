package com.example.enigma.Assistants;


import com.example.enigma.Helpers.CipherKeyGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DecryptAssistant {
    ObjectMapper mapper = new ObjectMapper();
    CipherKeyGenerator cipherKeyGenerator = new CipherKeyGenerator();


    public String decryptKey(String encryptedKey) {
        Map<Character, String> alphabetKeys = cipherKeyGenerator.getAlphabetKeys();
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

    public int extractShiftFromKey(String encryptedText, String key) {
        CipherKeyGenerator generator = new CipherKeyGenerator();


       return 0;// generator.DecryptKeyToShift(key);

    }

}

