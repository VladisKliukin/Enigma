package com.example.enigma.Assistants;


import java.util.Random;

public class EncryptionAssistant {
    final int KEY_LENGTH = 15;
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


    /*
     *вызывавется метод создание ключа
     * генерируется ключ:
     * шифрованный текст: wer ghyujki bfsdc vmtgr
     * key:gubdmr
     * далее идет проверка
     * поиск буквы(g) return index = 3;
     * поиск буквы(u) return index = 6; (6-3) = 3
     * и тд...
     * если в key error поиск буквы(j) return index = 7; (7 - 3) = 4 error invalid key
     * */
    public String createEncryptionKeyNew(String ciphertext, int shift) {

//Возможно стоит просто доходить до конца строки, и если к примеру у нас в строке всего 7 букв
// и здвиг к примеру равен 3 тогда получается что у нас будет в ключе только четвертая буква, а 14 букв ключа будут просто рандомные.
        if (ciphertext.length() <= (KEY_LENGTH * 3)) {
        }

        StringBuilder key = new StringBuilder();

                int letterIndex = 0;

        for (int i = 0; i < ciphertext.length(); i++) {
            char currentChar = ciphertext.charAt(i);



            //чтоб получить второй индекс нам нужно  3+3 = 6
            // 3
            // Сравниваем индекс символа с сдвигом
            if (Character.isLetter(currentChar)) {
                letterIndex++; // Увеличиваем индекс только для букв

                // Проверяем, совпадает ли текущий индекс с сдвигом
                if (letterIndex % shift == shift - 1) {
                    key.append(currentChar);
                    System.out.println("Символ на индексе " + i + " совпадает с сдвигом: " + currentChar);
                }
            }
        }


        return key.toString();
    }
}

