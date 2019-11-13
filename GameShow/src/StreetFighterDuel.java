import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

import java.io.File;

public class StreetFighterDuel {



    public static void display(Stage window, GridPane grid){

        Button buttonBack = null;
        buttonBack = new Button("Stop");



        BackgroundImage myBI= new BackgroundImage(new Image("StreetFighter.jpg",window.getWidth(),window.getHeight(),false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        grid.setBackground(new Background(myBI));

        /*
        grid.setId("StreetFighter");
        scene.getStylesheets().add("style.css");
*/
        String musicFile = "StreetFighter.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        grid.getChildren().add(buttonBack);
        buttonBack.setOnAction(e -> {
            grid.getChildren().clear();
            mediaPlayer.stop();
            grid.setBackground(null);
            GameShowPanel.display(window,grid);
        });
    }
}
