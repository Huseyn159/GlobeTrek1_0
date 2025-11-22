package model;

import java.time.LocalDateTime;

public class Travel {

    private String from;
    private String to;
    private boolean visaRequired;
    private String flightType;
    private double cost;
    private LocalDateTime date;

    public Travel(String from, String to, String flightType, boolean visaRequired) {
        this.from = from;
        this.to = to;
        this.flightType = flightType;
        this.visaRequired = visaRequired;
        this.date = LocalDateTime.now();
    }

    // setters
    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    // getters
    public String getFrom() { return from; }

    public String getTo() { return to; }

    public boolean isVisaRequired() { return visaRequired; }

    public String getFlightType() { return flightType; }

    public double getCost() { return cost; }

    public LocalDateTime getDate() { return date; }

    @Override
    public String toString() {
        return "🌍 " + from + " → " + to +
                " | ✈ " + flightType +
                " | 💵 $" + cost +
                " | 🛂 Visa: " + (visaRequired ? "Yes" : "No") +
                " | 📅 " + date;
    }
}

