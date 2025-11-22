package controller;

import app.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.User;
import service.UserService;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    private UserService userService = UserService.getInstance();

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = userService.login(username, password);

        if (user != null) {
            Main.setLoggedInUser(user);
            Main.showScene("Dashboard");
        } else {
            errorLabel.setText("Invalid username or password.");
        }
    }

    @FXML
    private void handleRegister() {
        Main.showScene("Register");
    }
}