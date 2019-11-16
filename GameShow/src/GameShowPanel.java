import com.sun.deploy.util.ArrayUtil;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

class GameShowPanel {

    private static Button game1 = new Button("1");
    private static Button game2 = new Button("2");
    private static Button game3 = new Button("3");
    private static Button game4 = new Button("4");
    private static Button game5 = new Button("5");
    private static Button game6 = new Button("6");
    private static Button game7 = new Button("7");
    private static Button game8 = new Button("8");
    private static Button game9 = new Button("9");
    private static Button game10 = new Button("10");
    private static Button game11 = new Button("11");
    private static Button game12 = new Button("12");
    private static Button game13 = new Button("13");
    private static Button game14 = new Button("14");
    private static Button game15 = new Button("15");
    private static Button game16 = new Button("16");
    private static Button game17 = new Button("17");
    private static Button game18 = new Button("18");
    private static Button game19 = new Button("19");
    private static Button game20 = new Button("20");
    private static Button game21 = new Button("21");
    private static Button game22 = new Button("22");
    private static Button game23 = new Button("23");
    private static Button game24 = new Button("24");
    private static Button game25 = new Button("25");
    private static Button[] buttons = new Button[25];
    private static Label labelPointLag1 = new Label("Poäng: 0");
    private static Label labelPointLag2 = new Label("Poäng: 0");
    private static int pointsLag1;
    private static int pointsLag2;
    private static Label lagnamn1 = new Label(StartScreen.getLagnamn1());
    private static Label lagnamn2 = new Label(StartScreen.getLagnamn2());

    static void display(GridPane grid){

        setupPanel(grid);

        String musicFile = "StartMusic.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
        mediaPlayer.play();

        game1.setOnAction(e-> {
            grid.getChildren().clear();
            grid.getColumnConstraints().clear();
            grid.getRowConstraints().clear();
            mediaPlayer.stop();
            StreetFighterDuel.display(grid);
            game1.setId("selectedButton");
            game1.setDisable(true);
        });

        game2.setOnAction(e-> {
            grid.getChildren().clear();
            grid.getColumnConstraints().clear();
            grid.getRowConstraints().clear();
            mediaPlayer.stop();
            GuessWord.display(grid);
            game1.setId("selectedButton");
            game1.setDisable(true);
        });
    }

    static void result(GridPane grid, int a, int b){

        pointsLag1 += a;
        pointsLag2 += b;
        labelPointLag1.setText("Poäng: " + Integer.toString(pointsLag1));
        labelPointLag2.setText("Poäng: " + Integer.toString(pointsLag2));

        display(grid);
    }

    private static void setupPanel(GridPane grid){

        grid.setId("root");

        buttons[0] = game1;
        buttons[1] = game2;
        buttons[2] = game3;
        buttons[3] = game4;
        buttons[4] = game5;
        buttons[5] = game6;
        buttons[6] = game7;
        buttons[7] = game8;
        buttons[8] = game9;
        buttons[9] = game10;
        buttons[10] = game11;
        buttons[11] = game12;
        buttons[12] = game13;
        buttons[13] = game14;
        buttons[14] = game15;
        buttons[15] = game16;
        buttons[16] = game17;
        buttons[17] = game18;
        buttons[18] = game19;
        buttons[19] = game20;
        buttons[20] = game21;
        buttons[21] = game22;
        buttons[22] = game23;
        buttons[23] = game24;
        buttons[24] = game25;

        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);

        int i = 1;
        int j = 0;
        for (Button b: buttons
             ) {
            GridPane.setConstraints(b,i,j);
            GridPane.setFillWidth(b,true);
            GridPane.setFillHeight(b,true);
            if(!b.isDisabled()){
                b.setId("panelButtons");
            }
            b.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
            i++;
            if(i == 6){
                j++;
                i = 1;
            }
        }


        labelPointLag1.setId("points");
        labelPointLag2.setId("points");
        GridPane.setConstraints(labelPointLag1,0,1);
        GridPane.setConstraints(labelPointLag2,6,1);
        GridPane.setHalignment(labelPointLag1,HPos.CENTER);
        GridPane.setHalignment(labelPointLag2,HPos.CENTER);

        GridPane.setConstraints(lagnamn1,0,0);
        GridPane.setConstraints(lagnamn2,6,0);
        GridPane.setHalignment(lagnamn1,HPos.CENTER);
        GridPane.setHalignment(lagnamn2,HPos.CENTER);


        grid.getChildren().addAll(lagnamn1,lagnamn2,labelPointLag1,labelPointLag2,game1,game2,game3,
                game4,game5,game6,game7,game8,game9,game10,game11,game12,
                game13,game14,game15,game16,game17,game18,game19,game20,
                game21,game22,game23,game24,game25);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(15);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(14);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(14);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(14);
        ColumnConstraints col5 = new ColumnConstraints();
        col5.setPercentWidth(14);
        ColumnConstraints col6 = new ColumnConstraints();
        col6.setPercentWidth(14);
        ColumnConstraints col7 = new ColumnConstraints();
        col7.setPercentWidth(15);
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(20);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(20);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(20);
        RowConstraints row4 = new RowConstraints();
        row4.setPercentHeight(20);
        RowConstraints row5 = new RowConstraints();
        row5.setPercentHeight(20);
        grid.getRowConstraints().addAll(row1,row2,row3,row4,row5);
        grid.getColumnConstraints().addAll(col1,col2,col3,col4,col5,col6,col7);



    }
}
