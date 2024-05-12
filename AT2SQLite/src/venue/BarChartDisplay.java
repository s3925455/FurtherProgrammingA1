package venue;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BarChartDisplay extends Application {
    private ObservableList<String> title = FXCollections.observableArrayList();
    private ObservableList<Double> totals = FXCollections.observableArrayList();
    private ObservableList<Double> commissions = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Booking Totals and Commissions");

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(event -> primaryStage.close());

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(exitButton);

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Booking Totals and Commissions");
        xAxis.setLabel("Booking");
        yAxis.setLabel("Amount");

        Scene scene = new Scene(new VBox(barChart, exitButton), 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        readJobRequestsFromDatabase();
        displayBarChart(barChart);
    }

    private void readJobRequestsFromDatabase() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:/Users/amitmunjal/eclipse-workspace/FutureProgramWk9/application.db")) {
            String query = "SELECT title, total, comission FROM requests";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String bookingName = resultSet.getString("title");
                    double total = resultSet.getDouble("total");
                    double commission = resultSet.getDouble("comission");

                    title.add(bookingName);
                    totals.add(total);
                    commissions.add(commission);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error reading job requests from database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void displayBarChart(BarChart<String, Number> barChart) {
        XYChart.Series<String, Number> totalSeries = new XYChart.Series<>();
        XYChart.Series<String, Number> commissionSeries = new XYChart.Series<>();

        for (int i = 0; i < title.size(); i++) {
            totalSeries.getData().add(new XYChart.Data<>(title.get(i), totals.get(i)));
            commissionSeries.getData().add(new XYChart.Data<>(title.get(i), commissions.get(i)));
        }

        totalSeries.setName("Total");
        commissionSeries.setName("Commission");

        barChart.getData().addAll(totalSeries, commissionSeries);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
