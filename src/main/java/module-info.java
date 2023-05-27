module com.example.graphjavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens graphproject to javafx.fxml;
    exports graphproject;
    exports graphproject.controller;
    opens graphproject.controller to javafx.fxml;
}