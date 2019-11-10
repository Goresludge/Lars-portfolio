import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class Main extends Application{

    Stage window;
    Button buttonPlay;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setTitle("GAME SHOW!!");
        buttonPlay = new Button("Play");
        TextField lagnamn1Input1 = new TextField("Lagnamn");
        TextField lagnamn1Input2 = new TextField("Lagnamn");
        GridPane grid = new GridPane();


        Scene scene = new Scene(grid,700,500);
        //scene.getStylesheets().add("style.css");

        setUpLayout(grid,lagnamn1Input1,lagnamn1Input2,buttonPlay);


        buttonPlay.setOnAction(e -> {
            grid.getChildren().removeAll(lagnamn1Input1,lagnamn1Input2,buttonPlay);
            GameShowPanel.display(window,grid);
        });
        window.setScene(scene);
        window.show();
        window.setFullScreen(true);

    }

    private void setUpLayout(GridPane grid, TextField lagnamn1Input1, TextField lagnamn1Input2, Button buttonPlay){

        grid.setGridLinesVisible(true);

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


        grid.setGridLinesVisible(true);

    }


}
