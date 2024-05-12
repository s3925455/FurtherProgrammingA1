//Version: Works with coma seprated displays

package venue;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DisplayOldJobRequests extends Application {
	private ObservableList<String> oldJobRequests = FXCollections.observableArrayList();

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Old Job Requests");

		// Create TableView
		TableView<String> tableView = new TableView<>();
		TableColumn<String, String> column = new TableColumn<>("Job Requests"); // Unique title for the column
		column.setCellValueFactory(data -> {
			String value = data.getValue();
			return value != null ? new SimpleStringProperty(value) : new SimpleStringProperty("");
		});
		column.setMinWidth(1000); // Set minimum width for the column
		tableView.getColumns().add(column);
		tableView.setItems(oldJobRequests);

		// Create Exit button
		Button exitButton = new Button("Exit");
		exitButton.setOnAction(event -> primaryStage.close());

		// Layout
		VBox vbox = new VBox(10);
		ScrollPane scrollPane = new ScrollPane(tableView);
		scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED); // Adjust as per your requirement
		vbox.getChildren().addAll(scrollPane, exitButton);

		Scene scene = new Scene(vbox, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.show();

		// Read job requests from SQLite database
		readJobRequestsFromDatabase();
	}

	private void readJobRequestsFromDatabase() {
		try (Connection connection = DriverManager
				.getConnection("jdbc:sqlite:/Users/amitmunjal/eclipse-workspace/FutureProgramWk9/application.db")) {
			String query = "SELECT requestId, client, title, artist, date, time, target, duration, type, category,venue FROM requests"; 
			try (PreparedStatement statement = connection.prepareStatement(query);
					ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					String requestId = resultSet.getString("requestId");
					String client = resultSet.getString("client");
					String title = resultSet.getString("title");
					String artist = resultSet.getString("artist");
					String date = resultSet.getString("date");
					String time = resultSet.getString("time");
					String target = resultSet.getString("target");
					String duration = resultSet.getString("duration");
					String type = resultSet.getString("type");
					String category = resultSet.getString("category");
					String venue = resultSet.getString("venue");

					// Concatenate the values with commas
					String jobRequest = requestId + ", " + client + ", " + title + ", " + artist + ", " + date + ", "
							+ time + ", " + target + ", " + duration + ", " + type + ", " + category + ", " + venue;
					oldJobRequests.add(jobRequest);
				}
			}
		} catch (SQLException e) {
			System.out.println("Error reading job requests from database: " + e.getMessage());
			e.printStackTrace(); // Print stack trace for detailed error information
		}
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}



/////-------------OPTION 2----------

//
//import javafx.application.Application;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.ScrollPane;
//import javafx.scene.control.ScrollPane.ScrollBarPolicy;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class DisplayOldJobRequests extends Application {
//    private final ObservableList<ObservableList<String>> oldJobRequests = FXCollections.observableArrayList();
//
//    @Override
//    public void start(Stage primaryStage) {
//        primaryStage.setTitle("Old Job Requests");
//
//        TableView<ObservableList<String>> tableView = new TableView<>();
//        readColumnNamesFromDatabase(tableView);
//        readJobRequestsFromDatabase(tableView);
//
//        Button exitButton = new Button("Exit");
//        exitButton.setOnAction(event -> primaryStage.close());
//
//        VBox vbox = new VBox(10);
//        ScrollPane scrollPane = new ScrollPane(tableView);
//        scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
//        scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
//        vbox.getChildren().addAll(scrollPane, exitButton);
//
//        Scene scene = new Scene(vbox, 800, 600);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//    private void readColumnNamesFromDatabase(TableView<ObservableList<String>> tableView) {
//        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:/Users/amitmunjal/eclipse-workspace/FutureProgramWk9/application.db")) {
//            String query = "PRAGMA table_info(requests)";
//            try (PreparedStatement statement = connection.prepareStatement(query);
//                 ResultSet resultSet = statement.executeQuery()) {
//                while (resultSet.next()) {
//                    String columnName = resultSet.getString("name");
//                    TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnName);
//                    final int columnIndex = resultSet.getRow();
//                    column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(columnIndex - 1)));
//                    tableView.getColumns().add(column);
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println("Error reading column names from database: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    private void readJobRequestsFromDatabase(TableView<ObservableList<String>> tableView) {
//        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:/Users/amitmunjal/eclipse-workspace/FutureProgramWk9/application.db")) {
//            String query = "SELECT requestId, client, title, artist, date, time, target, duration, type, category, venue FROM requests";
//            try (PreparedStatement statement = connection.prepareStatement(query);
//                 ResultSet resultSet = statement.executeQuery()) {
//                while (resultSet.next()) {
//                    String requestId = resultSet.getString("requestId");
//                    String client = resultSet.getString("client");
//                    String title = resultSet.getString("title");
//                    String artist = resultSet.getString("artist");
//                    String date = resultSet.getString("date");
//                    String time = resultSet.getString("time");
//                    String target = resultSet.getString("target");
//                    String duration = resultSet.getString("duration");
//                    String type = resultSet.getString("type");
//                    String category = resultSet.getString("category");
//                    String venue = resultSet.getString("venue");
//
//                    // Handle null values from the database
//                    requestId = (requestId != null) ? requestId : "";
//                    client = (client != null) ? client : "";
//                    title = (title != null) ? title : "";
//                    artist = (artist != null) ? artist : "";
//                    date = (date != null) ? date : "";
//                    time = (time != null) ? time : "";
//                    target = (target != null) ? target : "";
//                    duration = (duration != null) ? duration : "";
//                    type = (type != null) ? type : "";
//                    category = (category != null) ? category : "";
//                    venue = (venue != null) ? venue : "";
//
//                    ObservableList<String> rowData = FXCollections.observableArrayList(
//                            requestId, client, title, artist, date, time, target, duration, type, category, venue
//                    );
//
//                    oldJobRequests.add(rowData);
//                }
//                tableView.setItems(oldJobRequests);
//            }
//        } catch (SQLException e) {
//            System.out.println("Error reading job requests from database: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) {
//        Application.launch(args);
//    }
//}
//
