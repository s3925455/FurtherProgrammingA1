package venue;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class HireDetails extends Application {

	private static String selectedVenueName;
	private static double selectedVenueRate;
	private static List<String> orderSummaries = new ArrayList<>(); // List to hold order summaries
	private static List<String> finalSummaries = new ArrayList<>(); // List to hold final order summaries

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Hiring Details");

		Label venueLabel = new Label("Selected Venue: " + selectedVenueName); // Display the selected venue name
		Label rateLabel = new Label("Venue Rate: " + selectedVenueRate); // Display the selected venue rate

		Label requestLabel = new Label("Request Number:");
		TextField requestField = new TextField();

		Label requesterLabel = new Label("Requester Name:");
		TextField requesterField = new TextField();

		Label eventNameLabel = new Label("Event Name:");
		TextField eventNameField = new TextField();

		Label artistNameLabel = new Label("Artist Name:");
		TextField artistNameField = new TextField();

		Label dateLabel = new Label("Date (dd/mm/yyyy):");
		TextField dateField = new TextField();

		Label timeLabel = new Label("Time (hh:mmam/pm):");
		TextField timeField = new TextField();

		Label attendanceLabel = new Label("Number of Attendees:");
		TextField attendanceField = new TextField();

		Label hoursLabel = new Label("Number of Hours:");
		TextField hoursField = new TextField();

		Label eventTypeLabel = new Label("Event Type:");
		TextField eventTypeField = new TextField();

		Label categoryTypeLabel = new Label("Category Type:");
		TextField categoryTypeField = new TextField();

		Button confirmButton = new Button("Confirm");
		confirmButton.setOnAction(event -> handleConfirmation(requesterField.getText(), eventNameField.getText(),
				artistNameField.getText(), dateField.getText(), timeField.getText(), attendanceField.getText(),
				hoursField.getText(), eventTypeField.getText(), categoryTypeField.getText()));

		Button exitButton = new Button("Exit"); // Add Exit button
		exitButton.setOnAction(event -> primaryStage.close()); // Close the stage on Exit button click

		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(20));
		vbox.getChildren().addAll(venueLabel, rateLabel, requestLabel, requestField, hoursLabel, hoursField, dateLabel,
				dateField, timeLabel, timeField, eventNameLabel, eventNameField, artistNameLabel, artistNameField, attendanceLabel,attendanceField,
				requesterLabel, requesterField, eventTypeLabel, eventTypeField, categoryTypeLabel,categoryTypeField, confirmButton, exitButton);

		// Add the VBox to a ScrollPane
		ScrollPane scrollPane = new ScrollPane(vbox);
		scrollPane.setFitToWidth(true); // Allow the scroll pane to resize horizontally

		Scene scene = new Scene(scrollPane, 400, 800); // Use the scroll pane in the scene
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void handleConfirmation(String requesterName, String eventName, String artistName, String date, String time,
			String attendance, String numberOfHours, String eventType, String category) {
		if (requesterName.isEmpty() || eventName.isEmpty() || artistName.isEmpty() || date.isEmpty() || time.isEmpty()
				|| attendance.isEmpty() || numberOfHours.isEmpty() || eventType.isEmpty() || category.isEmpty()) {
			showAlert("Invalid Input", "Please fill in all fields.");
			return;
		}

		if (!DateValidator.isValidDate(date)) {
			showAlert("Invalid Date", "Please enter a valid date in dd/mm/yyyy format.");
			return;
		}

		if (!TimeValidator.isValidTime(time)) {
			showAlert("Invalid Time", "Please enter a valid time in hh:mmam/pm format.");
			return;
		}

		try {
			int hours = Integer.parseInt(numberOfHours);
			int attandence = Integer.parseInt(attendance);

			// Proceed with handling the confirmation
			// Add the order summary to the list
			orderSummaries.add(generateOrderSummary(requesterName, hours, date, time, eventName, artistName,
					requesterName, eventType));

			//Final order summary for saving
			finalSummaries.add(generateFinalSummary(requesterName, eventName, artistName, date, time, attendance, hours,
					eventType, category));

			showAlert("Order Confirmed", "Hiring details saved successfully.");

			// Launch the DisplayOrderSummary class with order summaries
			DisplayOrderSummary.display(orderSummaries);
		} catch (NumberFormatException e) {
			showAlert("Invalid Input", "Please enter valid numbers for hours and attendance.");
		}
	}

	private String generateFinalSummary(String requesterName, String eventName, String artistName, String date,
			String time, String attendance, int hours, String eventType, String category) {
		// TODO Auto-generated method stub
		return null;
	}

	private String generateOrderSummary(String requestNumber, int numberOfHours, String date, String time,
			String eventName, String artistName, String requesterName, String eventType) {
		return String.format(
				"\n Order Summary\n" + "--------------------------------------------------\n" + "Request No: %s\n"
						+ "Client: %s\n" + "Venue: %s\n" + "Event name: %s\n" + "Artist: %s\n"
						+ "Event date (dd/mm/yyyy): %s\n" + "Event time: %s\n"
						+ "%d hours venue hire @ $%d - Total Cost: $%.2f\n" + "Brokering commission 10%%: $%.2f\n",
				requestNumber, requesterName, selectedVenueName, eventName, artistName, date, time, numberOfHours,
				numberOfHours * HireRate.getRate(), (double) (numberOfHours * HireRate.getRate()),
				(double) (numberOfHours * HireRate.getRate()) * 0.1);
	}

	private void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public static void setVenueName(String selectedVenueName) {
		HireDetails.selectedVenueName = selectedVenueName;
	}

	public static void setSelectedVenueRate(double selectedVenueRate) {
		HireDetails.selectedVenueRate = selectedVenueRate;
	}
	
	public static void main(String[] args) {
	    Application.launch(args);
	}

	public static int getRate() {
		// TODO Auto-generated method stub
		return 0;
	}

}
