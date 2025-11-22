package controller;

import app.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import model.User;

import java.io.IOException;

public class DashboardController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private StackPane contentArea;

    private User loggedInUser;

    @FXML
    public void initialize() {
        loggedInUser = Main.getLoggedInUser();
        if (loggedInUser != null) {
            welcomeLabel.setText("Welcome, " + loggedInUser.getUsername() + "!");
        }
        showDashboardContent(); // Load default content
    }

    @FXML
    private void showDashboardContent() {
        // This could be a summary page or just empty for now
        loadContent("/fxml/DashboardContent.fxml");
    }

    @FXML
    private void showBookTrip() {
        loadContent("/fxml/BookTrip.fxml");
    }

    @FXML
    private void showTravelHistory() {
        loadContent("/fxml/TravelHistory.fxml");
    }

    @FXML
    private void showViewProfile() {
        loadContent("/fxml/ViewProfile.fxml");
    }

    @FXML
    private void showAddBalance() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddBalance.fxml"));
            Parent root = loader.load();

            // Controller-i əldə et və user-i set et
            AddBalanceController controller = loader.getController();
            controller.setLoggedInUser(Main.getLoggedInUser()); // loggedInUser burada verilir

            contentArea.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading AddBalance.fxml");
        }
    }


    @FXML
    private void showAchievements() {
        loadContent("/fxml/Achievements.fxml");
    }

    @FXML
    private void handleLogout() {
        Main.setLoggedInUser(null); // Clear logged-in user
        Main.showScene("Login");
    }

    private void loadContent(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent content = loader.load();
            contentArea.getChildren().setAll(content);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading content FXML: " + fxmlPath);
        }
    }
}