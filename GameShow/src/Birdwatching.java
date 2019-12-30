import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.io.File;

public class Birdwatching {

    private static ImageView eagle = new ImageView("img/eagle.png");
    private static ImageView binoculars = new ImageView("img/kikare.png");
    private static MediaPlayer mediaPlayer;
    private static MediaPlayer eaglePlayer;

    public static void display(GridPane grid) {

        Button lagA;
        Button lagB;
        lagA = new Button(StartScreen.getLagnamn1());
        lagB = new Button(StartScreen.getLagnamn2());

        setupScreen(grid);
        grid.setId("BirdWatching");
        new ScreenTransitionTo(grid,3,2);

        String musicFile = "src/mp3/bird_sounds.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

        grid.add(eagle,0,0);
        grid.add(binoculars,0,3);
        binoculars.setFitWidth(StartScreen.getScreenWidth()*1.75);
        binoculars.setFitHeight(StartScreen.getScreenHeight()*1.75);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(16.5)));
        timeline.play();
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                moveEagle(grid);
            }
        });

        moveBinoculars(binoculars);
    }

    private static void intro(){

    }

    private static void moveBinoculars(ImageView binoculars){
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
        pathTransition.setDuration(Duration.seconds(35));
        pathTransition.setNode(binoculars);
        pathTransition.setPath(path);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(false);
        pathTransition.play();
    }

    private static void moveEagle(GridPane grid){
        String musicFile = "src/mp3/eagle_sfx.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        eaglePlayer = new MediaPlayer(sound);
        eaglePlayer.play();

        double width = StartScreen.getScreenWidth();
        double height = StartScreen.getScreenHeight();
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

    private static void addButtons(GridPane grid, Button lagA, Button lagB){
        GridPane.setConstraints(lagA, 0, 4);
        GridPane.setConstraints(lagB, 6, 4);
        GridPane.setHalignment(lagA, HPos.CENTER);
        GridPane.setHalignment(lagB, HPos.CENTER);

        lagA.setId("teamButtons");
        lagB.setId("teamButtons");

        GridPane.setFillWidth(lagA, true);
        GridPane.setFillWidth(lagB, true);

        grid.getChildren().addAll(lagA, lagB);
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