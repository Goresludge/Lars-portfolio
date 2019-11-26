import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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

public class jeopardy {

    private static Button category1 = new Button("IKEA");
    private static Button category2 = new Button("ÅRTAL");
    private static Button category3 = new Button("3");
    private static Button question1 = new Button("1");
    private static Button question2 = new Button("1");
    private static Button question3 = new Button("1");
    private static Button question4 = new Button("2");
    private static Button question5 = new Button("2");
    private static Button question6 = new Button("2");
    private static Button question7 = new Button("3");
    private static Button question8 = new Button("3");
    private static Button question9 = new Button("3");
    private static Button[] buttons = new Button[12];
    private static Label labelPointLag1 = new Label("Poäng: 0");
    private static Label labelPointLag2 = new Label("Poäng: 0");
    private static Label lagnamn1 = new Label(StartScreen.getLagnamn1());
    private static Label lagnamn2 = new Label(StartScreen.getLagnamn2());
    private static ScaleTransition scaleTransition = new ScaleTransition();
    private static boolean gameEnabled = true;
    private static Button displayButton = new Button();

    static void display(GridPane grid){

        setupPanel(grid);
        displayButton.setOpacity(0);
        String musicFile = "Jeopardy.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(14),
                        new KeyValue(mediaPlayer.volumeProperty(), 0)));
        timeline.play();

        displayButton.setOnAction(e -> {
            hideQuestion(displayButton);
            scaleTransition.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    displayButton.setText("");
                    grid.getChildren().remove(displayButton);
                    scaleTransition = new ScaleTransition();
                }
            });
        });

        question1.setOnAction(e-> {
            String question = "Hur många ben har en spindel?";
            addDisabledButton(question1);
            displayQuestion(grid,question,displayButton);
        });

        question2.setOnAction(e-> {
            String question = "Hur många ben har jag?";
            addDisabledButton(question2);
            displayQuestion(grid,question,displayButton);
        });
/*
        question3.setOnAction(e-> {
            buttonTransition(grid);
            buttonTransition.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                }
            });
        });

        question4.setOnAction(e-> {
            buttonTransition(grid);
            buttonTransition.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                }
            });

        });
        */
    }

    private static void addDisabledButton(Button button){
        button.setText("");
        button.setOpacity(0.3);
    }

    private static void hideQuestion(Button button){
        scaleTransition.setByX(-3);
        scaleTransition.setByY(-3);
        scaleTransition.setDuration(Duration.millis(1500));
        scaleTransition.setCycleCount(1);
        scaleTransition.setNode(button);
        scaleTransition.setAutoReverse(false);
        scaleTransition.play();
    }

    private static void displayQuestion(GridPane grid,String question,Button button){
        button.setOpacity(1);
        GridPane.setFillWidth(button,true);
        GridPane.setFillHeight(button,true);
        button.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        button.setText(question);
        button.setId("jeopardyPanel");
        grid.add(button,3,2);
        scaleTransition.setByX(3);
        scaleTransition.setByY(3);
        scaleTransition.setDuration(Duration.millis(1500));
        scaleTransition.setCycleCount(1);
        scaleTransition.setNode(button);
        scaleTransition.setAutoReverse(false);
        scaleTransition.play();
    }


    private static void setupPanel(GridPane grid){

        grid.setId("root");

        buttons[0] = category1;
        buttons[1] = category2;
        buttons[2] = category3;
        buttons[3] = question1;
        buttons[4] = question2;
        buttons[5] = question3;
        buttons[6] = question4;
        buttons[7] = question5;
        buttons[8] = question6;
        buttons[9] = question7;
        buttons[10] = question8;
        buttons[11] = question9;

        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);

        int c = 2;
        int r = 0;
        for (Button b: buttons
                ) {
            GridPane.setConstraints(b,c,r);
            GridPane.setFillWidth(b,true);
            GridPane.setFillHeight(b,true);
            if(!b.isDisabled()){
                b.setId("jeopardyPanel");
            }
            b.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
            c++;
            if(c == 5){
                r++;
                c = 2;
            }
        }


        labelPointLag1.setId("points");
        labelPointLag2.setId("points");
        GridPane.setConstraints(labelPointLag1,0,1);
        GridPane.setConstraints(labelPointLag2,6,1);
        GridPane.setHalignment(labelPointLag1,HPos.CENTER);
        GridPane.setHalignment(labelPointLag2,HPos.CENTER);

        GridPane.setConstraints(lagnamn1,0,0);
        GridPane.setConstraints(lagnamn2,6,0);
        GridPane.setHalignment(lagnamn1,HPos.CENTER);
        GridPane.setHalignment(lagnamn2,HPos.CENTER);


        grid.getChildren().addAll(lagnamn1,lagnamn2,labelPointLag1,labelPointLag2,category1,category2,category3,
                question1,question2,question3,question4,question5,question6,question7,question8,question9);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(15);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(5);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(20);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(20);
        ColumnConstraints col5 = new ColumnConstraints();
        col5.setPercentWidth(20);
        ColumnConstraints col6 = new ColumnConstraints();
        col6.setPercentWidth(5);
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



    }
}
