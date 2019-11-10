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

public class GameShowPanel {



    public static void display(Stage window, GridPane grid){

        Button firstGame = null;
        firstGame = new Button("Test game!");
        grid.getChildren().add(firstGame);

        Button finalFirstGame = firstGame;
        firstGame.setOnAction(e-> {
            grid.getChildren().remove(finalFirstGame);
            TestGame.display(window,grid);
        });



    }
}
