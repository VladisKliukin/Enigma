package com.example.enigma.Controllers;

import com.example.enigma.Enums.EWindowType;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public abstract class BaseController {


    public EWindowType getOwnerWindowType() {
        return ownerWindowType;
    }

    protected EWindowType ownerWindowType;
    protected Scene ownerScene;

    public Scene getOwnerScene() {
        return ownerScene;
    }

    public void setOwnerScene(Scene ownerScene) {
        this.ownerScene = ownerScene;
    }


}