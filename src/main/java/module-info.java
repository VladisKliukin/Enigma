module com.example.enigma {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires com.fasterxml.jackson.databind;


    opens com.example.enigma to javafx.fxml;
    exports com.example.enigma;
    exports com.example.enigma.Controllers;
    opens com.example.enigma.Controllers to javafx.fxml;
}