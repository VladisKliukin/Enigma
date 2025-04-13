package com.example.enigma.Managers;

import com.example.enigma.Controllers.BaseController;
import com.example.enigma.Enums.EWindowType;
import com.example.enigma.Helpers.WindowContext;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;

public class ManagerWindow {
    private static ManagerWindow instance;
    private Stage primaryStage;

    HashMap<EWindowType, WindowContext> windows = new HashMap<>();


    private ManagerWindow() {
    }

    public static ManagerWindow getInstance() {
        if (instance == null) {
            instance = new ManagerWindow();
        }
        return instance;
    }



    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void switchTo(BaseController controller, EWindowType windowType) {

        try {
            WindowContext context = windows.get(windowType);
            if (context == null) {
                context = new WindowContext();
                FXMLLoader loader = new FXMLLoader(getClass().getResource(windowType.getUrl()));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                controller.setOwnerScene(scene);
                context = context.initContext(controller, windowType);
                windows.put(windowType, context);
            }
            primaryStage.setScene(context.ownerScene);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(windows);
    }


}
