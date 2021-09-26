module com.example.topicos_v3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;


    opens com.example.topicos_v3 to javafx.fxml;
    exports com.example.topicos_v3;
}