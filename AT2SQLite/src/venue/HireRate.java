package venue;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HireRate extends Application {

    private static String selectedVenueName;
    private static int selectedVenueRate;

    public static void main(String[] args) {
        launch(args); // Add this line to start the JavaFX application
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Venue Hire Rate");

        Label venueLabel = new Label("Selected Venue: " + selectedVenueName); // Use the stored name
        Label rateLabel = new Label("Rate: " + selectedVenueRate + "/hour"); // Use the stored rate

        Button hireButton = new Button("Hire");
        hireButton.setOnAction(event -> {
        	launchHireDetails(primaryStage, selectedVenueName,selectedVenueRate); // Pass the selected venue name to HireDetails
            showHiringMenu();
            
        });

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(venueLabel, rateLabel, hireButton);

        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showHiringMenu() {
        // Launch the ShowHiringMenu window
//    	HireDetails.setVenueDetails(selectedVenue.getName(), selectedVenue.getRate());
        ShowHiringMenu.showMenu(null);
    }

    public static void setVenueDetails(String name, int i) {
        selectedVenueName = name;
        selectedVenueRate = i;
        Object rateLabel = null;
		Object venueLabel = null;
		// Update labels if the UI is already initialized
        if (venueLabel != null && rateLabel != null) {
            ((Labeled) venueLabel).setText("Selected Venue: " + selectedVenueName);
            ((Labeled) rateLabel).setText("Rate: " + selectedVenueRate + "/hour");
            
            SaveHiringDetails1.setVenueDetails(selectedVenueName, selectedVenueRate);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void launchHireDetails(Stage primaryStage, String selectedVenueName, double selectedVenueRate) {
        try {
            HireDetails.setVenueName(selectedVenueName);
            HireDetails.setSelectedVenueRate(selectedVenueRate);
            HireDetails hireDetails = new HireDetails();
            hireDetails.start(primaryStage); // Start the HireDetails scene
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public static int getRate() {
		// TODO Auto-generated method stub
		return selectedVenueRate;
	}
}
