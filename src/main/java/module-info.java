module ru.feryafox.glamppc {
    requires javafx.controls;
    requires javafx.fxml;

    opens ru.feryafox.glamppc to javafx.fxml;
    requires GLampJava;
    requires com.google.gson;
    requires jdk.compiler;
    exports ru.feryafox.glamppc;
    exports ru.feryafox.glamppc.Controllers;
    opens ru.feryafox.glamppc.Controllers to javafx.fxml;
    exports ru.feryafox.glamppc.DataStorage.AppData;
}