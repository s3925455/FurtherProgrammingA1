package AT2;

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
    private static String selectedVenueRate;

    public static void main(String[] args) {
        launch(args); // Add this line to start the JavaFX application
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Venue Hire Rate");

        Label venueLabel = new Label("Selected Venue: " + selectedVenueName); // Use the stored name
        Label rateLabel = new Label("Rate: " + selectedVenueRate + "/hour"); // Use the stored rate

        Button hireButton = new Button("Hire");
        hireButton.setOnAction(event -> showHiringMenu());

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(venueLabel, rateLabel, hireButton);

        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static void showHiringMenu() {
        // Launch the ShowHiringMenu window
        ShowHiringMenu.showMenu(null);
    }

    public static void setVenueDetails(String name, String string) {
        selectedVenueName = name;
        selectedVenueRate = string;
        Object selectedVenueLabel = null;
		Labeled selectedRateLabel = null;
		// Update labels if the UI is already initialized
        if (selectedVenueLabel != null && selectedRateLabel != null) {
            ((Labeled) selectedVenueLabel).setText("Selected Venue: " + selectedVenueName);
            selectedRateLabel.setText("Rate: " + selectedVenueRate + "/hour");
     
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
