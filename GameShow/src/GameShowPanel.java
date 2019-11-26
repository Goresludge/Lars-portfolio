import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
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
    private static Button[] buttons = new Button[25];
    private static Label labelPointLag1 = new Label("Poäng: 0");
    private static Label labelPointLag2 = new Label("Poäng: 0");
    private static int pointsLag1;
    private static int pointsLag2;
    private static Label lagnamn1 = new Label(StartScreen.getLagnamn1());
    private static Label lagnamn2 = new Label(StartScreen.getLagnamn2());
    private static ScaleTransition scaleTransition = new ScaleTransition();
    private static boolean firstTransitionDone = false;

    static void display(GridPane grid){

        setupPanel(grid);

        if(!firstTransitionDone){
            firstTransition(grid);
        }

        String musicFile = "StartMusic.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
        mediaPlayer.play();
        mediaPlayer.setVolume(0.6);





        game1.setOnAction(e-> {
            screenTransitionFrom(grid);
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1),
                            new KeyValue(mediaPlayer.volumeProperty(), 0)));
            timeline.play();
            scaleTransition.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    grid.getChildren().clear();
                    grid.getColumnConstraints().clear();
                    grid.getRowConstraints().clear();
                    mediaPlayer.stop();
                    StreetFighterDuel.display(grid);
                    game1.setId("selectedButton");
                    game1.setDisable(true);
                }
            });

        });

        game2.setOnAction(e-> {
            screenTransitionFrom(grid);
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1),
                            new KeyValue(mediaPlayer.volumeProperty(), 0)));
            timeline.play();
            scaleTransition.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    grid.getChildren().clear();
                    grid.getColumnConstraints().clear();
                    grid.getRowConstraints().clear();
                    mediaPlayer.stop();
                    GuessWord.display(grid);
                    game2.setId("selectedButton");
                    game2.setDisable(true);
                }
            });
        });

        game3.setOnAction(e-> {
            screenTransitionFrom(grid);
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1),
                            new KeyValue(mediaPlayer.volumeProperty(), 0)));
            timeline.play();
            scaleTransition.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    grid.getChildren().clear();
                    grid.getColumnConstraints().clear();
                    grid.getRowConstraints().clear();
                    mediaPlayer.stop();
                    LongestWord.display(grid);
                    game3.setId("selectedButton");
                    game3.setDisable(true);
                }
            });
        });

        game4.setOnAction(e-> {
            bonusTransition(grid);
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1),
                            new KeyValue(mediaPlayer.volumeProperty(), 0)));
            timeline.play();
            scaleTransition.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    grid.getChildren().clear();
                    grid.getColumnConstraints().clear();
                    grid.getRowConstraints().clear();
                    mediaPlayer.stop();
                    BonusSpin.display(grid);
                    game4.setId("selectedButton");
                    game4.setDisable(true);
                }
            });

        });

        game5.setOnAction(e-> {
            screenTransitionFrom(grid);
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1),
                            new KeyValue(mediaPlayer.volumeProperty(), 0)));
            timeline.play();
            scaleTransition.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    grid.getChildren().clear();
                    grid.getColumnConstraints().clear();
                    grid.getRowConstraints().clear();
                    mediaPlayer.stop();
                    jeopardy.display(grid);
                    game4.setId("selectedButton");
                    game4.setDisable(true);
                }
            });

        });
    }


    static void result(GridPane grid, int a, int b) {

        pointsLag1 += a;
        pointsLag2 += b;
        labelPointLag1.setText("Poäng: " + Integer.toString(pointsLag1));
        labelPointLag2.setText("Poäng: " + Integer.toString(pointsLag2));
        display(grid);
        screenTransitionTo(grid);
    }

    private static void firstTransition(GridPane grid){
        ImageView image = new ImageView("star.png");
        image.setFitHeight(StartScreen.getScreenHeight()*12);
        image.setFitWidth(StartScreen.getScreenWidth()*8);
        GridPane.setHalignment(image,HPos.CENTER);
        GridPane.setValignment(image,VPos.CENTER);
        grid.add(image,3,2);
        scaleTransition.setDuration(Duration.millis(1500));
        scaleTransition.setByX(-1.0);
        scaleTransition.setByY(-1.0);
        scaleTransition.setCycleCount(1);
        scaleTransition.setNode(image);
        scaleTransition.setAutoReverse(false);
        scaleTransition.play();
        scaleTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                firstTransitionDone = true;
                grid.getChildren().remove(image);
            }
        });
    }

    private static void bonusTransition(GridPane grid){
        ImageView image = new ImageView("bonus.png");
        GridPane.setHalignment(image,HPos.CENTER);
        GridPane.setValignment(image,VPos.CENTER);
        grid.add(image,3,2);
        ScaleTransition bonusTransition = new ScaleTransition();
        bonusTransition.setDuration(Duration.millis(500));
        bonusTransition.setByX(1.5);
        bonusTransition.setByY(1.5);
        bonusTransition.setCycleCount(10);
        bonusTransition.setNode(image);
        bonusTransition.setAutoReverse(true);
        bonusTransition.play();
        bonusTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                screenTransitionFrom(grid);
            }
        });

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

    private static void setupPanel(GridPane grid){

        grid.setId("root");

        buttons[0] = game1;
        buttons[1] = game2;
        buttons[2] = game3;
        buttons[3] = game4;
        buttons[4] = game5;
        buttons[5] = game6;
        buttons[6] = game7;
        buttons[7] = game8;
        buttons[8] = game9;
        buttons[9] = game10;
        buttons[10] = game11;
        buttons[11] = game12;
        buttons[12] = game13;
        buttons[13] = game14;
        buttons[14] = game15;
        buttons[15] = game16;
        buttons[16] = game17;
        buttons[17] = game18;
        buttons[18] = game19;
        buttons[19] = game20;
        buttons[20] = game21;
        buttons[21] = game22;
        buttons[22] = game23;
        buttons[23] = game24;
        buttons[24] = game25;

        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);

        int i = 1;
        int j = 0;
        for (Button b: buttons
             ) {
            GridPane.setConstraints(b,i,j);
            GridPane.setFillWidth(b,true);
            GridPane.setFillHeight(b,true);
            if(!b.isDisabled()){
                b.setId("panelButtons");
            }
            b.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
            i++;
            if(i == 6){
                j++;
                i = 1;
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


        grid.getChildren().addAll(lagnamn1,lagnamn2,labelPointLag1,labelPointLag2,game1,game2,game3,
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



    }
}
