
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


public class SelectFromVenueList extends Application {
    private ObservableList<String> selectFromVenueList = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
    	FileHandler fileHandler = new FileHandler();
		List<String> existingJobRequests = null;
		ScrollPane scrollPane = new ScrollPane();////
		
        primaryStage.setTitle("Select from Venue List");
        
        
        try {
			existingJobRequests = fileHandler.readJobRequestsFromFile("venues.csv");
		} catch (CustomException e) {
			System.out.println("Error: " + e.getMessage());
			return;
		}

        selectFromVenueList.addAll(existingJobRequests);

     // Create TableView
        TableView<String> tableView = new TableView<>();
        TableColumn<String, String> column = new TableColumn<>("Venue List");
        column.setCellValueFactory(data -> {
            String value = data.getValue();
            return value != null ? new SimpleStringProperty(value) : new SimpleStringProperty("");
        });
        tableView.getColumns().add(column);
        tableView.setItems(selectFromVenueList);

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

        Scene scene = new Scene(vbox, 800, 600);   
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void displayOldJobRequests(List<String> jobRequests) {
    	selectFromVenueList.addAll(jobRequests);
    }

    public static void main(String[] args) {
        Application.launch(args);
        
        
    }
}

