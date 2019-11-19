import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;

public class LongestWord {

    private static String currentWord ="";
    private static int currentGame = 1;
    private static ArrayList<String> words = new ArrayList<String>();
    private static int lag1points = 0;
    private static int lag2points = 0;
    private static ScaleTransition scaleTransition = new ScaleTransition();

    public static void display(GridPane grid) {


        Button lagA;
        Button lagB;
        Button tie;
        lagA = new Button(StartScreen.getLagnamn1());
        lagB = new Button(StartScreen.getLagnamn2());
        tie = new Button("Oavgjort!");

        grid.setId("GuessWord");

        String musicFile = "GuessWord.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

        setupScreen(grid);
        setupTeamButtons(grid,lagA,lagB,tie);
        tie.setOnAction(e -> {
            if(currentGame == words.size()){
                screenTransitionFrom(grid);
                scaleTransition.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        grid.getChildren().clear();
                        grid.getColumnConstraints().clear();
                        grid.getRowConstraints().clear();
                        mediaPlayer.stop();
                        grid.setBackground(null);
                        lag1points++;
                        lag2points++;
                        GameShowPanel.result(grid, lag1points, lag2points);
                    }
                });

            }
            else {
                grid.getChildren().clear();
                lag1points++;
                lag2points++;
                currentGame++;
                currentWord = "";
                setupTeamButtons(grid,lagA,lagB,tie);
                nextGame(grid);
            }

        });
        lagA.setOnAction(e -> {
            if(currentGame == words.size()){
                screenTransitionFrom(grid);
                scaleTransition.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        grid.getChildren().clear();
                        grid.getColumnConstraints().clear();
                        grid.getRowConstraints().clear();
                        mediaPlayer.stop();
                        grid.setBackground(null);
                        lag1points++;
                        GameShowPanel.result(grid, lag1points, lag2points);
                    }
                });

            }
            else {
                grid.getChildren().clear();
                lag1points++;
                currentGame++;
                currentWord = "";
                setupTeamButtons(grid,lagA,lagB,tie);
                nextGame(grid);
            }

        });
        lagB.setOnAction(e -> {
            if(currentGame == words.size()){
                screenTransitionFrom(grid);
                scaleTransition.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        grid.getChildren().clear();
                        grid.getColumnConstraints().clear();
                        grid.getRowConstraints().clear();
                        mediaPlayer.stop();
                        grid.setBackground(null);
                        lag2points++;
                        GameShowPanel.result(grid, lag1points, lag2points);
                    }
                });
            }
            else {
                grid.getChildren().clear();
                lag2points++;
                currentGame++;
                setupTeamButtons(grid,lagA,lagB,tie);
                nextGame(grid);
            }
        });

        gameAttributes();
        nextGame(grid);
        screenTransitionTo(grid);
    }

    private static void nextGame(GridPane grid){

        currentWord = words.get(currentGame-1);
        setupGame(grid);

    }

    private static void setupGame(GridPane grid){
        for (int i = 0; i < currentWord.length(); i++) {
            String s = String.valueOf(currentWord.charAt(i));
            Button b = new Button(s);
            b.setId("guessWord");
            GridPane.setFillWidth(b, true);
            GridPane.setFillHeight(b, true);
            b.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            grid.add(b, i + 1, 4);
        }
    }

    private static void gameAttributes(){

        String word1 = "RDSEJHTUU"; //Husdjuret
        String word2 = "MDJASSCER"; //Smedjas
        String word3 = "OSKDFOAPS";

        words.add(word1);
        words.add(word2);
        words.add(word3);

    }

    private static void setupScreen(GridPane grid) {

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(15);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(8);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(8);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(8);
        ColumnConstraints col5 = new ColumnConstraints();
        col5.setPercentWidth(8);
        ColumnConstraints col6 = new ColumnConstraints();
        col6.setPercentWidth(8);
        ColumnConstraints col7 = new ColumnConstraints();
        col7.setPercentWidth(8);
        ColumnConstraints col8 = new ColumnConstraints();
        col8.setPercentWidth(8);
        ColumnConstraints col9 = new ColumnConstraints();
        col9.setPercentWidth(8);
        ColumnConstraints col10 = new ColumnConstraints();
        col10.setPercentWidth(8);
        ColumnConstraints col11 = new ColumnConstraints();
        col11.setPercentWidth(15);
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
        grid.getRowConstraints().addAll(row1, row2, row3, row4, row5, row6, row7, row8);
        grid.getColumnConstraints().addAll(col1, col2, col3, col4, col5, col6, col7, col8, col9, col10, col11);

    }

    private static void setupTeamButtons(GridPane grid, Button lagA, Button lagB,Button tie){
        GridPane.setConstraints(tie,7,7);
        GridPane.setConstraints(lagA, 0, 7);
        GridPane.setConstraints(lagB, 10, 7);
        GridPane.setHalignment(tie,HPos.CENTER);
        GridPane.setHalignment(lagA, HPos.CENTER);
        GridPane.setHalignment(lagB, HPos.CENTER);
        grid.add(tie,4,7,3,1);
        tie.setId("teamButtons");
        lagA.setId("teamButtons");
        lagB.setId("teamButtons");

        GridPane.setFillWidth(lagA, true);
        GridPane.setFillWidth(lagB, true);
        GridPane.setFillWidth(tie,true);

        grid.getChildren().addAll(lagA, lagB);
    }

    private static void screenTransitionTo(GridPane grid){
        Circle circle = new Circle();
        circle.setFill(Color.BLACK);
        circle.setCenterX(50);
        circle.setCenterY(50);
        circle.setRadius(1400);
        GridPane.setHalignment(circle,HPos.CENTER);
        GridPane.setValignment(circle,VPos.CENTER);
        grid.add(circle,7,3);
        scaleTransition.setDuration(Duration.millis(1000));
        scaleTransition.setByX(-1.0);
        scaleTransition.setByY(-1.0);
        scaleTransition.setCycleCount(1);
        scaleTransition.setNode(circle);
        scaleTransition.setAutoReverse(false);
        scaleTransition.play();
        scaleTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                grid.getChildren().remove(circle);
            }
        });
    }

    private static void screenTransitionFrom(GridPane grid){
        Circle circle = new Circle();
        circle.setFill(Color.BLACK);
        circle.setCenterX(1);
        circle.setCenterY(1);
        circle.setRadius(1);
        GridPane.setHalignment(circle,HPos.CENTER);
        GridPane.setValignment(circle,VPos.CENTER);
        grid.add(circle,7,3);
        scaleTransition.setDuration(Duration.millis(1000));
        scaleTransition.setByX(1400);
        scaleTransition.setByY(1400);
        scaleTransition.setCycleCount(1);
        scaleTransition.setNode(circle);
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();
    }
}
