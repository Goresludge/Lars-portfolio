import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

public class StartScreen {

    private static TextField lagnamn1Input1 = new TextField("Lagnamn");
    private static TextField lagnamn1Input2 = new TextField("Lagnamn");
    private static Button buttonPlay = new Button("KÖR IGÅNG SPELET");
    private static GridPane grid = new GridPane();
    private static Scene scene = new Scene(grid,700,500);

    /*
    Alla spel:
    Fågelskådning
    Bonus
    Kulturfrågan
    DJContest
    Fiskespel
    Gissa ordet x3
    Jeopardy
    Listan
    Längsta ordet
    Street Fighter
    Vem där?
    Vilket år?
    Vilken stad?
    */

    public static void display(Stage primaryStage) {

        AtomicReference<Boolean> enabled = new AtomicReference<>(true);
        primaryStage.setTitle("GAME SHOW!!");

        scene.getStylesheets().add("style.css");

        setUpLayout(grid,lagnamn1Input1,lagnamn1Input2,buttonPlay);

        String musicFile = "src/mp3/StartMusic.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
        mediaPlayer.play();
        mediaPlayer.setVolume(0.7);

        buttonPlay.setId("startButton");
        grid.setId("startScreen");

        buttonPlay.setOnAction(e -> {
            new ScreenTransitionFrom(grid,1,1);
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1),
                            new KeyValue(mediaPlayer.volumeProperty(), 0)));
            timeline.play();
            timeline.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    enabled.set(false);
                    grid.getChildren().clear();
                    grid.getColumnConstraints().clear();
                    grid.getRowConstraints().clear();
                    grid.setId("null");
                    mediaPlayer.stop();
                    Birdwatching.display(grid);
                }
            });
        });
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setFullScreen(true);

        grid.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent key) {
                if(enabled.get()){
                    String sfx = key.getCode().toString();
                    if(sfx.equals("A")){
                        String musicFile = "src/mp3/Applause1.mp3";
                        Media sound = new Media(new File(musicFile).toURI().toString());
                        MediaPlayer mediaPlayerSFX = new MediaPlayer(sound);
                        mediaPlayerSFX.play();
                    }
                }
            }
        });

    }

    static Scene getScene(){
        return scene;
    }

    static double getScreenHeight(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return screenSize.getHeight();
    }

    static double getScreenWidth(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return screenSize.getWidth();
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

        lagnamn1Input1.setId("input");
        lagnamn1Input2.setId("input");

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
        row1.setPercentHeight(50);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(20);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(40);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(17);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(33);

        grid.getColumnConstraints().addAll(column1,column2,column3);
        grid.getRowConstraints().addAll(row1,row2,row3);

    }
}
