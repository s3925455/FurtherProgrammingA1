//Version: Baselined

package venue;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
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
        TableColumn<String, String> column = new TableColumn<>("Job Requests");
        column.setCellValueFactory(data -> {
            String value = data.getValue();
            return value != null ? new SimpleStringProperty(value) : new SimpleStringProperty("");
        });
        column.setMinWidth(1000);
        tableView.getColumns().add(column);
        tableView.setItems(oldJobRequests);

        // Create Pie Chart button
        Button pieChartButton = new Button("Pie Chart");
        pieChartButton.setOnAction(event -> openPieChart());

        // Create Bar Chart button
        Button barChartButton = new Button("Bar Chart");
        barChartButton.setOnAction(event -> openBarChart());

        // Create Exit button
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(event -> primaryStage.close());

        // Layout
        VBox vbox = new VBox(10);
        ScrollPane scrollPane = new ScrollPane(tableView);
        scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
        vbox.getChildren().addAll(scrollPane, pieChartButton, barChartButton, exitButton);

        Scene scene = new Scene(vbox, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Read job requests from SQLite database
        readJobRequestsFromDatabase();
    }

    private void readJobRequestsFromDatabase() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:/Users/amitmunjal/eclipse-workspace/FutureProgramWk9/application.db")) {
            String query = "SELECT requestId, client, title, artist, date, time, target, duration, type, category, venue FROM requests";
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

    private void openPieChart() {
        PieChartDisplay pieChartDisplay = new PieChartDisplay();
        Stage pieChartStage = new Stage();
        pieChartDisplay.start(pieChartStage);
    }

    private void openBarChart() {
        BarChartDisplay barChartDisplay = new BarChartDisplay();
        Stage barChartStage = new Stage();
        barChartDisplay.start(barChartStage);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
