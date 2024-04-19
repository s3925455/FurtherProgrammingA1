package AT2;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import AT2.SelectFromVenueList.VenueItem;

public class SelectByCategory extends Application {

    private List<Venue> venues = new ArrayList<>();
    private ObservableList<VenueItem> venueItems = FXCollections.observableArrayList();
    private List<String> columnHeadings = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Select Venue by Category");

        // Read venues from CSV file and populate the TableView
        readVenuesFromFile("venues.csv");

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
                selectedVenues.add(venue);
            }
        }

        if (selectedVenues.isEmpty()) {
            showAlert("No Venues Available", "There are no venues available for the selected category: " + category);
        } else {
            displayVenues(selectedVenues);
        }
    }

    private void displayVenues(List<Venue> venues) {
        Stage venueStage = new Stage();
        venueStage.setTitle("Available Venues");

        VBox vbox = new VBox(10);

        int uniqueId = 1;

        for (Venue venue : venues) {
            String venueInfo = uniqueId + " - " + venue.getName();

            Label venueLabel = new Label(venueInfo);
            vbox.getChildren().add(venueLabel);

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
                HireRate.setVenueDetails(selectedVenue.getName(), selectedVenue.getRate());
                Stage primaryStage = null;
				launchHireRate(primaryStage);
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

    private void readVenuesFromFile(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                // Assuming data order is: name, capacity, category, suitableFor
                venues.add(new Venue(data[0], data[1], data[2], data[3], 0));
//                venues.add(new Venue(data[0], data[1], data[2], data[3], Integer.parseInt(data[4])));

            }

            scanner.close();
        } catch (FileNotFoundException e) {
            showAlert("Error", "Failed to read venues from file: " + filename);
        }
    }

    
    
    
    public static void main(String[] args) {
        launch(args);
    }
    
    private void launchHireRate(Stage primaryStage) {
        try {
        	HireRate hireRate = new HireRate();
        	hireRate.start(new Stage()); // Create a new stage for SelectFromVenueList
        } catch (Exception e) {
            e.printStackTrace();
        }
}
}
