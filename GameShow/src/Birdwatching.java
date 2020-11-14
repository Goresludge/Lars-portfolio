import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.File;

public class Birdwatching {

    private static ImageView eagle = new ImageView("img/eagle.png");
    private static ImageView binoculars = new ImageView("img/kikare.png");
    private static MediaPlayer mediaPlayer;
    private static MediaPlayer eaglePlayer;
    private static double width = StartScreen.getScreenWidth();
    private static double height = StartScreen.getScreenHeight();
    private static Label labelPointLag1 = new Label("Poäng: 0");
    private static Label labelPointLag2 = new Label("Poäng: 0");
    private static Label lagnamn1 = new Label(StartScreen.getLagnamn1());
    private static Label lagnamn2 = new Label(StartScreen.getLagnamn2());
    private static Button lag1minus = new Button("-");
    private static Button lag1plus = new Button("+");
    private static Button lag2minus = new Button("-");
    private static Button lag2plus = new Button("+");
    private static Button finishButton = new Button("Färdig!");
    private static int pointsLag1 = 0;
    private static int pointsLag2 = 0;

    public static void display(GridPane grid) {

        setupScreen(grid);
        intro(grid);

        new ScreenTransitionTo(grid,3,2);

        String musicFile = "src/mp3/bird_sounds.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

    }

