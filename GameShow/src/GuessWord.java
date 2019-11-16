import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

public class GuessWord {

    public static void display(GridPane grid){

        Button lagA;
        Button lagB;
        lagA = new Button(StartScreen.getLagnamn1());
        lagB = new Button(StartScreen.getLagnamn2());

        grid.setId("GuessWord");

        String musicFile = "GuessWord.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

        setupScreen(grid,lagA,lagB);
        lagA.setOnAction(e -> {
            grid.getChildren().clear();
            grid.getColumnConstraints().clear();
            grid.getRowConstraints().clear();
            mediaPlayer.stop();
            grid.setBackground(null);
            GameShowPanel.result(grid,3,0);
        });
        lagB.setOnAction(e -> {
            grid.getChildren().clear();
            grid.getColumnConstraints().clear();
            grid.getRowConstraints().clear();
            mediaPlayer.stop();
            grid.setBackground(null);
            GameShowPanel.result(grid,0,3);
        });
    }

    private static void setupScreen(GridPane grid,Button lagA,Button lagB){

        GridPane.setConstraints(lagA,0,7);
        GridPane.setConstraints(lagB,15,7);
        GridPane.setHalignment(lagA,HPos.CENTER);
        GridPane.setHalignment(lagB,HPos.CENTER);

        lagA.setId("teamButtons");
        lagB.setId("teamButtons");

        GridPane.setFillWidth(lagA,true);
        GridPane.setFillWidth(lagB,true);

        grid.getChildren().addAll(lagA,lagB);
        grid.setGridLinesVisible(true);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(15);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(5);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(5);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(5);
        ColumnConstraints col5 = new ColumnConstraints();
        col5.setPercentWidth(5);
        ColumnConstraints col6 = new ColumnConstraints();
        col6.setPercentWidth(5);
        ColumnConstraints col7 = new ColumnConstraints();
        col7.setPercentWidth(5);
        ColumnConstraints col8 = new ColumnConstraints();
        col8.setPercentWidth(5);
        ColumnConstraints col9 = new ColumnConstraints();
        col9.setPercentWidth(5);
        ColumnConstraints col10 = new ColumnConstraints();
        col10.setPercentWidth(5);
        ColumnConstraints col11 = new ColumnConstraints();
        col11.setPercentWidth(5);
        ColumnConstraints col12 = new ColumnConstraints();
        col12.setPercentWidth(5);
        ColumnConstraints col13 = new ColumnConstraints();
        col13.setPercentWidth(5);
        ColumnConstraints col14 = new ColumnConstraints();
        col14.setPercentWidth(5);
        ColumnConstraints col15 = new ColumnConstraints();
        col15.setPercentWidth(5);
        ColumnConstraints col16 = new ColumnConstraints();
        col16.setPercentWidth(15);
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(20);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(10);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(10);
        RowConstraints row4 = new RowConstraints();
        row4.setPercentHeight(10);
        RowConstraints row5 = new RowConstraints();
        row5.setPercentHeight(10);
        RowConstraints row6 = new RowConstraints();
        row6.setPercentHeight(10);
        RowConstraints row7 = new RowConstraints();
        row7.setPercentHeight(10);
        RowConstraints row8 = new RowConstraints();
        row8.setPercentHeight(20);
        grid.getRowConstraints().addAll(row1,row2,row3,row4,row5,row6,row7,row8);
        grid.getColumnConstraints().addAll(col1,col2,col3,col4,col5,col6,col7,col8,col9,col10,col11,col12,col13,col14,col15,col16);

        wordGame(grid);
    }

    private static void wordGame(GridPane grid){

    }
}
