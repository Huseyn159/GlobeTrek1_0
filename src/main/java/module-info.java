module globetrek1_0 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.graphics; // Added for general JavaFX graphics access

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.desktop;

    opens controller to javafx.fxml;
    opens app to javafx.fxml, javafx.graphics; // Open app to javafx.graphics for LauncherImpl
    exports app;
}