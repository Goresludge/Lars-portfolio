import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class StartScreen {

    private static TextField lagnamn1Input1 = new TextField("Lagnamn");
    private static TextField lagnamn1Input2 = new TextField("Lagnamn");
    private static Button buttonPlay = new Button("KÖR IGÅNG SPELET");

    public static void display(Stage primaryStage) {



        primaryStage.setTitle("GAME SHOW!!");

        GridPane grid = new GridPane();
        Scene scene = new Scene(grid,700,500);


        scene.getStylesheets().add("style.css");

/*
        ImageView karran = new ImageView("karran.jpg");
        karran.setFitHeight(200);
        karran.setFitWidth(200);
        RotateTransition rotate = new RotateTransition(Duration.millis(1000), karran);
        rotate.setByAngle(360);
        rotate.setCycleCount(500);
        rotate.play();
        GridPane.setHalignment(karran,HPos.CENTER);
        grid.getChildren().addAll(karran);
*/
        setUpLayout(grid,lagnamn1Input1,lagnamn1Input2,buttonPlay);

        String musicFile = "StartMusic.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
        mediaPlayer.play();

        buttonPlay.setId("startButton");
        grid.setId("startScreen");

        buttonPlay.setOnAction(e -> {
            grid.getChildren().clear();
            grid.getColumnConstraints().clear();
            grid.getRowConstraints().clear();
            grid.setId("null");
            GameShowPanel.display(grid);
        });
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setFullScreen(true);

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
