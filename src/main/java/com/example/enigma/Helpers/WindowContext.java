package com.example.enigma.Helpers;

import com.example.enigma.Controllers.BaseController;
import com.example.enigma.Enums.EWindowType;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WindowContext {
    public BaseController ownerController;
    public EWindowType ownerWindowType = null;
    public Scene ownerScene;



    public WindowContext initContext(BaseController controller, EWindowType windowType) {

        ownerController = controller;
        ownerWindowType = controller.getOwnerWindowType();
        ownerScene = controller.getOwnerScene();


        return this;
    }
}
