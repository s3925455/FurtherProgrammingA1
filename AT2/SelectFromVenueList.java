package AT2;

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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SelectFromVenueList extends Application {
    private ObservableList<VenueItem> venueItems = FXCollections.observableArrayList();
    private ObservableList<String> selectedVenues = FXCollections.observableArrayList();
    private TextField userInput;
    private List<String> columnHeadings = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Select from Venue List");

        // Read venues from CSV file and populate the TableView
        readVenuesFromFile("venues.csv");

        // Create TableView
        TableView<VenueItem> tableView = new TableView<>();

        // Create TableColumns dynamically based on column headings
        for (int i = 0; i < columnHeadings.size(); i++) {
            final int columnIndex = i;
            TableColumn<VenueItem, String> column = new TableColumn<>(columnHeadings.get(i));
            column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getData(columnIndex)));
            tableView.getColumns().add(column);
        }

        // Create a column for Unique ID
        TableColumn<VenueItem, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        tableView.getColumns().add(0, idColumn); // Insert at index 0

        tableView.setItems(venueItems);

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
        VBox vbox = new VBox(10);
        HBox hbox = new HBox(10);
        ScrollPane scrollPane = new ScrollPane(tableView);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        hbox.getChildren().addAll(userInput, clearButton, okButton, exitButton);
        vbox.getChildren().addAll(scrollPane, hbox);

        Scene scene = new Scene(vbox, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void readVenuesFromFile(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);

            // Read column headings from the first line
            if (scanner.hasNextLine()) {
                String[] headings = scanner.nextLine().split(",");
                for (String heading : headings) {
                    columnHeadings.add(heading);
                }
            }

            // Read venue data and populate VenueItems
            int number = 1;
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                venueItems.add(new VenueItem(number, data));
                number++;
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            showAlert("Error", "Failed to read venues from file: " + filename);
        }
    }

    private void handleUserInput() {
        String input = userInput.getText().trim();
        try {
            int selectedIndex = Integer.parseInt(input);
            if (selectedIndex >= 1 && selectedIndex <= venueItems.size()) {
                String selectedVenue = venueItems.get(selectedIndex - 1).getVenue();
                selectedVenues.add(selectedVenue);
                userInput.clear(); // Clear input box
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

    // Class representing a venue item with a unique ID, number, and data array
    public static class VenueItem {
        private final int id;
        private final int number;
        private final String[] data;

        public VenueItem(int number, String[] data) {
            this.id = number; // Assign ID as number initially
            this.number = number;
            this.data = data;
        }

        public int getId() {
            return id;
        }

        public int getNumber() {
            return number;
        }

        public String getData(int index) {
            return data[index];
        }

        public String getVenue() {
            return data[1]; // Assuming index 1 is the venue name in your CSV
        }
    }
}
