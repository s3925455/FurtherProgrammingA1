package AT2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class SelectByCategory extends Application {

    private List<Venue> venues;

    public SelectByCategory(Object object) {
		// TODO Auto-generated constructor stub
	}

	@Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Select Venue by Category");

        FileHandler fileHandler = new FileHandler();
        try {
            venues = fileHandler.readVenuesFromFile("venues.csv");
        } catch (CustomException e) {
            showAlert("Error", e.getMessage());
            return;
        }

        // Create components
        Label categoryLabel = new Label("Select Category:");
        Button outdoorButton = new Button("Outdoor");
        Button indoorButton = new Button("Indoor");
        Button convertibleButton = new Button("Convertible");
        Button exitButton = new Button("Exit");

        // Set actions for buttons
        outdoorButton.setOnAction(event -> selectCategory("Outdoor"));
        indoorButton.setOnAction(event -> selectCategory("Indoor"));
        convertibleButton.setOnAction(event -> selectCategory("Convertible"));
        exitButton.setOnAction(event -> primaryStage.close());

        // Layout
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(categoryLabel, outdoorButton, indoorButton, convertibleButton, exitButton);

        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void selectCategory(String category) {
        System.out.println("Selected Category: " + category);

        List<Venue> selectedVenues = new ArrayList<>();
        
        for (Venue venue : venues) {
            if (venue.getCategory().equalsIgnoreCase(category)) {
                selectedVenues.add( venue);
            }
        }

        if (selectedVenues.isEmpty()) {
            showAlert("No Venues Available", "There are no venues available for the selected category.");
        } else {
            displayVenues(selectedVenues);

        }
    }

    private void displayVenues(List<Venue> venues) {
        Stage venueStage = new Stage();
        venueStage.setTitle("Available Venues");

        VBox vbox = new VBox(10);

        // Initialize a counter for the unique IDs
        int uniqueId = 1;

        for (Venue venue : venues) {
            // Format the venue information with a unique ID
//            String venueInfo = uniqueId + ": " + venue.getId() + " - " + venue.getName();
            String venueInfo = uniqueId + " - " + venue.getName();

            Label venueLabel = new Label(venueInfo);
            vbox.getChildren().add(venueLabel);

            // Increment the unique ID for the next record
            uniqueId++;
        }

        TextField userInput = new TextField();
        userInput.setPromptText("Enter venue ID");

        Button okButton = new Button("OK");
        okButton.setOnAction(event -> handleUserInput(userInput.getText(), venues, venueStage));

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(event -> venueStage.close());

        vbox.getChildren().addAll(new Label("Enter venue ID:"), userInput, okButton, exitButton);

        Scene scene = new Scene(vbox, 300, 400);
        venueStage.setScene(scene);
        venueStage.show();
    }
    private void handleUserInput(String userInput, List<Venue> venues, Stage venueStage) {
        try {
            int selectedId = Integer.parseInt(userInput);
            if (selectedId >= 1 && selectedId <= venues.size()) {
                Venue selectedVenue = venues.get(selectedId - 1);
                showAlert("Venue Selected", "You selected: " + selectedVenue.getName());
            } else {
                showAlert("Error", "Invalid venue ID. Please enter a valid ID.");
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid input. Please enter a valid venue ID.");
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
