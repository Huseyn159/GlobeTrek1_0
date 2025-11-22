package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.User;
import service.BalanceService;
import exception.InvalidAmountException;
import exception.InvalidCardNumberException;

public class AddBalanceController {

    @FXML private TextField cardNumberField;
    @FXML private TextField cardHolderField;
    @FXML private TextField expMonthField;
    @FXML private TextField expYearField;
    @FXML private TextField cvvField;
    @FXML private TextField amountField;

    @FXML private Label cardNumberLabel;
    @FXML private Label cardHolderLabel;
    @FXML private Label expireLabel;
    @FXML private Label cvvBackLabel;
    @FXML private Label errorLabel;
    @FXML private Label confirmationLabel;

    @FXML private ImageView visaImageView;
    @FXML private ImageView chipImageView;

    private final BalanceService balanceService = new BalanceService();
    private User loggedInUser;

    /** Setter for logged-in user, must be called from Main or DashboardController */
    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    @FXML
    public void initialize() {
        // Load card images
        visaImageView.setImage(new Image(getClass().getResourceAsStream("/images/visa.png")));
        chipImageView.setImage(new Image(getClass().getResourceAsStream("/images/chip.png")));

        // ---- CARD NUMBER FORMATTER ----
        cardNumberField.textProperty().addListener((obs, oldV, newV) -> {
            String digits = newV.replaceAll("[^0-9]", "");
            if (digits.length() > 16) digits = digits.substring(0, 16);
            String formatted = digits.replaceAll("(.{4})", "$1 ").trim();
            cardNumberField.setText(formatted);
            cardNumberField.positionCaret(formatted.length());
            cardNumberLabel.setText(formatted.isEmpty() ? "#### #### #### ####" : formatted);
        });

        // ---- CARD HOLDER NAME ----
        cardHolderField.textProperty().addListener((obs, oldV, newV) -> {
            cardHolderLabel.setText(newV.isEmpty() ? "YOUR NAME" : newV.toUpperCase());
        });

        // ---- EXPIRY MM & YY ----
        expMonthField.textProperty().addListener((obs, oldV, newV) -> updateExpiry(newV, true));
        expYearField.textProperty().addListener((obs, oldV, newV) -> updateExpiry(newV, false));

        // ---- CVV ----
        cvvField.textProperty().addListener((obs, oldV, newV) -> {
            String digits = newV.replaceAll("[^0-9]", "");
            if (digits.length() > 3) digits = digits.substring(0, 3);
            cvvField.setText(digits);
            cvvField.positionCaret(digits.length());
            cvvBackLabel.setText(digits.isEmpty() ? "●●●" : digits.replaceAll(".", "•"));
        });
    }

    private void updateExpiry(String value, boolean isMonth) {
        value = value.replaceAll("[^0-9]", "");
        if (value.length() > 2) value = value.substring(0, 2);
        if (isMonth) expMonthField.setText(value);
        else expYearField.setText(value);

        String mm = expMonthField.getText();
        String yy = expYearField.getText();
        expireLabel.setText((mm.isEmpty() ? "MM" : mm) + "/" + (yy.isEmpty() ? "YY" : yy));
    }

    @FXML
    private void handleAddBalance() {
        errorLabel.setText("");
        String cardNum = cardNumberField.getText();
        String holder = cardHolderField.getText();
        String month = expMonthField.getText();
        String year = expYearField.getText();
        String cvv = cvvField.getText();
        String amountStr = amountField.getText();

        // --- Empty check ---
        if (cardNum.isEmpty() || holder.isEmpty() || month.isEmpty() || year.isEmpty() || cvv.isEmpty() || amountStr.isEmpty()) {
            errorLabel.setText("All fields are required!");
            return;
        }

        // --- Amount parse ---
        double amount;
        try {
            amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                errorLabel.setText("Amount must be greater than zero!");
                return;
            }
        } catch (NumberFormatException e) {
            errorLabel.setText("Invalid amount!");
            return;
        }

        // --- Try adding balance ---
        try {
            balanceService.addBalance(loggedInUser, cardNum, amount);
            errorLabel.setStyle("-fx-text-fill:green; -fx-font-weight:bold;");
            errorLabel.setText("✅ $" + amount + " added successfully!");

            // --- Reset fields safely ---
            cardNumberField.clear();
            cardHolderField.clear();
            expMonthField.clear();
            expYearField.clear();
            cvvField.clear();
            amountField.clear();

            // --- Reset card preview ---
            cardNumberLabel.setText("#### #### #### ####");
            cardHolderLabel.setText("YOUR NAME");
            expireLabel.setText("MM/YY");
            cvvBackLabel.setText("●●●");

        } catch (InvalidCardNumberException e) {
            errorLabel.setStyle("-fx-text-fill:red; -fx-font-weight:bold;");
            errorLabel.setText("❌ Invalid card number!");
        } catch (InvalidAmountException e) {
            errorLabel.setStyle("-fx-text-fill:red; -fx-font-weight:bold;");
            errorLabel.setText("❌ " + e.getMessage());
        }
    }

}
