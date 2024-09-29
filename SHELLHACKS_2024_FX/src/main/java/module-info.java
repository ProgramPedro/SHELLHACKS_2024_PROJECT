module shellhacks_2024_fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires jlatexmath;
    requires com.google.gson;
    requires java.desktop;
    requires javafx.swing;


    opens mainfolder to javafx.fxml;
    exports mainfolder;
}