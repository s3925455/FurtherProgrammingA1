package AT2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class VenueMatcher extends Application {
	private List<String> oldJobRequests = new ArrayList<>(); // Declare old job requests list

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
		Button searchByName = new Button("Search venue by name");
		Button autoMatchEvents = new Button("Auto-match events with suitable venues");
		Button showOrderSummary = new Button("Show order summary");
		Button exitButton = new Button("Exit");

		listJobRequests.setOnAction(event -> handleUserInput("1", primaryStage));
		browseByCategory.setOnAction(event -> handleUserInput("2", primaryStage));
		searchByName.setOnAction(event -> handleUserInput("3", primaryStage));
		autoMatchEvents.setOnAction(event -> handleUserInput("4", primaryStage));
		showOrderSummary.setOnAction(event -> handleUserInput("5", primaryStage));
		exitButton.setOnAction(event -> primaryStage.close());

		vbox.getChildren().addAll(listJobRequests, browseByCategory, searchByName, autoMatchEvents, showOrderSummary,
				exitButton);

		Scene scene = new Scene(vbox, 400, 400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void handleUserInput(String input, Stage primaryStage) {
		try {
			int choice = Integer.parseInt(input);
			switch (choice) {
			case 1:
				launchDisplayOldJobRequest(primaryStage); // Launch DisplayOldJobRequests
				break;
			case 2:
				System.out.println("Handling option 2: Browse venue by category...");
				break;
			case 3:
				System.out.println("Handling option 3: Search venue by name...");
				break;
			case 4:
				System.out.println("Handling option 4: Auto-match events with suitable venues...");
				break;
			case 5:
				System.out.println("Handling option 5: Show order summary...");
				break;
			case 6:
				System.out.println("Exiting...");
				System.exit(0);
				break;
			default:
				showAlert("Invalid Input", "Please enter a valid menu option.");
			}
		} catch (NumberFormatException e) {
			showAlert("Error", "Please enter a valid number.");
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

	private void launchDisplayOldJobRequest(Stage primaryStage) {
		try {
			DisplayOldJobRequests displayOldJobRequest = new DisplayOldJobRequests();
			displayOldJobRequest.start(new Stage()); // Create a new stage for DisplayOldJobRequests
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
