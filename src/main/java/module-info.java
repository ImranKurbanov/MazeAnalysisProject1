module com.example.mazeproject {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.mazeproject.ui to javafx.fxml;

    exports com.example.mazeproject.ui;
    exports com.example.mazeproject;
}