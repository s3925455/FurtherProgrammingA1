package AT2;

import AT2.Venue;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HireDetails extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hiring Details");

        Label requestLabel = new Label("Request Number:");
        TextField requestField = new TextField();

        Label hoursLabel = new Label("Number of Hours:");
        TextField hoursField = new TextField();

        Label dateLabel = new Label("Date (dd/mm/yyyy):");
        TextField dateField = new TextField();

        Label timeLabel = new Label("Time (hh:mmam/pm):");
        TextField timeField = new TextField();

        Label eventNameLabel = new Label("Event Name:");
        TextField eventNameField = new TextField();

        Label artistNameLabel = new Label("Artist Name:");
        TextField artistNameField = new TextField();

        Label requesterLabel = new Label("Requester Name:");
        TextField requesterField = new TextField();

        Label eventTypeLabel = new Label("Event Type:");
        TextField eventTypeField = new TextField();

        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction(event -> handleConfirmation(requestField.getText(), hoursField.getText(),
                dateField.getText(), timeField.getText(), eventNameField.getText(), artistNameField.getText(),
                requesterField.getText(), eventTypeField.getText()));

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(requestLabel, requestField, hoursLabel, hoursField, dateLabel, dateField,
                timeLabel, timeField, eventNameLabel, eventNameField, artistNameLabel, artistNameField,
                requesterLabel, requesterField, eventTypeLabel, eventTypeField, confirmButton);

        Scene scene = new Scene(vbox, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleConfirmation(String requestNumber, String numberOfHours, String date, String time,
            String eventName, String artistName, String requesterName, String eventType) {
        if (!DateValidator.isValidDate(date)) {
            showAlert("Invalid Date", "Please enter a valid date in dd/mm/yyyy format.");
            return;
        }

        if (!TimeValidator.isValidTime(time)) {
            showAlert("Invalid Time", "Please enter a valid time in hh:mmam/pm format.");
            return;
        }

        // Proceed with hiring details if validation passes
        saveHiringDetails(requestNumber, numberOfHours, date, time, eventName, artistName, requesterName, eventType);
        showAlert("Order Confirmed", "Hiring details saved successfully.");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void saveHiringDetails(String requestNumber, String numberOfHours, String date, String time,
            String eventName, String artistName, String requesterName, String eventType) {
        // Save hiring details logic here
        System.out.println("Request Number: " + requestNumber);
        System.out.println("Number of Hours: " + numberOfHours);
        System.out.println("Date: " + date);
        System.out.println("Time: " + time);
        System.out.println("Event Name: " + eventName);
        System.out.println("Artist Name: " + artistName);
        System.out.println("Requester Name: " + requesterName);
        System.out.println("Event Type: " + eventType);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void showHiringMenu() {
        String args = null;
		launch(args);
    }
}
