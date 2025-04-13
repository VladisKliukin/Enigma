package com.example.enigma.Helpers;

import com.example.enigma.Controllers.BaseController;
import com.example.enigma.Enums.EWindowType;
import javafx.stage.Stage;

public class WindowContext {
    public Stage ownerStage = null;
    public EWindowType ownerWindowType = null;


    public WindowContext initContext(BaseController controller, EWindowType windowType) {

        ownerStage = controller.getStage();
        ownerWindowType = controller.getOwnerWindowType();


        return this;
    }
}
