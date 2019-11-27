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
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.io.File;

public class jeopardy {

    private static Button category1 = new Button("IKEA-\nKÄNDISAR");
    private static Button category2 = new Button("LÅTTEXTER\nAV QUEEN");
    private static Button category3 = new Button("DÅLIGT\nBESKRIVNA\nFILMER");
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
    private static Button lag1minus = new Button("-");
    private static Button lag1plus = new Button("+");
    private static Button lag2minus = new Button("-");
    private static Button lag2plus = new Button("+");
    private static int pointsLag1 = 0;
    private static int pointsLag2 = 0;
    private static int countDone = 0;
    private static ScaleTransition scaleTransition = new ScaleTransition();
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

        question1.setOnAction(e-> {
            String question = "Ikeas främsta bokförvarare\nsom även har en namne\ni en pan-pizza";
            addDisabledButton(question1);
            countDone++;
            displayQuestion(grid,question,displayButton);
        });

        question2.setOnAction(e-> {
            String question = "Is this the real life?\nIs this just fantasy?";
            addDisabledButton(question2);
            countDone++;
            displayQuestion(grid,question,displayButton);
        });

        question3.setOnAction(e-> {
            String question = "Emo-tjej som inte vet\nom hon vill ligga\nmed ett lik eller\nen hund";
            addDisabledButton(question3);
            countDone++;
            displayQuestion(grid,question,displayButton);
        });

        question4.setOnAction(e-> {
            String question = "En möbelserie som många\ninklusive Lasse Berghagen\nhar i sitt hjärta";
            addDisabledButton(question4);
            countDone++;
            displayQuestion(grid,question,displayButton);
        });

        question5.setOnAction(e-> {
            String question = "pushing down on me\npressing down on you\nno man ask for";
            addDisabledButton(question5);
            countDone++;
            displayQuestion(grid,question,displayButton);
        });

        question6.setOnAction(e-> {
            String question = "Nio snubbar lämnar\ntillbaka ett smycke\nunder nio timmar";
            addDisabledButton(question6);
            countDone++;
            displayQuestion(grid,question,displayButton);
        });

        question7.setOnAction(e-> {
            String question = "Den mest sålda soffan\nsom man kan luta sig\nmot när man har det svårt";
            addDisabledButton(question7);
            countDone++;
            displayQuestion(grid,question,displayButton);
        });

        question8.setOnAction(e-> {
            String question = "Inside my heart is breaking\nMy make-up may be flaking\nBut my smile still stays on.";
            addDisabledButton(question8);
            countDone++;
            displayQuestion(grid,question,displayButton);
        });

        question9.setOnAction(e-> {
            String question = "Talande gris man tror\nska vara sexig";
            addDisabledButton(question9);
            countDone++;
            displayQuestion(grid,question,displayButton);
        });

    }

    private static boolean finished(){
        return countDone == 9;
    }

    private static void runIntro(){

    }

    private static void addDisabledButton(Button button){
        button.setText("");
        button.setOpacity(0.1);
        button.setDisable(true);
    }

    private static void lastQuestion(Button button,GridPane grid){
        button.setOpacity(1);
        countDone = 0;
        GridPane.setFillWidth(button,true);
        GridPane.setFillHeight(button,true);
        button.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        button.setText("Konstnär");
        button.setId("Målade det relativt\nkända konstverket\n'Mona Lisa'");
        grid.add(button,3,2);
        scaleTransition.setByX(2.5);
        scaleTransition.setByY(2.5);
        scaleTransition.setDuration(Duration.millis(1500));
        scaleTransition.setCycleCount(1);
        scaleTransition.setNode(button);
        scaleTransition.setAutoReverse(false);
        scaleTransition.play();
        button.setDisable(true);
        scaleTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                button.setDisable(false);
            }
        });
        final int[] i = {0};
        button.setOnAction(e -> {
            if(i[0] == 0){
                displayTimer(grid);
                button.setText("En bra fråga");
            }
            if(i[0] == 1){
                hideQuestion(button,grid);
            }
            i[0]++;
        });

    }

    private static void hideQuestion(Button button,GridPane grid){
        button.setDisable(true);
        scaleTransition.setByX(-3);
        scaleTransition.setByY(-3);
        scaleTransition.setDuration(Duration.millis(1500));
        scaleTransition.setCycleCount(1);
        scaleTransition.setNode(button);
        scaleTransition.setAutoReverse(false);
        scaleTransition.play();
        scaleTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                displayButton.setText("");
                grid.getChildren().remove(displayButton);
                scaleTransition = new ScaleTransition();
                button.setDisable(false);
                if(finished()){
                    lastQuestion(button,grid);
                }
            }
        });
    }

    private static void displayTimer(GridPane grid){
        ImageView image = new ImageView("img/jeopardyTimer.png");
        GridPane.setHalignment(image,HPos.CENTER);
        GridPane.setValignment(image,VPos.CENTER);
        grid.add(image,3,4);

        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setInterpolator(Interpolator.LINEAR);
        rotateTransition.setAxis(Rotate.Z_AXIS);
        rotateTransition.setNode(image);
        rotateTransition.setByAngle(360);
        rotateTransition.setCycleCount(1);
        rotateTransition.setDuration(Duration.millis(8000));
        rotateTransition.setAutoReverse(false);
        image.opacityProperty().setValue(1);
        rotateTransition.play();
        rotateTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                image.opacityProperty().setValue(0);
            }
        });

    }

    private static void displayQuestion(GridPane grid,String question,Button button){
        button.setOpacity(1);
        GridPane.setFillWidth(button,true);
        GridPane.setFillHeight(button,true);
        button.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        button.setText(question);
        button.setId("displayJeopardyPanel");
        grid.add(button,3,2);
        scaleTransition.setByX(3);
        scaleTransition.setByY(3);
        scaleTransition.setDuration(Duration.millis(1500));
        scaleTransition.setCycleCount(1);
        scaleTransition.setNode(button);
        scaleTransition.setAutoReverse(false);
        scaleTransition.play();
        button.setDisable(true);
        displayTimer(grid);
        scaleTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                button.setDisable(false);
            }
        });
        button.setOnAction(e -> {
            hideQuestion(displayButton,grid);
        });
    }


    private static void setupPanel(GridPane grid){

        grid.setId("jeopardyBG");

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

        Font.loadFont(jeopardy.class.getResource("font/Chalkduster.ttf").toExternalForm(), 10);

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

        grid.getChildren().addAll(category1,category2,category3,
                question1,question2,question3,question4,question5,question6,question7,question8,question9,lag1plus,lag2plus,lag1minus,lag2minus);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(10);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(10);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(20);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(20);
        ColumnConstraints col5 = new ColumnConstraints();
        col5.setPercentWidth(20);
        ColumnConstraints col6 = new ColumnConstraints();
        col6.setPercentWidth(10);
        ColumnConstraints col7 = new ColumnConstraints();
        col7.setPercentWidth(10);
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
