package controller;

import app.Main;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import model.Achievement;
import model.Travel;
import model.User;
import service.AchievementManager;
import service.TravelService;
import service.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BookTripController {

    @FXML
    private ComboBox<String> fromCountryComboBox;

    @FXML
    private ComboBox<String> toCountryComboBox;

    @FXML
    private ComboBox<String> flightTypeComboBox;

    @FXML
    private DatePicker travelDatePicker;

    @FXML
    private Label costLabel;

    @FXML
    private Label errorLabel;

    private User loggedInUser;
    private TravelService travelService = TravelService.getInstance();
    private AchievementManager achievementManager = new AchievementManager();

    // Country data for demonstration, now populated from TravelService
    private Map<String, CountryInfo> countryData = new HashMap<>();

    @FXML
    public void initialize() {
        loggedInUser = Main.getLoggedInUser();

        // Populate countryData from TravelService
        travelService.getCountryToContinentMap().forEach((country, continent) -> {
            // Dummy visa required and base price for now, can be refined
            boolean visaRequired = switch (continent) {
                case "Europe", "Asia", "Africa" -> true; // Assume visa required for some continents
                default -> false;
            };
            countryData.put(country, new CountryInfo(continent, visaRequired, 100.0));
        });

        // Add any additional countries not in TravelService's continent map if needed
        // Example:
        if (!countryData.containsKey("Azerbaijan")) countryData.put("Azerbaijan", new CountryInfo("Asia", false, 100.0));
        if (!countryData.containsKey("Turkey")) countryData.put("Turkey", new CountryInfo("Asia", false, 150.0));
        if (!countryData.containsKey("Georgia")) countryData.put("Georgia", new CountryInfo("Asia", false, 120.0));
        if (!countryData.containsKey("Cuba")) countryData.put("Cuba", new CountryInfo("America", true, 400.0));


        List<String> countries = countryData.keySet().stream().sorted().collect(Collectors.toList());
        fromCountryComboBox.setItems(FXCollections.observableArrayList(countries));
        toCountryComboBox.setItems(FXCollections.observableArrayList(countries));

        flightTypeComboBox.setItems(FXCollections.observableArrayList("Economy", "Business", "First"));
        flightTypeComboBox.getSelectionModel().selectFirst(); // Default to Economy

        // Set default 'from' country to user's nationality if available
        if (loggedInUser != null && loggedInUser.getNationality() != null && !loggedInUser.getNationality().isEmpty()) {
            fromCountryComboBox.getSelectionModel().select(loggedInUser.getNationality());
        }

        // Add listeners to update cost
        fromCountryComboBox.valueProperty().addListener((obs, oldVal, newVal) -> updateCost());
        toCountryComboBox.valueProperty().addListener((obs, oldVal, newVal) -> updateCost());
        flightTypeComboBox.valueProperty().addListener((obs, oldVal, newVal) -> updateCost());
        travelDatePicker.valueProperty().addListener((obs, oldVal, newVal) -> updateCost());

        updateCost(); // Initial cost update
    }

    private void updateCost() {
        String from = fromCountryComboBox.getValue();
        String to = toCountryComboBox.getValue();
        String flightType = flightTypeComboBox.getValue();
        LocalDate date = travelDatePicker.getValue();

        if (from == null || to == null || flightType == null || date == null) {
            costLabel.setText("Estimated Cost: $0.00");
            return;
        }

        if (from.equals(to)) {
            errorLabel.setText("Cannot book a trip to the same country.");
            costLabel.setText("Estimated Cost: $0.00");
            return;
        } else {
            errorLabel.setText("");
        }

        CountryInfo toCountryInfo = countryData.get(to);
        boolean visaRequired = toCountryInfo != null && toCountryInfo.isVisaRequired();

        double baseCost = travelService.calculateCost(from, to, flightType, visaRequired);

        // Level discount
        double discountPercent = switch (loggedInUser.getLevel().toLowerCase()) {
            case "silver" -> 5;
            case "gold" -> 10;
            case "diamond" -> 15;
            case "platinum" -> 20;
            default -> 0;
        };

        double discountedCost = baseCost * (1 - discountPercent / 100);

        // Display cost with discount info
        if (discountPercent > 0) {
            costLabel.setText(String.format("Estimated Cost: $%.2f (Discount: %d%% for %s level)",
                    discountedCost, (int) discountPercent, loggedInUser.getLevel()));
        } else {
            costLabel.setText(String.format("Estimated Cost: $%.2f", discountedCost));
        }
    }

    @FXML
    private void handleBookTrip() {
        String from = fromCountryComboBox.getValue();
        String to = toCountryComboBox.getValue();
        String flightType = flightTypeComboBox.getValue();
        LocalDate date = travelDatePicker.getValue();

        if (from == null || to == null || flightType == null || date == null) {
            errorLabel.setText("Please fill in all trip details.");
            return;
        }

        if (from.equals(to)) {
            errorLabel.setText("Cannot book a trip to the same country.");
            return;
        }

        CountryInfo toCountryInfo = countryData.get(to);
        boolean visaRequired = toCountryInfo != null && toCountryInfo.isVisaRequired();

        double baseCost = travelService.calculateCost(from, to, flightType, visaRequired);

        // Apply level discount
        double discountPercent = switch (loggedInUser.getLevel().toLowerCase()) {
            case "silver" -> 5;
            case "gold" -> 10;
            case "diamond" -> 15;
            case "platinum" -> 20;
            default -> 0;
        };
        double finalCost = baseCost * (1 - discountPercent / 100);

        if (loggedInUser.getBalance() < finalCost) {
            errorLabel.setText(String.format("Insufficient balance. You need $%.2f more.", finalCost - loggedInUser.getBalance()));
            return;
        }

        if (visaRequired) {
            // ✅ Pass all info to VisaQuizController
            VisaQuizController.setQuizInfo(
                    toCountryInfo.getRegion(), // quiz region
                    from,
                    to,
                    flightType,
                    finalCost,
                    date,
                    this // BookTripController instance
            );
            Main.showScene("VisaQuiz");
        } else {
            // No visa needed -> finalize directly
            bookTripFinalize(from, to, flightType, visaRequired, finalCost, date);
        }
    }




    // This method is called after a successful visa quiz or directly if no visa is required
    public void bookTripFinalize(String from, String to, String flightType, boolean visaRequired, double cost, LocalDate date) {
        // Deduct balance
        loggedInUser.setBalance(loggedInUser.getBalance() - cost);

        // Add travel
        Travel travel = new Travel(from, to, flightType, visaRequired);
        travel.setCost(cost);
        travel.setDate(date.atStartOfDay());
        travelService.addTravel(loggedInUser, travel);

        // XP / Level
        int xpBonus = switch (flightType) {
            case "Economy" -> 50;
            case "Business" -> 100;
            case "First" -> 250;
            default -> 0;
        };
        loggedInUser.addXp(xpBonus);
        String newLevel = loggedInUser.updateLevel();

        UserService.getInstance().save();

        List<Achievement> newlyUnlockedAchievements = achievementManager.checkTravelAchievements(loggedInUser);

        // Clear form
        fromCountryComboBox.getSelectionModel().clearSelection();
        toCountryComboBox.getSelectionModel().clearSelection();
        flightTypeComboBox.getSelectionModel().selectFirst();
        travelDatePicker.setValue(null);
        updateCost();

        // Build success message
        StringBuilder msg = new StringBuilder("Trip to " + to + " booked successfully!\nCost: $" + String.format("%.2f", cost));
        if (!newlyUnlockedAchievements.isEmpty()) {
            msg.append("\nAchievements unlocked: ");
            for (Achievement a : newlyUnlockedAchievements) msg.append(a.getName()).append(", ");
            msg.setLength(msg.length() - 2); // remove last comma
        }
        if (newLevel != null) msg.append("\nLevel Up: ").append(newLevel);

        TravelSuccessController.setSuccessMessage(msg.toString());
        Main.showScene("TravelSuccess");
    }



    private static class CountryInfo {
        private String region;
        private boolean visaRequired;
        private double basePrice; // Base price for calculation, not directly used for display

        public CountryInfo(String region, boolean visaRequired, double basePrice) {
            this.region = region;
            this.visaRequired = visaRequired;
            this.basePrice = basePrice;
        }

        public String getRegion() {
            return region;
        }

        public boolean isVisaRequired() {
            return visaRequired;
        }

        public double getBasePrice() {
            return basePrice;
        }
    }
}
