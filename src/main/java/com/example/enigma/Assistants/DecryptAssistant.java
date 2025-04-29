package com.example.enigma.Assistants;


import com.example.enigma.Managers.EncryptionKeyManager;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DecryptAssistant {
    final int NUMBER_LETTERS = 26;

    ObjectMapper mapper = new ObjectMapper();
    EncryptionKeyManager keyManager = new EncryptionKeyManager();


    public int extractShiftFromKey(String key) {

        keyManager.DecryptKeyToShift(key);

        return keyManager.DecryptKeyToShift(key);

    }

    public String applyDecryptionText(String encryptedText,String encryptedKey ){
        int shift = keyManager.DecryptKeyToShift(encryptedKey);
        StringBuilder decryptedText = new StringBuilder();

        for (char currentChar : encryptedText.toCharArray()) {
            if (Character.isLetter(currentChar)) {
                char baseChar = Character.isUpperCase(currentChar) ? 'A' : 'a';
                decryptedText.append((char) ((currentChar - baseChar - shift + NUMBER_LETTERS) % NUMBER_LETTERS + baseChar));
            } else {
                decryptedText.append(currentChar);
            }
        }

        return decryptedText.toString();
    }

}

