package controller;

import app.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TravelSuccessController {

    @FXML
    private Label messageLabel;

    private static String successMessage = "Your trip has been successfully booked!";

    @FXML
    public void initialize() {
        messageLabel.setText(successMessage);
    }

    public static void setSuccessMessage(String message) {
        successMessage = message;
    }

    @FXML
    private void handleReturnToDashboard() {
        Main.showScene("Dashboard");
    }
}
