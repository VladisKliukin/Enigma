package com.example.enigma.Enums;

public enum EWindowType {
    WINDOW_MAIN("/com/example/enigma/Main-view.fxml"),
    WINDOW_DECRYPT("/com/example/enigma/Decrypt-view.fxml"),
    WINDOW_ENCRYPT("/com/example/enigma/Encrypt-view.fxml");

    private final String URL;

    EWindowType(String url) {
        URL = url;
    }

    public String getUrl() {
        return URL;
    }
}
