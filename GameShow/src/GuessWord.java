import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
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

public class GuessWord {

    private static String currentWord ="";
    private static String currentBonus ="";
    private static int currentGame = 1;
    private static boolean gameEnabled = true;
    private static int wrongGuess = 0;
    private static String pickedLetter = "" ;
    private static ArrayList<String> hints = new ArrayList<String>();
    private static ArrayList<String> words = new ArrayList<String>();
    private static ArrayList<String> bonusLetters = new ArrayList<String>();
    private static int lag1points = 0;
    private static int lag2points = 0;
    private static ScaleTransition scaleTransition = new ScaleTransition();


    public static void display(GridPane grid) {

        grid.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent key) {
                if (gameEnabled && validGuess(key)) {
                    wordGame(grid, key.getCode());
                }

            }
        });

        Button lagA;
        Button lagB;
        lagA = new Button(StartScreen.getLagnamn1());
        lagB = new Button(StartScreen.getLagnamn2());

        grid.setId("GuessWord");

        String musicFile = "GuessWord.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

        setupScreen(grid);
        setupTeamButtons(grid,lagA,lagB);
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
                        gameEnabled = false;
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
                setupTeamButtons(grid,lagA,lagB);
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
                        gameEnabled = false;
                        lag2points++;
                        GameShowPanel.result(grid, lag1points, lag2points);
                    }
                });
            }
            else {
                grid.getChildren().clear();
                lag2points++;
                currentGame++;
                setupTeamButtons(grid,lagA,lagB);
                nextGame(grid);
            }
        });

        gameAttributes();
        nextGame(grid);
        screenTransitionTo(grid);
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

    private static void gameAttributes(){

        String hint1 = "Film";
        String word1 = "James Bond Tomorrow Never Dies";
        String bonusLetter1 = "i";
        String hint2 = "TV-serie";
        String word2 = "Morden i midsomer";
        String bonusLetter2 = "s";
        String hint3 = "Bok";
        String word3 = "To Kill a Mockingbird";
        String bonusLetter3 = "c";

        hints.add(hint1);
        hints.add(hint2);
        hints.add(hint3);
        words.add(word1);
        words.add(word2);
        words.add(word3);
        bonusLetters.add(bonusLetter1);
        bonusLetters.add(bonusLetter2);
        bonusLetters.add(bonusLetter3);


    }

    private static void nextGame(GridPane grid){

        wrongGuess = 0;
        pickedLetter = "";
        Label currentHint = new Label(hints.get(currentGame-1));
        currentHint.setId("guessWordCategory");
        GridPane.setHalignment(currentHint,HPos.CENTER);
        GridPane.setValignment(currentHint,VPos.CENTER);
        currentHint.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        grid.add(currentHint,7,0,8,1);
        currentBonus = bonusLetters.get(currentGame-1);
        currentWord = words.get(currentGame-1);
        setupGame(grid);

    }


    private static void setupScreen(GridPane grid) {

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
        grid.getRowConstraints().addAll(row1, row2, row3, row4, row5, row6, row7, row8);
        grid.getColumnConstraints().addAll(col1, col2, col3, col4, col5, col6, col7, col8, col9, col10, col11, col12, col13, col14, col15, col16);

    }

    private static void setupTeamButtons(GridPane grid, Button lagA, Button lagB){
        GridPane.setConstraints(lagA, 0, 7);
        GridPane.setConstraints(lagB, 15, 7);
        GridPane.setHalignment(lagA, HPos.CENTER);
        GridPane.setHalignment(lagB, HPos.CENTER);

        lagA.setId("teamButtons");
        lagB.setId("teamButtons");

        GridPane.setFillWidth(lagA, true);
        GridPane.setFillWidth(lagB, true);

        grid.getChildren().addAll(lagA, lagB);
    }

    private static void setupGame(GridPane grid) {
        String [] words = currentWord.split(" ");
        String stringRow = "";
        int rows = 2;
        for (String word:words) {
            if((stringRow.length()+word.length()) > 14){
                for (int i = 0; i < stringRow.length(); i++) {
                    if (Character.isLetter(stringRow.charAt(i))) {
                        Button b = new Button("");
                        b.setId("guessWord");
                        GridPane.setFillWidth(b, true);
                        GridPane.setFillHeight(b, true);
                        b.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                        grid.add(b, i + 1, rows);
                    }
                }
                stringRow = "";
                stringRow = stringRow + word + " ";
                rows++;
            }
            else{
                stringRow = stringRow + word + " ";
            }
        }
        for (int i = 0; i < stringRow.length(); i++) {
            if (Character.isLetter(stringRow.charAt(i))) {
                Button b = new Button("");
                b.setId("guessWord");
                GridPane.setFillWidth(b, true);
                GridPane.setFillHeight(b, true);
                b.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                grid.add(b, i + 1, rows);
            }
        }

    }

    private static void wordGame(GridPane grid, KeyCode letter) {
        String c = letter.toString();
        boolean exists = false;

        String [] words = currentWord.split(" ");
        String stringRow = "";
        int rows = 2;
        for (String word:words) {
            if((stringRow.length()+word.length()) > 14){
                for (int i = 0; i < stringRow.length(); i++) {
                    if (Character.isLetter(stringRow.charAt(i))) {
                        String s = String.valueOf(stringRow.charAt(i));
                        if(s.equals(c) || s.equals(c.toLowerCase())){
                            Button b = new Button(s);
                            if(currentBonus.equals(s)){
                                b.setId("guessWordBonus");
                            }
                            else {
                                b.setId("guessWord");
                            }
                            GridPane.setFillWidth(b, true);
                            GridPane.setFillHeight(b, true);
                            b.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                            grid.add(b, i + 1, rows);
                            exists = true;
                        }

                    }
                }
                stringRow = "";
                stringRow = stringRow + word + " ";
                rows++;
            }
            else{
                stringRow = stringRow + word + " ";
            }
        }
        for (int i = 0; i < stringRow.length(); i++) {
            if (Character.isLetter(stringRow.charAt(i))) {
                String s = String.valueOf(stringRow.charAt(i));
                if(s.equals(c) || s.equals(c.toLowerCase())){
                    Button b = new Button(s);
                    if(currentBonus.equals(s)){
                        b.setId("guessWordBonus");
                    }
                    else {
                        b.setId("guessWord");
                    }
                    GridPane.setFillWidth(b, true);
                    GridPane.setFillHeight(b, true);
                    b.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                    grid.add(b, i + 1, rows);
                    exists = true;
                }

            }
        }

        if(!exists){
            Button b = new Button(c);
            b.setId("guessWord");
            GridPane.setFillWidth(b, true);
            GridPane.setFillHeight(b, true);
            b.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            grid.add(b, 1 + wrongGuess, 7);
            wrongGuess++;
        }
    }

    private static boolean validGuess(KeyEvent key){

        if(pickedLetter.contains(key.getCode().toString()) || key.getCode().toString().equals("UNDEFINED")){
            return false;
        }
        else{
            pickedLetter = pickedLetter+key.getCode().toString();
            return true;
        }
    }
}
