package AT2;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;

public class SelectFromVenueList extends Application {
    private ObservableList<Venue> venues = FXCollections.observableArrayList();

    public SelectFromVenueList(List<Venue> venueList) {
        if (venueList != null) {
            venues.addAll(venueList);
        }
    }

    @Override
    public void start(Stage primaryStage) {
        System.out.println("\n--------------------------------------------------");
        System.out.println("3. Select from venue list");
        System.out.println("--------------------------------------------------");

        ListView<Venue> venueListView = new ListView<>(venues);

        // Create a dialog box to display the venue list
        Alert venueDialog = new Alert(Alert.AlertType.CONFIRMATION);
        venueDialog.setTitle("Select Venue");
        venueDialog.setHeaderText("Select a venue from the list:");
        venueDialog.getDialogPane().setContent(new VBox(venueListView));
        venueDialog.getButtonTypes().clear();
        venueDialog.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        venueDialog.showAndWait();

        Optional<ButtonType> result = Optional.ofNullable(venueDialog.getResult());
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Venue selectedVenue = venueListView.getSelectionModel().getSelectedItem();
            if (selectedVenue != null) {
                System.out.println("Selected Venue: " + selectedVenue.getName());
                primaryStage.close(); // Close the dialog
            } else {
                showAlert("Invalid Selection", "Please select a venue from the list.");
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
