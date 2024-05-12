package venue;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PieChartDisplay extends Application {
    private Map<String, Integer> venueUsageMap = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Venue Usage Pie Chart");

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(event -> primaryStage.close());

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(exitButton);

        PieChart pieChart = new PieChart();
        vbox.getChildren().add(pieChart);

        Scene scene = new Scene(vbox, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        readJobRequestsFromDatabase();
        displayPieChart(pieChart);
    }

    private void readJobRequestsFromDatabase() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:/Users/amitmunjal/eclipse-workspace/FutureProgramWk9/application.db")) {
            String query = "SELECT venue FROM requests";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String venue = resultSet.getString("venue");
                    venueUsageMap.put(venue, venueUsageMap.getOrDefault(venue, 0) + 1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error reading job requests from database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void displayPieChart(PieChart pieChart) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : venueUsageMap.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
        pieChart.setData(pieChartData);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
