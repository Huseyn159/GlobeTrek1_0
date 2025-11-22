package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    private static Stage primaryStage;
    private static final Map<String, String> fxmls = new HashMap<>();
    private static User loggedInUser;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        primaryStage.setTitle("GlobeTrek");

        // Register FXML paths
        addFxml("Login", "/fxml/Login.fxml");
        addFxml("Register", "/fxml/Register.fxml");
        addFxml("Dashboard", "/fxml/Dashboard.fxml");
        addFxml("BookTrip", "/fxml/BookTrip.fxml");
        addFxml("TravelHistory", "/fxml/TravelHistory.fxml");
        addFxml("ViewProfile", "/fxml/ViewProfile.fxml");
        addFxml("AddBalance", "/fxml/AddBalance.fxml");
        addFxml("Achievements", "/fxml/Achievements.fxml");
        addFxml("VisaQuiz", "/fxml/VisaQuiz.fxml");
        addFxml("TravelSuccess", "/fxml/TravelSuccess.fxml");


        showScene("Login"); // Start with the Login screen
        primaryStage.show();
    }

    public static void addFxml(String name, String path) {
        fxmls.put(name, path);
    }

    public static void showScene(String name) {
        try {
            String path = fxmls.get(name);
            if (path == null) {
                System.err.println("FXML not registered: " + name);
                return;
            }
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(path));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Main.class.getResource("/css/style.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.sizeToScene();
            primaryStage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading FXML: " + name);
        }
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    public static void main(String[] args) {
        launch(args);
    }
}