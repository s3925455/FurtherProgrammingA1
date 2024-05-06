package venue;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SelectFromMatchingList extends Application {

    @FXML
    private TableView<Venue> table1= new TableView<>(); // Inject TableView from FXML

    private List<Venue> venues = new ArrayList<>(); // Initialize the list

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Selection.fxml"));
        
        VBox root = loader.load();

        // Get the controller from the loader to access injected components
        SelectFromMatchingListController controller = loader.getController();

        // Now you can access table1 from the controller
        TableView<Venue> tableView = controller.getTable1();

        // Create TableColumns for venue properties
        TableColumn<Venue, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getVenueId()).asObject());
        tableView.getColumns().add(idColumn);

        TableColumn<Venue, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        tableView.getColumns().add(nameColumn);

        TableColumn<Venue, Integer> capacityColumn = new TableColumn<>("Capacity");
        capacityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCapacity()).asObject());
        tableView.getColumns().add(capacityColumn);

        // Add other TableColumn definitions as needed

        // Read venues from SQLite database
        readVenuesFromDatabase();

        // Convert ArrayList to ObservableList
        ObservableList<Venue> venueList = FXCollections.observableArrayList(venues);
        tableView.setItems(venueList);

        Scene scene = new Scene(root, 920, 620);
        primaryStage.setScene(scene);
        primaryStage.setTitle("MatchMaker");
        primaryStage.show();
        System.out.println("FXML loaded successfully"); // Debugging
    }

    private void readVenuesFromDatabase() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:/Users/amitmunjal/eclipse-workspace/FutureProgramWk9/application.db")) {
            String query = "SELECT * FROM venues"; // Assuming the table name is 'venues'
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int venueId = resultSet.getInt("venueId");
                    String name = resultSet.getString("name");
                    int capacity = resultSet.getInt("capacity");
                    String suitableFor = resultSet.getString("suitableFor");
                    String category = resultSet.getString("category");
                    int rate = resultSet.getInt("rate");
                    System.out.println("Venue ID: " + venueId + ", Name: " + name + ", Capacity: " + capacity + ", Suitable For: " + suitableFor + ", Category: " + category + ", Rate: " + rate);
                    Venue venue = new Venue(venueId, name, capacity, suitableFor, category, rate);
                    venues.add(venue);
                }
            } catch (SQLException e) {
                showAlert("Error", "Failed to read venues from database.");
                e.printStackTrace(); // Print stack trace for detailed error information
            }
        } catch (SQLException e) {
            showAlert("Error", "Failed to establish connection with the database.");
            e.printStackTrace(); // Print stack trace for detailed error information
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
