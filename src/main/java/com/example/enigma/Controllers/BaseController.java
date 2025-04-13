package com.example.enigma.Controllers;

import com.example.enigma.Enums.EWindowType;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.stage.Stage;

public abstract class BaseController {

    protected Stage ownerStage;

    public EWindowType getOwnerWindowType() {
        return ownerWindowType;
    }

    protected EWindowType ownerWindowType;

    protected void initOwnerStage(Node anyNodeInScene) {
        Platform.runLater(() -> {
            if (anyNodeInScene.getScene() != null) {
                ownerStage = (Stage) anyNodeInScene.getScene().getWindow();
            }
        });
    }

    public Stage getStage() {
        return ownerStage;
    }
}