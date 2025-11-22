package service;

import model.Travel;
import model.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TravelService {
    private static TravelService instance;
    private Map<String, List<Travel>> travelData = new HashMap<>();
    private final String FILE_NAME = "data/travel_history.txt";

    private Map<String, String> countryToContinent = new HashMap<>();

    private TravelService() {
        loadTravelData();
        initializeContinents();
    }

    public static TravelService getInstance() {
        if (instance == null) {
            instance = new TravelService();
        }
        return instance;
    }

    private void initializeContinents() {
        // Europe
        countryToContinent.put("France", "Europe");
        countryToContinent.put("Germany", "Europe");
        countryToContinent.put("Italy", "Europe");
        countryToContinent.put("Spain", "Europe");
        countryToContinent.put("United Kingdom", "Europe");

        // Asia
        countryToContinent.put("Japan", "Asia");
        countryToContinent.put("China", "Asia");
        countryToContinent.put("India", "Asia");
        countryToContinent.put("Thailand", "Asia");
        countryToContinent.put("South Korea", "Asia");

        // America
        countryToContinent.put("United States", "America");
        countryToContinent.put("Canada", "America");
        countryToContinent.put("Brazil", "America");
        countryToContinent.put("Mexico", "America");
        countryToContinent.put("Argentina", "America");

        // Africa
        countryToContinent.put("Egypt", "Africa");
        countryToContinent.put("South Africa", "Africa");
        countryToContinent.put("Nigeria", "Africa");
        countryToContinent.put("Kenya", "Africa");
        countryToContinent.put("Morocco", "Africa");

        // Oceania
        countryToContinent.put("Australia", "Oceania");
        countryToContinent.put("New Zealand", "Oceania");
        countryToContinent.put("Fiji", "Oceania");
    }

    public String getContinent(String country) {
        return countryToContinent.get(country);
    }


    public void addTravel(User user, Travel travel) {
        travelData.computeIfAbsent(user.getUsername(), k -> new ArrayList<>()).add(travel);
        saveTravelData(); // Save immediately after adding a travel
    }

    public List<Travel> getTravelHistory(User user) {
        return travelData.getOrDefault(user.getUsername(), new ArrayList<>());
    }

    private void loadTravelData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 7) {
                    String username = parts[0];
                    String from = parts[1];
                    String to = parts[2];
                    String flightType = parts[3];
                    boolean visaRequired = Boolean.parseBoolean(parts[4]);
                    double cost = Double.parseDouble(parts[5]);
                    java.time.LocalDateTime date = java.time.LocalDateTime.parse(parts[6]);

                    Travel travel = new Travel(from, to, flightType, visaRequired);
                    travel.setCost(cost);
                    travel.setDate(date);
                    // Directly add to travelData without calling addTravel to avoid recursive save
                    travelData.computeIfAbsent(username, k -> new ArrayList<>()).add(travel);
                }
            }
        } catch (IOException e) {
            // File might not exist on first run, which is fine.
        }
    }

    public double calculateCost(String from, String to, String flightType, boolean visaRequired) {
        double baseCost = 100.0; // Base cost for any trip

        // Adjust cost based on distance/destination (dummy logic)
        if (getContinent(from) != null && getContinent(to) != null && !getContinent(from).equals(getContinent(to))) {
            baseCost += 300.0; // Intercontinental
        } else {
            baseCost += 100.0; // Intracontinental
        }

        // Adjust cost based on flight type
        switch (flightType) {
            case "Business":
                baseCost *= 1.5;
                break;
            case "First":
                baseCost *= 2.0;
                break;
            case "Economy":
            default:
                // No change
                break;
        }

        // Add visa cost if required
        if (visaRequired) {
            baseCost += 50.0;
        }

        return baseCost;
    }

    public Map<String, String> getCountryToContinentMap() {
        return countryToContinent;
    }

    public void saveTravelData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Map.Entry<String, List<Travel>> entry : travelData.entrySet()) {
                String username = entry.getKey();
                for (Travel travel : entry.getValue()) {
                    writer.println(
                            username + "|" + 
                            travel.getFrom() + "|" + 
                            travel.getTo() + "|" + 
                            travel.getFlightType() + "|" + 
                            travel.isVisaRequired() + "|" + 
                            travel.getCost() + "|" + 
                            travel.getDate()
                    );
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving travel data: " + e.getMessage());
        }
    }
}
