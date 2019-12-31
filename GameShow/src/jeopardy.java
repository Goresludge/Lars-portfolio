
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
    private static Button finishButton = new Button("Färdig");
    private static int pointsLag1 = 0;
    private static int pointsLag2 = 0;
    private static int countDone = 0;
    private static ScaleTransition scaleTransition = new ScaleTransition();
    private static Button displayButton = new Button();

    static void display(GridPane grid){

        setupPanel(grid);
        screenTransitionTo(grid);
        displayButton.setOpacity(0);
        runIntro(grid);

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

        finishButton.setOnAction(e -> {
            screenTransitionFrom(grid);
            scaleTransition.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    grid.getChildren().clear();
                    grid.getColumnConstraints().clear();
                    grid.getRowConstraints().clear();
                    returnResult(grid);
                }
            });

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

    private static void returnResult(GridPane grid){
        GameShowPanel.result(grid,pointsLag1,pointsLag2);
    }

    private static boolean finishedCounter(){
        return countDone == 9;
    }

    private static void runIntro(GridPane grid){
        Image image = new Image("img/JeopardyLogo.png");
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitWidth(StartScreen.getScreenWidth());
        imageView.setFitHeight(StartScreen.getScreenHeight());
        imageView.setSmooth(true);
        imageView.setCache(true);
        String musicFile = "src/mp3/JeopardyIntro.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(2),
                        new KeyValue(mediaPlayer.volumeProperty(), 0)));
        grid.add(imageView,0,2);
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(3000),imageView);
        translateTransition.setToY(StartScreen.getScreenHeight()*2);
        translateTransition.setCycleCount(1);
        translateTransition.play();
        translateTransition.pause();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(9));
        pauseTransition.play();
        pauseTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timeline.play();
                translateTransition.play();
            }
        });
        translateTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                grid.getChildren().remove(imageView);
                translateTransition.stop();
                pauseTransition.stop();
                mediaPlayer.dispose();
            }
        });
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
        grid.add(button,3,2);
        scaleTransition.setByX(2.5);
        scaleTransition.setByY(2.5);
        scaleTransition.setDuration(Duration.millis(1500));
        scaleTransition.setCycleCount(1);
        scaleTransition.setNode(button);
        scaleTransition.setAutoReverse(false);
        scaleTransition.play();
        button.setDisable(true);
        String musicFile = "src/mp3/JeopardyFinalMusic.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        scaleTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                button.setDisable(false);
            }
        });
        final int[] i = {0};
        button.setOnAction(e -> {
            if(i[0] == 0){
                button.setText("Målade det relativt\nkända konstverket\n'Mona Lisa'");
                displayFinalTimer(grid);
                mediaPlayer.play();
            }
            if(i[0] == 1){
                hideQuestion(button,grid);
                mediaPlayer.dispose();
                GridPane.setHalignment(finishButton,HPos.CENTER);
                finishButton.setId("jeopardyButtons");
                grid.add(finishButton,3,4);
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
                if(finishedCounter()){
                    lastQuestion(button,grid);
                }
            }
        });
    }

    private static void displayFinalTimer(GridPane grid){
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
        rotateTransition.setDuration(Duration.seconds(30));
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
        rotateTransition.setDuration(Duration.seconds(15));
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
        String musicFile = "src/mp3/SwooshSFX.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        button.setOpacity(1);
        GridPane.setFillWidth(button,true);
        GridPane.setFillHeight(button,true);
        button.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        button.setText(question);
        button.setId("displayJeopardyPanel");
        grid.add(button,3,2);
        scaleTransition.setByX(3);
        scaleTransition.setByY(3);
        scaleTransition.setDuration(Duration.millis(500));
        scaleTransition.setCycleCount(1);
        scaleTransition.setNode(button);
        scaleTransition.setAutoReverse(false);
        scaleTransition.play();
        button.setDisable(true);
        displayTimer(grid);
        scaleTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mediaPlayer.dispose();
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

    private static void screenTransitionFrom(GridPane grid){
        Circle circle = new Circle();
        circle.setFill(Color.BLACK);
        circle.setCenterX(1);
        circle.setCenterY(1);
        circle.setRadius(1);
        GridPane.setHalignment(circle,HPos.CENTER);
        GridPane.setValignment(circle,VPos.CENTER);
        grid.add(circle,3,2);
        scaleTransition.setDuration(Duration.millis(1000));
        scaleTransition.setByX(1400);
        scaleTransition.setByY(1400);
        scaleTransition.setCycleCount(1);
        scaleTransition.setNode(circle);
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();
    }

    private static void screenTransitionTo(GridPane grid){
        Circle circle = new Circle();
        circle.setFill(Color.BLACK);
        circle.setCenterX(50);
        circle.setCenterY(50);
        circle.setRadius(1400);
        GridPane.setHalignment(circle,HPos.CENTER);
        GridPane.setValignment(circle,VPos.CENTER);
        grid.add(circle,3,2);
        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setDuration(Duration.millis(1000));
        scaleTransition.setByX(-1.0);
        scaleTransition.setByY(-1.0);
        scaleTransition.setCycleCount(1);
        scaleTransition.setNode(circle);
        scaleTransition.setAutoReverse(false);
        scaleTransition.play();
        scaleTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                grid.getChildren().remove(circle);
            }
        });

    }
}
