import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class StartScreen {

    private static TextField lagnamn1Input1 = new TextField("Lagnamn");
    private static TextField lagnamn1Input2 = new TextField("Lagnamn");
    private static Button buttonPlay = new Button("Play");

    public static void display(Stage window, Stage primaryStage) {


        window = primaryStage;
        window.setTitle("GAME SHOW!!");


        GridPane grid = new GridPane();

        Scene scene = new Scene(grid,700,500);
        //scene.getStylesheets().add("style.css");

        setUpLayout(grid,lagnamn1Input1,lagnamn1Input2,buttonPlay);


        Stage finalWindow = window;
        buttonPlay.setOnAction(e -> {
            grid.getChildren().clear();
            grid.getColumnConstraints().clear();
            grid.getRowConstraints().clear();
            GameShowPanel.display(finalWindow,grid);
        });
        window.setScene(scene);
        window.show();
        window.setFullScreen(true);

    }

    static String getLagnamn1(){
        return lagnamn1Input1.getText();
    }

    static String getLagnamn2(){
        return lagnamn1Input2.getText();
    }

    private static void setUpLayout(GridPane grid, TextField lagnamn1Input1, TextField lagnamn1Input2, Button buttonPlay){


        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);


        GridPane.setFillWidth(buttonPlay,true);
        GridPane.setFillHeight(buttonPlay,true);

        buttonPlay.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);

        GridPane.setConstraints(lagnamn1Input1,0,1);
        GridPane.setConstraints(lagnamn1Input2,2,1);

        grid.add(buttonPlay,0,2,3,1);
        grid.getChildren().addAll(lagnamn1Input1,lagnamn1Input2);

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(40);
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(33);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(20);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(40);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(33);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(33);

        grid.getColumnConstraints().addAll(column1,column2,column3);
        grid.getRowConstraints().addAll(row1,row2,row3);

    }
}
