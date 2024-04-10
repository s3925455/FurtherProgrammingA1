package AT2;

import AT2.Venue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Optional;

public class SelectFromVenueList {
    private ObservableList<Venue> venues = FXCollections.observableArrayList();

    public SelectFromVenueList(List<Venue> venueList) {
        venues.addAll(venueList);
    }

    public Venue selectFromVenueList() {
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
                return selectedVenue;
            }
        }

        return null;
    }
}
