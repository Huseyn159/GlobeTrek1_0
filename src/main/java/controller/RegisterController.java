package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import service.UserService;
import app.Main;

public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField nationalityField;

    @FXML
    private Label errorLabel;

    @FXML
    private Label successLabel;

    private final UserService userService = UserService.getInstance();

    @FXML
    private void handleRegister() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String nationality = nationalityField.getText().trim();

        errorLabel.setText("");
        successLabel.setText("");

        // ---- VALIDATION ----
        if (username.isEmpty() || password.isEmpty() || nationality.isEmpty()) {
            errorLabel.setText("All fields are required!");
            return;
        }
        if (username.length() < 6) {
            errorLabel.setText("Username must be at least 6 characters!");
            return;
        }
        if (password.length() < 8) {
            errorLabel.setText("Password must be at least 8 characters!");
            return;
        }

        // ---- CHECK IF USER EXISTS ----
        if (userService.getUserByUsername(username) != null) {
            errorLabel.setText("Username already exists!");
            return;
        }

        // ---- REGISTER ----
        boolean ok = userService.register(username, nationality, password);

        if (ok) {
            successLabel.setText("Registration successful!");

            // clear fields
            usernameField.clear();
            passwordField.clear();
            nationalityField.clear();
        } else {
            errorLabel.setText("Could not register user.");
        }
    }

    @FXML
    private void goToLogin() {
        Main.showScene("Login"); // FXML adı Main-də qeyd olunan açarla eyni olmalıdır
    }

}
