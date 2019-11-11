import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class GameShowPanel {

    static Button game1 = new Button("1");
    static Button game2 = new Button("2");
    static Button game3 = new Button("3");
    static Button game4 = new Button("4");
    static Button game5 = new Button("5");
    static Button game6 = new Button("6");
    static Button game7 = new Button("7");
    static Button game8 = new Button("8");
    static Button game9 = new Button("9");
    static Button game10 = new Button("10");
    static Button game11 = new Button("11");
    static Button game12 = new Button("12");
    static Button game13 = new Button("13");
    static Button game14 = new Button("14");
    static Button game15 = new Button("15");
    static Button game16 = new Button("16");
    static Button game17 = new Button("17");
    static Button game18 = new Button("18");
    static Button game19 = new Button("19");
    static Button game20 = new Button("20");
    static Button game21 = new Button("21");
    static Button game22 = new Button("22");
    static Button game23 = new Button("23");
    static Button game24 = new Button("24");
    static Button game25 = new Button("25");

    public static void display(Stage window,GridPane grid){

        setupPanel(grid);


        game1.setOnAction(e-> {
            grid.getChildren().remove(game1);
            TestGame.display(window,grid);
        });

    }

    private static void setupPanel(GridPane grid){


        grid.setGridLinesVisible(true);
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);


        Label lagnamn1 = new Label(StartScreen.getLagnamn1());
        Label lagnamn2 = new Label(StartScreen.getLagnamn2());
        grid.add(lagnamn1,0,0);
        grid.add(lagnamn2,1,0);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
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
        grid.getColumnConstraints().addAll(col1,col2);

        grid.setGridLinesVisible(true);

    }
}
