package controller;

import app.Main;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Achievement;
import model.User;
import service.AchievementManager;

import java.util.List;

public class AchievementsController {

    @javafx.fxml.FXML
    private ListView<Achievement> achievementListView;

    @javafx.fxml.FXML
    private Label noAchievementsLabel;

    private User loggedInUser;
    private AchievementManager achievementManager = new AchievementManager();

    @javafx.fxml.FXML
    public void initialize() {
        loggedInUser = Main.getLoggedInUser();
        loadAchievements();
    }

    private void loadAchievements() {
        List<Achievement> userAchievements = achievementManager.loadUserAchievements(loggedInUser.getUsername());
        List<Achievement> allAchievements = achievementManager.getAllAchievements();

        achievementListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Achievement item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    boolean isUnlocked = userAchievements.stream().anyMatch(a -> a.getName().equals(item.getName()));

                    // Emoji extraction
                    String emoji = "";
                    String nameText = item.getName();
                    if (!nameText.isEmpty()) {
                        int codePoint = nameText.codePointAt(0);
                        if (Character.isSupplementaryCodePoint(codePoint) || Character.isEmoji(codePoint)) {
                            emoji = new String(Character.toChars(codePoint));
                            nameText = nameText.substring(Character.charCount(codePoint)).trim();
                        }
                    }

                    Label emojiLabel = new Label(emoji);
                    emojiLabel.setStyle("-fx-font-size: 24px;");

                    Label nameLabel = new Label(nameText);
                    nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

                    Label descriptionLabel = new Label(item.getDescription());
                    descriptionLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #4A4A4A;");

                    Label dateLabel = new Label(isUnlocked ?
                            "Unlocked: " + userAchievements.stream().filter(a -> a.getName().equals(item.getName()))
                                    .findFirst().get().getUnlockedDate()
                            : "Locked");
                    dateLabel.setStyle(isUnlocked ?
                            "-fx-text-fill: #2E7D32; -fx-font-weight: bold;" :
                            "-fx-text-fill: #D32F2F; -fx-font-weight: bold;");

                    VBox detailsBox = new VBox(nameLabel, descriptionLabel, dateLabel);
                    detailsBox.setSpacing(2);
                    VBox.setVgrow(detailsBox, Priority.ALWAYS);

                    HBox cellBox = new HBox(emojiLabel, detailsBox);
                    cellBox.setSpacing(12);
                    cellBox.setAlignment(Pos.CENTER_LEFT);
                    cellBox.setStyle(isUnlocked ?
                            "-fx-background-color: linear-gradient(to right, #E0F2FF, #B3D9FF); -fx-padding: 10px; -fx-background-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 6, 0, 0, 2);"
                            :
                            "-fx-background-color: #F5F5F5; -fx-padding: 10px; -fx-background-radius: 10px; -fx-opacity: 0.6;"
                    );

                    // Hover effect
                    cellBox.setOnMouseEntered(e -> {
                        if (isUnlocked) {
                            cellBox.setStyle("-fx-background-color: linear-gradient(to right, #CFE9FF, #99CFFF); -fx-padding: 10px; -fx-background-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 8, 0, 0, 4);");
                        } else {
                            cellBox.setStyle("-fx-background-color: #E0E0E0; -fx-padding: 10px; -fx-background-radius: 10px; -fx-opacity: 0.8;");
                        }
                    });
                    cellBox.setOnMouseExited(e -> {
                        if (isUnlocked) {
                            cellBox.setStyle("-fx-background-color: linear-gradient(to right, #E0F2FF, #B3D9FF); -fx-padding: 10px; -fx-background-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 6, 0, 0, 2);");
                        } else {
                            cellBox.setStyle("-fx-background-color: #F5F5F5; -fx-padding: 10px; -fx-background-radius: 10px; -fx-opacity: 0.6;");
                        }
                    });

                    setGraphic(cellBox);
                }
            }
        });

        if (allAchievements.isEmpty()) {
            noAchievementsLabel.setVisible(true);
            achievementListView.setVisible(false);
        } else {
            achievementListView.getItems().setAll(allAchievements);
            noAchievementsLabel.setVisible(false);
            achievementListView.setVisible(true);
        }
    }
}
