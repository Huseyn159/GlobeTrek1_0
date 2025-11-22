package controller;

import app.Main;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Travel;
import model.User;
import service.TravelService;

import java.time.LocalDateTime;
import java.util.List;

public class TravelHistoryController {

    @FXML
    private TableView<Travel> travelHistoryTable;

    @FXML
    private TableColumn<Travel, String> fromColumn;

    @FXML
    private TableColumn<Travel, String> toColumn;

    @FXML
    private TableColumn<Travel, String> flightTypeColumn;

    @FXML
    private TableColumn<Travel, Boolean> visaRequiredColumn;

    @FXML
    private TableColumn<Travel, Double> costColumn;

    @FXML
    private TableColumn<Travel, LocalDateTime> dateColumn;

    @FXML
    private Label noTripsLabel;

    private User loggedInUser;
    private TravelService travelService = TravelService.getInstance();

    @FXML
    public void initialize() {
        loggedInUser = Main.getLoggedInUser();

        fromColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFrom()));
        toColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTo()));
        flightTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFlightType()));
        visaRequiredColumn.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isVisaRequired()).asObject());
        costColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getCost()).asObject());
        dateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDate()));

        loadTravelHistory();
    }

    private void loadTravelHistory() {
        List<Travel> history = travelService.getTravelHistory(loggedInUser);
        if (history.isEmpty()) {
            travelHistoryTable.setVisible(false);
            noTripsLabel.setVisible(true);
        } else {
            travelHistoryTable.setItems(FXCollections.observableArrayList(history));
            travelHistoryTable.setVisible(true);
            noTripsLabel.setVisible(false);
        }
    }
}