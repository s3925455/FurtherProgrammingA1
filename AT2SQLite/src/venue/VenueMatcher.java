package venue;

import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TextInputControl;

public class VenueMatcher extends Application {

    private String username; // Added username field

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Venue Matcher Menu");

        // Create components
        TextField userInput = new TextField();
        userInput.setPromptText("Enter your choice");
        Button okButton = new Button("OK");
        Button cancelButton = new Button("Cancel");

        // Set actions for buttons
        okButton.setOnAction(event -> {
            String input = userInput.getText();
            handleUserInput(input, primaryStage); // Pass primaryStage to handleUserInput
            userInput.clear(); // Clear input field after handling input
        });

        cancelButton.setOnAction(event -> primaryStage.close());

        // Layout
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));

        // Add menu options
        Button listJobRequests = new Button("List current job requests");
        Button browseByCategory = new Button("Browse venue by category");
        Button searchByName = new Button("Search by venue");
        Button autoMatchEvents = new Button("Auto-match events with suitable venues");
        Button showOrderSummary = new Button("Show order summary");
        Button showUserAdmin = new Button("User Management");
        Button saveExitButton = new Button("Save and Exit");
        Button exitButton = new Button("Exit");

        listJobRequests.setOnAction(event -> handleUserInput("1", primaryStage));
        browseByCategory.setOnAction(event -> handleUserInput("2", primaryStage));
        searchByName.setOnAction(event -> handleUserInput("3", primaryStage));
        autoMatchEvents.setOnAction(event -> handleUserInput("4", primaryStage));
        showOrderSummary.setOnAction(event -> handleUserInput("5", primaryStage));
        showUserAdmin.setOnAction(event -> handleUserInput("7", primaryStage));
        saveExitButton.setOnAction(event -> handleUserInput("8", primaryStage)); // Changed from "8" to match Save and Exit
        exitButton.setOnAction(event -> primaryStage.close());

        // Check if user is admin to show additional buttons
        if ("admin".equals(username)) { // Fixed the if condition
            Button showBackup = new Button("Backup: export/import file");

            showBackup.setOnAction(event -> handleUserInput("6", primaryStage));

            vbox.getChildren().addAll(listJobRequests, browseByCategory, searchByName, autoMatchEvents,
                    showOrderSummary, showBackup, showUserAdmin, saveExitButton, exitButton);
        } else {
            vbox.getChildren().addAll(listJobRequests, browseByCategory, searchByName, autoMatchEvents,
                    showOrderSummary, showUserAdmin, saveExitButton, exitButton);
        }

        Scene scene = new Scene(vbox, 1000, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Added setUsername method
    public void setUsername(String username) {
        this.username = username;
    }

    private void handleUserInput(String input, Stage primaryStage) {
        try {
            int choice = Integer.parseInt(input);
            switch (choice) {
                case 1:
                    launchDisplayOldJobRequest(primaryStage);
                    break;
                case 2:
                    launchSelectByCategory(primaryStage);
                    break;
                case 3:
                    launchSelectFromVenueList(primaryStage);
                    break;
                case 4:
                    System.out.println("Handling option 4: Auto-match events with suitable venues...");
                    launchSelectFromMatchingList(primaryStage);
                    break;
                case 5:
                    System.out.println("Handling option 5: Show order summary...");
                    displayOrderSummary(primaryStage);
                    break;
                case 6:
                    System.out.println("Handling option 6: Backup: export/import file...");
                    showBackup(primaryStage);
                    break;
                case 7:
                    System.out.println("Handling option 7: Add/Delete/Update user...");
                    userAdmin(primaryStage);
                    break;
                
                case 8:
                    System.out.println("Venue Matcher : Option 8: Save and Exit...");//DeBug statement
                   
                    List<String> finalSummaries = HireDetails.getFinalSummary();

                    System.out.println("Venue Matcher: Final Summaries size: " + HireDetails.getFinalSummary().size()); // Debug: Print size of finalSummaries list
                    for (String summary : finalSummaries) {
                        System.out.println(summary);
                    }
                    try {
                        FileHandler fileHandler = new FileHandler();
                        fileHandler.saveToDatabase(HireDetails.getFinalSummary());
                        showAlert1("Success", "Records added to database");
                    } catch (CustomException e) {
                        showAlert1("Error", "Error saving data: " + e.getMessage());
                    } finally {
                        System.out.println("Exiting...");
                        primaryStage.close();
                    }
                    break;
                    
          
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter a valid number.");
        }
    }

    // Show Alert methods
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showAlert1(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    ///--------------------OTHER METHOS TO MANAGE USER SELECTION-------------
    
    private void launchDisplayOldJobRequest(Stage primaryStage) {
        try {
            DisplayOldJobRequests displayOldJobRequest = new DisplayOldJobRequests();
            displayOldJobRequest.start(new Stage()); // Create a new stage for DisplayOldJobRequests
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void launchSelectByCategory(Stage primaryStage) {
        try {
            SelectByCategory selectByCategory = new SelectByCategory();
            selectByCategory.start(new Stage()); // Create a new stage for SelectByCategory
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void launchSelectFromVenueList(Stage primaryStage) {
        try {
            SelectFromVenueList selectFromVenueList = new SelectFromVenueList();
            selectFromVenueList.start(new Stage()); // Create a new stage for SelectFromVenueList
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void launchSelectFromMatchingList(Stage primaryStage) {
        try {
            SelectFromMatchingList selectFromMatchingList = new SelectFromMatchingList();
            selectFromMatchingList.start(new Stage()); // Create a new stage for Display fromMatching list
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayOrderSummary(Stage primaryStage) {
        try {
            DisplayOrderSummary displayOrderSummary = new DisplayOrderSummary();
            displayOrderSummary.start(new Stage()); // Create a new stage for Display Order Summary
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showBackup(Stage primaryStage) {
        try {
            ShowBackup showBackup = new ShowBackup();
            showBackup.start(new Stage()); // Create a new stage for Display Order Summary
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void userAdmin(Stage primaryStage) {
        try {
            UserAdmin userAdmin = new UserAdmin();
            userAdmin.start(new Stage()); // Create a new stage for UserAdmin
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ClearOrderSummaries() {
        Object orderSummaries = null;
        ((TextInputControl) orderSummaries).clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    
}
