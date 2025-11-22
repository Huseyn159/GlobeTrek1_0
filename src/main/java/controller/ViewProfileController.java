package controller;

import app.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import model.User;

public class ViewProfileController {

    @FXML
    private Label usernameLabel;

    @FXML
    private Label nationalityLabel;

    @FXML
    private Label balanceLabel;

    @FXML
    private Label xpLabel;

    @FXML
    private Label levelLabel;

    @FXML
    private ProgressBar xpProgressBar;

    @FXML
    private Label progressLabel;

    private User loggedInUser;

    @FXML
    public void initialize() {
        loggedInUser = Main.getLoggedInUser();
        if (loggedInUser != null) {
            updateProfileDisplay();
        }
    }

    private void updateProfileDisplay() {
        usernameLabel.setText(loggedInUser.getUsername());
        nationalityLabel.setText(loggedInUser.getNationality());
        balanceLabel.setText(String.format("$%.2f", loggedInUser.getBalance()));
        xpLabel.setText(String.valueOf(loggedInUser.getXp()));
        levelLabel.setText(loggedInUser.getLevel());

        int currentXp = loggedInUser.getXp();
        String currentLevel = loggedInUser.getLevel();

        if (currentLevel.equals("Diamond")) {
            xpProgressBar.setProgress(1.0); 
            progressLabel.setText("Max Level Reached!");
        } else {
            int xpForNextLevel = getXpForNextLevel(currentLevel);
            int xpForCurrentLevel = getXpForCurrentLevel(currentLevel);
            double progress = (double) (currentXp - xpForCurrentLevel) / (xpForNextLevel - xpForCurrentLevel);
            xpProgressBar.setProgress(progress);
            progressLabel.setText(String.format("%d/%d XP to %s", currentXp - xpForCurrentLevel, xpForNextLevel - xpForCurrentLevel, getNextLevelName(currentLevel)));
        }
    }

    private int getXpForNextLevel(String currentLevel) {
        return switch (currentLevel) {
            case "Bronze" -> 1200;
            case "Silver" -> 1500;
            case "Gold" -> 3000;
            case "Platinum" -> 5000;
            case "Diamond" -> 5000; 
            default -> 0;
        };
    }

    private int getXpForCurrentLevel(String currentLevel) {
        return switch (currentLevel) {
            case "Bronze" -> 0;
            case "Silver" -> 1200;
            case "Gold" -> 1500;
            case "Platinum" -> 3000;
            case "Diamond" -> 5000;
            default -> 0;
        };
    }

    private String getNextLevelName(String currentLevel) {
        return switch (currentLevel) {
            case "Bronze" -> "Silver";
            case "Silver" -> "Gold";
            case "Gold" -> "Platinum";
            case "Platinum" -> "Diamond";
            case "Diamond" -> "Max Level";
            default -> "";
        };
    }
}