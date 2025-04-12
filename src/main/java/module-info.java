module com.example.enigma {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.enigma to javafx.fxml;
    exports com.example.enigma;
}