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

    WindowContext windowContext = new WindowContext();
    HashMap<EWindowType, WindowContext> windows = new HashMap<>();

    private ManagerWindow() {
    }

    public static ManagerWindow getInstance() {
        if (instance == null) {
            instance = new ManagerWindow();
        }
        return instance;
    }

    public void openWindow(BaseController controller, EWindowType windowType) {


        if (windows.get(controller.getOwnerWindowType()) == null) {
            windows.put(controller.getOwnerWindowType(), windowContext.initContext(controller, windowType));
        }

        WindowContext context = windows.get(windowType);
        if (context == null) {
            if (controller.getStage() != null) {
                controller.getStage().hide();
            }


            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(windowType.getUrl()));
                Parent root = loader.load();


                Stage newStage = new Stage();
                newStage.setScene(new Scene(root));
                newStage.setTitle("E N I G M A");

                BaseController newController = loader.getController();
                WindowContext newContext = windowContext.initContext(newController, windowType);
                newContext.ownerStage = newStage;
                // Сохраняем контекст в коллекцию
                windows.put(windowType, newContext);


                newStage.showAndWait();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (windows.get(windowType).ownerStage != null) {
                windows.get(windowType).ownerStage.showAndWait();
            }else {
                System.out.println("Ошибка: ownerStage для текущего окна не инициализирован.");
            }
        }
    }
}
