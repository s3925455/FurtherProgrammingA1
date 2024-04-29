package venue;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class DisplayOrderSummary extends Application {

    private static List<String> orderSummaries = new ArrayList<>(); // Initialize the orderSummaries list

    public static void display(List<String> summaries) {
        orderSummaries.clear(); // Clear existing data
        orderSummaries.addAll(summaries); // Add new data
//        launch(); // Launch the JavaFX application
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Order Summary");

        // Create an ObservableList to hold the order summaries
        ObservableList<String> observableOrderSummaries = FXCollections.observableArrayList(orderSummaries);

        // Create a TableView to display the order summaries
        TableView<String> tableView = new TableView<>();
        TableColumn<String, String> summaryColumn = new TableColumn<>("Order Summary");
        summaryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue())); // Wrap String into ObservableValue
        tableView.getColumns().add(summaryColumn);
        tableView.setItems(observableOrderSummaries);

        // Check if orderSummaries is not empty
        if (orderSummaries.isEmpty()) {
            showAlert("No Order Summaries", "There are no order summaries to display.");
        }

        // Create an Exit button to close the window
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(event -> primaryStage.close());

        // Create a VBox layout to arrange the components
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(new Label("Order Summary:"), tableView, exitButton);

        Scene scene = new Scene(vbox, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Helper method to show an alert
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
