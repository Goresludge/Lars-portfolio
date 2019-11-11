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

class GameShowPanel {

    private static Button game1 = new Button("1");
    private static Button game2 = new Button("2");
    private static Button game3 = new Button("3");
    private static Button game4 = new Button("4");
    private static Button game5 = new Button("5");
    private static Button game6 = new Button("6");
    private static Button game7 = new Button("7");
    private static Button game8 = new Button("8");
    private static Button game9 = new Button("9");
    private static Button game10 = new Button("10");
    private static Button game11 = new Button("11");
    private static Button game12 = new Button("12");
    private static Button game13 = new Button("13");
    private static Button game14 = new Button("14");
    private static Button game15 = new Button("15");
    private static Button game16 = new Button("16");
    private static Button game17 = new Button("17");
    private static Button game18 = new Button("18");
    private static Button game19 = new Button("19");
    private static Button game20 = new Button("20");
    private static Button game21 = new Button("21");
    private static Button game22 = new Button("22");
    private static Button game23 = new Button("23");
    private static Button game24 = new Button("24");
    private static Button game25 = new Button("25");

    static void display(Stage window, GridPane grid){

        setupPanel(grid);

        game1.setOnAction(e-> {
            grid.getChildren().clear();
            grid.getColumnConstraints().clear();
            grid.getRowConstraints().clear();
            TestGame.display(window,grid);
        });

    }

    private static void setupPanel(GridPane grid){


        grid.setGridLinesVisible(true);
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);

        GridPane.setConstraints(game1,1,0);
        GridPane.setConstraints(game2,2,0);
        GridPane.setConstraints(game3,3,0);
        GridPane.setConstraints(game4,4,0);
        GridPane.setConstraints(game5,5,0);
        GridPane.setConstraints(game6,1,1);
        GridPane.setConstraints(game7,2,1);
        GridPane.setConstraints(game8,3,1);
        GridPane.setConstraints(game9,4,1);
        GridPane.setConstraints(game10,5,1);
        GridPane.setConstraints(game11,1,2);
        GridPane.setConstraints(game12,2,2);
        GridPane.setConstraints(game13,3,2);
        GridPane.setConstraints(game14,4,2);
        GridPane.setConstraints(game15,5,2);
        GridPane.setConstraints(game16,1,3);
        GridPane.setConstraints(game17,2,3);
        GridPane.setConstraints(game18,3,3);
        GridPane.setConstraints(game19,4,3);
        GridPane.setConstraints(game20,5,3);
        GridPane.setConstraints(game21,1,4);
        GridPane.setConstraints(game22,2,4);
        GridPane.setConstraints(game23,3,4);
        GridPane.setConstraints(game24,4,4);
        GridPane.setConstraints(game25,5,4);

        GridPane.setFillWidth(game1,true);
        GridPane.setFillWidth(game2,true);
        GridPane.setFillWidth(game3,true);
        GridPane.setFillWidth(game4,true);
        GridPane.setFillWidth(game5,true);
        GridPane.setFillWidth(game6,true);
        GridPane.setFillWidth(game7,true);
        GridPane.setFillWidth(game8,true);
        GridPane.setFillWidth(game9,true);
        GridPane.setFillWidth(game10,true);
        GridPane.setFillWidth(game11,true);
        GridPane.setFillWidth(game12,true);
        GridPane.setFillWidth(game13,true);
        GridPane.setFillWidth(game14,true);
        GridPane.setFillWidth(game15,true);
        GridPane.setFillWidth(game16,true);
        GridPane.setFillWidth(game17,true);
        GridPane.setFillWidth(game18,true);
        GridPane.setFillWidth(game19,true);
        GridPane.setFillWidth(game20,true);
        GridPane.setFillWidth(game21,true);
        GridPane.setFillWidth(game22,true);
        GridPane.setFillWidth(game23,true);
        GridPane.setFillWidth(game24,true);
        GridPane.setFillWidth(game25,true);


        GridPane.setFillHeight(game1,true);
        GridPane.setFillHeight(game2,true);
        GridPane.setFillHeight(game3,true);
        GridPane.setFillHeight(game4,true);
        GridPane.setFillHeight(game5,true);
        GridPane.setFillHeight(game6,true);
        GridPane.setFillHeight(game7,true);
        GridPane.setFillHeight(game8,true);
        GridPane.setFillHeight(game9,true);
        GridPane.setFillHeight(game10,true);
        GridPane.setFillHeight(game11,true);
        GridPane.setFillHeight(game12,true);
        GridPane.setFillHeight(game13,true);
        GridPane.setFillHeight(game14,true);
        GridPane.setFillHeight(game15,true);
        GridPane.setFillHeight(game16,true);
        GridPane.setFillHeight(game17,true);
        GridPane.setFillHeight(game18,true);
        GridPane.setFillHeight(game19,true);
        GridPane.setFillHeight(game20,true);
        GridPane.setFillHeight(game21,true);
        GridPane.setFillHeight(game22,true);
        GridPane.setFillHeight(game23,true);
        GridPane.setFillHeight(game24,true);
        GridPane.setFillHeight(game25,true);

        game1.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        game2.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        game3.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        game4.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        game5.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        game6.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        game7.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        game8.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        game9.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        game10.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        game11.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        game12.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        game13.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        game14.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        game15.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        game16.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        game17.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        game18.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        game19.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        game20.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        game21.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        game22.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        game23.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        game24.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        game25.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);

        Label lagnamn1 = new Label(StartScreen.getLagnamn1());
        GridPane.setConstraints(lagnamn1,0,0);
        Label lagnamn2 = new Label(StartScreen.getLagnamn2());
        GridPane.setConstraints(lagnamn1,6,0);
        grid.getChildren().addAll(lagnamn1,lagnamn2,game1,game2,game3,
                game4,game5,game6,game7,game8,game9,game10,game11,game12,
                game13,game14,game15,game16,game17,game18,game19,game20,
                game21,game22,game23,game24,game25);

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
        grid.getRowConstraints().addAll(row1,row2,row3,row4,row5);
        grid.getColumnConstraints().addAll(col1,col2,col3,col4,col5,col6,col7);

        grid.setGridLinesVisible(true);

    }
}
