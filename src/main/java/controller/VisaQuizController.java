package controller;

import app.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.Question;
import model.User;
import service.VisaQuizService;

import java.time.LocalDate;
import java.util.List;

public class VisaQuizController {

    @FXML
    private VBox quizBox;

    @FXML
    private Label questionLabel;

    @FXML
    private TextField answerField;

    @FXML
    private Label resultLabel;

    @FXML
    private Button returnButton;

    private static String quizRegion;
    private static String fromCountry;
    private static String toCountry;
    private static String flightType;
    private static double tripCost;
    private static LocalDate travelDate;
    private static BookTripController bookTripControllerInstance;

    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int correctAnswers = 0;

    private VisaQuizService visaQuizService = new VisaQuizService();
    private User loggedInUser;

    public static void setQuizInfo(String region, String from, String to, String type, double cost, LocalDate date, BookTripController controller) {
        quizRegion = region;
        fromCountry = from;
        toCountry = to;
        flightType = type;
        tripCost = cost;
        travelDate = date;
        bookTripControllerInstance = controller;
    }

    @FXML
    public void initialize() {
        loggedInUser = Main.getLoggedInUser();
        questions = visaQuizService.getQuestions(quizRegion);

        // Officer intro message
        showAlert(AlertType.INFORMATION, "Officer's Desk", visaQuizService.getRandomIntroMessage(toCountry));

        if (questions.isEmpty()) {
            showAlert(AlertType.INFORMATION, "Officer's Desk", "No quiz required for this region. Proceeding with booking.");
            quizBox.setVisible(false);
            if (bookTripControllerInstance != null) {
                bookTripControllerInstance.bookTripFinalize(fromCountry, toCountry, flightType, true, tripCost, travelDate);
            }
        } else {
            displayQuestion();
        }
    }

    private void displayQuestion() {
        if (currentQuestionIndex < questions.size()) {
            questionLabel.setText(questions.get(currentQuestionIndex).getQuestionText());
            answerField.clear();
            resultLabel.setText("");
        } else {
            finishQuiz();
        }
    }

    @FXML
    private void submitAnswer() {
        String userAnswer = answerField.getText();
        Question currentQuestion = questions.get(currentQuestionIndex);

        if (userAnswer.equalsIgnoreCase(currentQuestion.getCorrectAnswer())) {
            correctAnswers++;
            showAlert(AlertType.INFORMATION, "Officer's Feedback", visaQuizService.getCorrectAnswerMessage());
        } else {
            showAlert(AlertType.WARNING, "Officer's Feedback", visaQuizService.getWrongAnswerMessage());
        }

        currentQuestionIndex++;
        displayQuestion();
    }

    private void finishQuiz() {
        quizBox.setVisible(false);
        returnButton.setVisible(true);

        if (correctAnswers >= 2) { // Pass condition
            showAlert(AlertType.INFORMATION, "Officer's Decision", visaQuizService.getVisaApprovedMessage(toCountry));
            resultLabel.setText("Visa Approved! Booking your trip...");
            if (bookTripControllerInstance != null) {
                bookTripControllerInstance.bookTripFinalize(fromCountry, toCountry, flightType, true, tripCost, travelDate);
            }
        } else {
            showAlert(AlertType.WARNING, "Officer's Decision", visaQuizService.getVisaRejectedMessage(toCountry, correctAnswers, questions.size()));
            resultLabel.setText("Visa Denied. You answered " + correctAnswers + " out of " + questions.size() + " correctly.\nTrip cancelled.");
        }
    }

    @FXML
    private void handleReturnToDashboard() {
        Main.showScene("Dashboard");
    }

    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        // Apply custom stylesheet to the alert dialog
        alert.getDialogPane().getStylesheets().add(Main.class.getResource("/css/style.css").toExternalForm());
        alert.getDialogPane().getStyleClass().add("dialog-pane"); // Apply the custom style class

        // Apply specific style for correct/wrong answer feedback
        if (title.equals("Officer's Feedback")) {
            if (type == AlertType.INFORMATION) { // Correct answer
                alert.getDialogPane().lookup(".content.label").getStyleClass().add("correct-answer-message");
            } else if (type == AlertType.WARNING) { // Wrong answer
                alert.getDialogPane().lookup(".content.label").getStyleClass().add("wrong-answer-message");
            }
        }
        alert.showAndWait();
    }
}
