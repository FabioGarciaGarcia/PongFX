module com.example.pongfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pongfx to javafx.fxml;
    exports com.example.pongfx;
}