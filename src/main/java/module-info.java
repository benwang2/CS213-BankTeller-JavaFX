module com.example.cs213banktellergui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cs213banktellergui to javafx.fxml;
    exports com.example.cs213banktellergui;
}