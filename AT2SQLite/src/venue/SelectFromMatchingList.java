package venue;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

public class SelectFromMatchingList extends Application {
	
    @FXML
    private Label masterLabel;


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Selection.fxml"));
        VBox root = loader.load();
        Scene scene = new Scene(root, 920, 620);
        primaryStage.setScene(scene);
        primaryStage.setTitle("MatchMaker");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
