import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class whosThere {

    public static void display(GridPane grid){

    }

    private static void setupScreen(GridPane grid) {

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(15);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(35);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(35);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(15);
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(10);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(40);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(40);
        RowConstraints row4 = new RowConstraints();
        row4.setPercentHeight(10);
        grid.getRowConstraints().addAll(row1, row2, row3, row4);
        grid.getColumnConstraints().addAll(col1, col2, col3, col4);
    }
}
