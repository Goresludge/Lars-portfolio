import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

import java.io.File;

public class TestGame {



    public static void display(Stage window, GridPane grid){

        Button buttonBack = null;
        buttonBack = new Button("Stop");

        String musicFile = "vengaboys.mp3";     // For example
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        grid.getChildren().add(buttonBack);
        buttonBack.setOnAction(e -> {
            mediaPlayer.stop();
            GameShowPanel.display(window,grid);
        });
    }
}
