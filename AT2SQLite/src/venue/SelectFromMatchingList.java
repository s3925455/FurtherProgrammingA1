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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
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

            readVenuesFromDatabase();

            ObservableList<Venue> venueList = FXCollections.observableArrayList(venues);
            table1.setItems(venueList);

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
        // Your database query to fetch column names
        // For example, assuming there's a table named "venues" in your database
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:/Users/amitmunjal/eclipse-workspace/FutureProgramWk9/application.db", "username", "password")) {
            String query = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'venues'";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String columnName = resultSet.getString("COLUMN_NAME");
                TableColumn<Venue, String> column = new TableColumn<>(columnName);
                column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFieldValue(columnName)));
                tableView.getColumns().add(column);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void readVenuesFromDatabase() {
        // Your database reading logic here
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
