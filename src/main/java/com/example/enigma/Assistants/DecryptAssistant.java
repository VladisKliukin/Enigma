package com.example.enigma.Assistants;


import com.example.enigma.Managers.EncryptionKeyManager;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DecryptAssistant {
    ObjectMapper mapper = new ObjectMapper();
    EncryptionKeyManager cipherKeyGenerator = new EncryptionKeyManager();


    public String decryptKey(String encryptedKey) {
        return cipherKeyGenerator.decryptKey(encryptedKey);
    }

    public int extractShiftFromKey(String key) {

cipherKeyGenerator.DecryptKeyToShift(key);

        return  cipherKeyGenerator.DecryptKeyToShift(key);

    }

}

