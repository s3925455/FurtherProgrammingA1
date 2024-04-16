package AT2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {
	

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();
        
        LoginController controller = loader.getController();
        controller.setStage(primaryStage);

        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 320, 200));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
