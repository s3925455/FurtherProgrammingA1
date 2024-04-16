package AT2;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;


public class DisplayOldJobRequests extends Application {
    private ObservableList<String> oldJobRequests = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
    	FileHandler fileHandler = new FileHandler();
		List<String> existingJobRequests = null;
		ScrollPane scrollPane = new ScrollPane();////
		
        primaryStage.setTitle("Old Job Requests");
        
        
        try {
			existingJobRequests = fileHandler.readJobRequestsFromFile("requests.csv");
		} catch (CustomException e) {
			System.out.println("Error: " + e.getMessage());
			return;
		}

		oldJobRequests.addAll(existingJobRequests);

     // Create TableView
        TableView<String> tableView = new TableView<>();
        TableColumn<String, String> column = new TableColumn<>("Job Requests");
        column.setCellValueFactory(data -> {
            String value = data.getValue();
            return value != null ? new SimpleStringProperty(value) : new SimpleStringProperty("");
        });
        tableView.getColumns().add(column);
        tableView.setItems(oldJobRequests);

        // Create Exit button
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(event -> {
            primaryStage.close();
            
        });
        
        

        // Layout
        VBox vbox = new VBox(10);
//Scrolling functionality
		scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);////
		scrollPane.setHbarPolicy(ScrollBarPolicy.ALWAYS);////
        
        vbox.getChildren().addAll(tableView, exitButton);

        Scene scene = new Scene(vbox, 400, 300);   
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void displayOldJobRequests(List<String> jobRequests) {
        oldJobRequests.addAll(jobRequests);
    }

    public static void main(String[] args) {
        Application.launch(args);
        
        
    }
}
