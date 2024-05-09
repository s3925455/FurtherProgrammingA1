package venue;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;

public class SelectFromMatchingList extends Application {

    private List<Venue> venues = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Selection.fxml"));
            VBox root = loader.load();

            SelectFromMatchingListController controller = loader.getController();

            TableView<Venue> table1;
            if (controller != null) {
                table1 = controller.getTable1();
            } else {
                table1 = new TableView<>();
                VBox vbox = new VBox(table1);
                root.getChildren().add(vbox);
            }

            readColumnsFromDatabase(table1);

            readVenuesFromDatabase(table1);

            Scene scene = new Scene(root, 920, 620);
            primaryStage.setScene(scene);
            primaryStage.setTitle("MatchMaker");
            primaryStage.show();
            System.out.println("FXML loaded successfully");
        } catch (Exception e) {
            showAlert("Error", "Failed to load table data.");
            e.printStackTrace();
        }
    }

    private void readColumnsFromDatabase(TableView<Venue> tableView) {
        System.out.println("Loading columns from the database...");
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:/Users/amitmunjal/eclipse-workspace/FutureProgramWk9/application.db", "username", "password")) {
            // Use ResultSetMetaData to get column names
            String query = "SELECT * FROM venues WHERE 1 = 0"; // This query returns an empty result set with column names
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                TableColumn<Venue, String> column = new TableColumn<>(columnName);
                column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFieldValue(columnName)));
                tableView.getColumns().add(column);
                System.out.println("Column added: " + columnName);
            }
            System.out.println("Columns loaded successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error loading columns from the database: " + e.getMessage());
        }
    }

    private void readVenuesFromDatabase(TableView<Venue> tableView) {
        System.out.println("Loading venues from the database...");
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:/Users/amitmunjal/eclipse-workspace/FutureProgramWk9/application.db")) {
            String query = "SELECT * FROM venues"; // the table name is 'venues'
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int venueId = resultSet.getInt("venueId");
                    String name = resultSet.getString("name");
                    int capacity = resultSet.getInt("capacity");
                    String suitableFor = resultSet.getString("suitableFor");
                    String category = resultSet.getString("category");
                    int rate = resultSet.getInt("rate");
                    Venue venue = new Venue(venueId, name, capacity, suitableFor, category, rate);
                    venues.add(venue);
                }
                System.out.println("Venues loaded successfully.");

                // Populate the TableView with the venues data
                ObservableList<Venue> venueList = FXCollections.observableArrayList(venues);
                tableView.setItems(venueList);

                // Debugging: Print loaded data
                for (Venue v : venueList) {
                    System.out.println(v);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error loading venues from the database: " + e.getMessage());
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
}