    private static void startGame(GridPane grid){
        grid.setId("BirdWatching");
        eagle.setFitHeight(height/3);
        eagle.setFitWidth(width/3);
        grid.add(eagle,0,0);
        grid.add(binoculars,0,3);
        binoculars.setFitWidth(StartScreen.getScreenWidth()*1.75);
        binoculars.setFitHeight(StartScreen.getScreenHeight()*1.75);
        mediaPlayer.stop();
        mediaPlayer.play();

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(26.5)));
        timeline.play();
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                moveEagle(grid);
            }
        });

        moveBinoculars(binoculars,grid);
    }

    private static void intro(GridPane grid){
        grid.setId("BirdWatchingIntro");
        Button button = new Button("Spana!");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                startGame(grid);
                grid.getChildren().remove(button);
            }
        });
        grid.add(button,3,4);

    }

    private static void moveBinoculars(ImageView binoculars,GridPane grid){
        double width = StartScreen.getScreenWidth();
        double height = StartScreen.getScreenHeight();
        double absLeft = width/8;
        double absTop = height/3;
        Path path = new Path();
        MoveTo moveTo = new MoveTo(width/2, height/2);
        LineTo line1 = new LineTo(absLeft*6,absTop);
        LineTo line2 = new LineTo(absLeft*7,absTop);
        LineTo line3 = new LineTo(absLeft*7,absTop*3);
        LineTo line4 = new LineTo(absLeft*5,absTop*3);
        LineTo line5 = new LineTo(absLeft*3,absTop*1);
        LineTo line6 = new LineTo(absLeft*4.5,absTop);
        LineTo line7 = new LineTo(absLeft*2.5,absTop*3);
        LineTo line8 = new LineTo(absLeft*2,absTop);
        LineTo line9 = new LineTo(absLeft,absTop*3);
        LineTo fin = new LineTo(absLeft,absTop);
        path.getElements().add(moveTo);
        path.getElements().addAll(line1,line2,line3,line4,line5,line6,line7,line8,line9,fin);
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(55));
        pathTransition.setNode(binoculars);
        pathTransition.setPath(path);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(false);
        pathTransition.play();
        pathTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                grid.getChildren().remove(binoculars);
                addButtons(grid);
            }
        });
    }

    private static void moveEagle(GridPane grid){
        String musicFile = "src/mp3/eagle_sfx.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        eaglePlayer = new MediaPlayer(sound);
        eaglePlayer.play();

        double absLeft = width/8;
        double absTop = height/3;
        eagle.setFitHeight(height/3);
        eagle.setFitWidth(width/3);
        Path path = new Path();
        MoveTo moveTo = new MoveTo(width/1.5, 0);
        LineTo line = new LineTo(absLeft*3,absTop*2);
        path.getElements().add(moveTo);
        path.getElements().addAll(line);
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(3000));
        pathTransition.setNode(eagle);
        pathTransition.setPath(path);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(false);
        pathTransition.play();
        pathTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                grid.getChildren().remove(eagle);
            }
        });

    }

    private static void addButtons(GridPane grid){

        grid.setId("BirdWatchingIntro");
        Font.loadFont(Jeopardy.class.getResource("font/Chalkduster.ttf").toExternalForm(), 10);

        labelPointLag1.setId("jeopardyLabels");
        labelPointLag2.setId("jeopardyLabels");
        lagnamn1.setId("jeopardyLabels");
        lagnamn2.setId("jeopardyLabels");
        grid.add(labelPointLag1,0,1,2,1);
        grid.add(labelPointLag2,5,1,2,1);
        GridPane.setHalignment(labelPointLag1,HPos.CENTER);
        GridPane.setHalignment(labelPointLag2,HPos.CENTER);
        grid.add(lagnamn1,0,0,2,1);
        grid.add(lagnamn2,5,0,2,1);
        GridPane.setHalignment(lagnamn1,HPos.CENTER);
        GridPane.setHalignment(lagnamn2,HPos.CENTER);
        lag1plus.setId("jeopardyButtons");
        lag1minus.setId("jeopardyButtons");
        lag2plus.setId("jeopardyButtons");
        lag2minus.setId("jeopardyButtons");
        GridPane.setConstraints(lag1plus,1,2);
        GridPane.setConstraints(lag1minus,0,2);
        GridPane.setConstraints(lag2plus,6,2);
        GridPane.setConstraints(lag2minus,5,2);
        GridPane.setHalignment(lag1plus,HPos.CENTER);
        GridPane.setHalignment(lag1minus,HPos.CENTER);
        GridPane.setHalignment(lag2plus,HPos.CENTER);
        GridPane.setHalignment(lag2minus,HPos.CENTER);
        GridPane.setHalignment(finishButton,HPos.CENTER);
        finishButton.setId("jeopardyButtons");
        grid.add(finishButton,3,4);

        finishButton.setOnAction(e -> {
            new ScreenTransitionFrom(grid,3,2);
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1)));
            timeline.play();
            timeline.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    grid.getChildren().clear();
                    grid.getColumnConstraints().clear();
                    grid.getRowConstraints().clear();
                    mediaPlayer.dispose();
                    returnResult(grid);
                }
            });
        });

        grid.getChildren().addAll(lag1plus,lag1minus,lag2plus,lag2minus);

        lag1minus.setOnAction(e -> {
            pointsLag1--;
            labelPointLag1.setText("Poäng: " + Integer.toString(pointsLag1));
        });

        lag2minus.setOnAction(e -> {
            pointsLag2--;
            labelPointLag2.setText("Poäng: " + Integer.toString(pointsLag2));
        });

        lag1plus.setOnAction(e -> {
            pointsLag1++;
            labelPointLag1.setText("Poäng: " + Integer.toString(pointsLag1));
        });

        lag2plus.setOnAction(e -> {
            pointsLag2++;
            labelPointLag2.setText("Poäng: " + Integer.toString(pointsLag2));
        });

    }

    private static void returnResult(GridPane grid){
        GameShowPanel.result(grid,pointsLag1,pointsLag2);
    }

    private static void setupScreen(GridPane grid) {

        grid.setPadding(new Insets(0,0,0,0));
        grid.setVgap(0);
        grid.setHgap(0);

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
        grid.getRowConstraints().addAll(row1, row2, row3, row4, row5);
        grid.getColumnConstraints().addAll(col1, col2, col3, col4, col5, col6, col7);
    }
}