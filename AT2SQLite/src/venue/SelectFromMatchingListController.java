package venue;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class SelectFromMatchingListController {

    @FXML
    private TableView<Venue> table1; // Inject TableView from FXML

    public TableView<Venue> getTable1() {
        return table1;
    }
}
