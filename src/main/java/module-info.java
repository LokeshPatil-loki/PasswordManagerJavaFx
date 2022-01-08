module com.example.passwordmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.passwordmanager to javafx.fxml;
    exports com.example.passwordmanager;
}