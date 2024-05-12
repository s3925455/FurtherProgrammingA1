package venue;

import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;

public class SelectFromMatchingListController {

    @FXML
    private TableView<Venue> table1;

    public TableView<Venue> getTable1() {
        return table1;
    }

    public void setTable1(TableView<Venue> table1) {
        this.table1 = table1;
    }

	public MenuItem getCurrentJobsMenuItem() {
		// TODO Auto-generated method stub
		return null;
	}

	public Menu getFileMenu() {
		// TODO Auto-generated method stub
		return null;
	}
    
    
    
//    @FXML
//    private TableView<SomeOtherData> table2;
//
// 
//
//    public TableView<SomeOtherData> getTable2() {
//        return table2;
//    }
////
//    public void setTable2(TableView<SomeOtherData> table2) {
//        this.table2 = table2;
//    }
}


