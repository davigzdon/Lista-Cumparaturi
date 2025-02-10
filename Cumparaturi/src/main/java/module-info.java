module com.example.cumparaturi {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cumparaturi to javafx.fxml;
    exports com.example.cumparaturi;
}