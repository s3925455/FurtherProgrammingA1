package AT2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HireRate extends Application {

    private Venue selectedVenue;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Venue Hire Rate");

        // Simulate selection of a venue
        selectedVenue = new Venue("Example Venue", "$550.00", "Example Address", "Example Description");

        Label venueLabel = new Label("Selected Venue: " + selectedVenue.getName());
        Label rateLabel = new Label("Rate: " + selectedVenue.getRate() + "/hour");

        Button hireButton = new Button("Hire");
        hireButton.setOnAction(event -> showHiringMenu());

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(venueLabel, rateLabel, hireButton);

        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showHiringMenu() {
        // Launch the ShowHiringMenu window and pass the selected venue
        ShowHiringMenu.showMenu(selectedVenue);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
