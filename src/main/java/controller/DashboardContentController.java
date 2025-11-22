package controller;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class DashboardContentController {

    @FXML
    private ImageView globeImageView;

    @FXML
    private Label welcomeTitleLabel;

    @FXML
    private Label welcomeMessageLabel;

    @FXML
    public void initialize() {
        setupGlobe();
        playIntroAnimation();
    }

    private void setupGlobe() {
        // Globe şəkilini yüklə
        globeImageView.setImage(new Image(getClass().getResourceAsStream("/images/globe.png")));
        globeImageView.setOpacity(1.0);

        // RotateTransition ilə fırlanma
        RotateTransition rotate = new RotateTransition(Duration.seconds(12), globeImageView);
        rotate.setByAngle(360);
        rotate.setCycleCount(RotateTransition.INDEFINITE);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.play();
    }

    private void playIntroAnimation() {
        // Welcome title və message üçün fade-in animasiya
        FadeTransition fadeTitle = new FadeTransition(Duration.seconds(1.5), welcomeTitleLabel);
        fadeTitle.setFromValue(0.0);
        fadeTitle.setToValue(1.0);
        fadeTitle.setDelay(Duration.seconds(0.5));

        FadeTransition fadeMessage = new FadeTransition(Duration.seconds(1.5), welcomeMessageLabel);
        fadeMessage.setFromValue(0.0);
        fadeMessage.setToValue(1.0);
        fadeMessage.setDelay(Duration.seconds(1.0));

        ParallelTransition parallelTransition = new ParallelTransition(fadeTitle, fadeMessage);
        parallelTransition.play();
    }
}
