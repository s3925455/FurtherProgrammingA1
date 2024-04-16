package AT2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class SelectByCategory extends Application {

    private List<Venue> venues;

    // Constructor to initialize the list of venues
    public SelectByCategory(List<Venue> venues) {
        this.venues = venues;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Select Venue by Category");

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
        // Code to handle category selection and display venues for the selected category
        System.out.println("Selected Category: " + category);
        // Add your logic here to display venues based on the selected category
    }

}
