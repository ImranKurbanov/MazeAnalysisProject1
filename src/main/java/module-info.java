module com.example.mazeproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.mazeproject to javafx.fxml;
    exports com.example.mazeproject;
}