package venue;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectFromVenueList extends Application {
    private ObservableList<Venue> venues = FXCollections.observableArrayList();
    private ObservableList<String> selectedVenues = FXCollections.observableArrayList();
    private TextField userInput;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Select from Venue List");

        // Read venues from SQLite database
        readVenuesFromDatabase();

        // Create TableView
        TableView<Venue> tableView = new TableView<>();

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

        TableColumn<Venue, String> suitableForColumn = new TableColumn<>("Suitable For");
        suitableForColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSuitableFor()));
        tableView.getColumns().add(suitableForColumn);

        TableColumn<Venue, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));
        tableView.getColumns().add(categoryColumn);

        TableColumn<Venue, Integer> rateColumn = new TableColumn<>("Rate");
        rateColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getRate()).asObject());
        tableView.getColumns().add(rateColumn);

        tableView.setItems(venues);

        // Create input box for valid number
        userInput = new TextField();
        userInput.setPromptText("Enter a valid number");

        // Create Clear button
        Button clearButton = new Button("Clear");
        clearButton.setOnAction(event -> userInput.clear());

        // Create OK button
        Button okButton = new Button("OK");
        okButton.setOnAction(event -> handleUserInput());

        // Create Exit button
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(event -> primaryStage.close());

        // Layout
        VBox vbox = new VBox(20);
        HBox hbox = new HBox(20);
        ScrollPane scrollPane = new ScrollPane(tableView);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        hbox.getChildren().addAll(userInput, clearButton, okButton, exitButton);
        vbox.getChildren().addAll(scrollPane, hbox);

        Scene scene = new Scene(vbox, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
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
                    Venue venue = new Venue(venueId, name, capacity, suitableFor, category, rate);
                    venues.add(venue);
                }
            }
        } catch (SQLException e) {
            showAlert("Error", "Failed to read venues from database.");
            e.printStackTrace(); // Print stack trace for detailed error information
        }
    }

    private void handleUserInput() {
        String input = userInput.getText().trim();
        try {
            int selectedIndex = Integer.parseInt(input);
            if (selectedIndex >= 1 && selectedIndex <= venues.size()) {
                Venue selectedVenue = venues.get(selectedIndex - 1);
                String selectedVenueName = selectedVenue.getName();
                int selectedVenueRate = selectedVenue.getRate();
                selectedVenues.add(selectedVenueName);
                userInput.clear(); // Clear input box

                HireRate.setVenueDetails(selectedVenueName, selectedVenueRate);
                Stage primaryStage = null;
                launchHireRate(primaryStage);
            } else {
                showAlert("Invalid Input", "Please enter a valid number from the list.");
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter a valid number.");
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
        Application.launch(args);
    }

    private void launchHireRate(Stage primaryStage) {
        try {
            HireRate hireRate = new HireRate();
            hireRate.start(new Stage()); // Create a new stage for HireRate
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
