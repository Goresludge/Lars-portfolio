import javafx.animation.*;
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
import javafx.util.Duration;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

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
    private static Button[] buttons = new Button[16];
    private static Label labelPointLag1 = new Label("Poäng: 0");
    private static Label labelPointLag2 = new Label("Poäng: 0");
    private static int pointsLag1;
    private static int pointsLag2;
    private static Label lagnamn1 = new Label(StartScreen.getLagnamn1());
    private static Label lagnamn2 = new Label(StartScreen.getLagnamn2());
    private static String musicFile = "src/mp3/StartMusic.mp3";
    private static Media sound = new Media(new File(musicFile).toURI().toString());
    private static MediaPlayer mediaPlayer = new MediaPlayer(sound);
    private static List<Integer> list = new ArrayList<Integer>(){{add(1); add(2);add(3);
        add(4); add(5);add(6);add(7);add(8);}};

    public static void display(GridPane grid){

        setupPanel(grid);

        new ScreenTransitionTo(grid,3,2);

        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
        mediaPlayer.play();
        mediaPlayer.setVolume(0.6);


        game1.setOnAction(e-> {
            int id = randomNumber();
            runGame(grid,id,game1);
        });

        game2.setOnAction(e-> {
            int id = randomNumber();
            runGame(grid,id,game2);
        });

        game3.setOnAction(e-> {
            int id = randomNumber();
            runGame(grid,id,game3);
        });

        game4.setOnAction(e-> {
            int id = randomNumber();
            runGame(grid,id,game4);
        });

        game5.setOnAction(e-> {
            int id = randomNumber();
            runGame(grid,id,game5);
        });

        game6.setOnAction(e-> {
            int id = randomNumber();
            runGame(grid,id,game6);
        });

        game7.setOnAction(e-> {
            int id = randomNumber();
            runGame(grid,id,game7);
        });

        game8.setOnAction(e-> {
            int id = randomNumber();
            runGame(grid,id,game8);
        });
    }

    private static int randomNumber(){

        Random rand = new Random();
        int id = list.get(rand.nextInt(list.size()));
        list.removeIf(list->list.equals(id));
        return id;
    }

    private static void runGame(GridPane grid, int id, Button button){

        button.setId("selectedButton");
        button.setDisable(true);

        new ScreenTransitionFrom(grid,3,2);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1),
                        new KeyValue(mediaPlayer.volumeProperty(), 0)));
        timeline.play();
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                grid.getChildren().clear();
                grid.getColumnConstraints().clear();
                grid.getRowConstraints().clear();
                mediaPlayer.stop();
                switch (id){
                    case 1: StreetFighterDuel.display(grid);
                            break;
                    case 2: GuessWord.display(grid);
                            break;
                    case 3: LongestWord.display(grid);
                            break;
                    case 4: BonusSpin.display(grid);
                            break;
                    case 5: Jeopardy.display(grid);
                            break;
                    case 6: DVD.display(grid);
                            break;
                    case 7: Birdwatching.display(grid);
                            break;
                    case 8: MusicQuiz.display(grid);
                            break;
                }

            }
        });
    }

    static void result(GridPane grid, int a, int b) {
        pointsLag1 += a;
        pointsLag2 += b;
        labelPointLag1.setText("Poäng: " + Integer.toString(pointsLag1));
        labelPointLag2.setText("Poäng: " + Integer.toString(pointsLag2));
        display(grid);
    }

    private static void bonusTransition(GridPane grid){
        ImageView image = new ImageView("img/bonus.png");
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
                new ScreenTransitionFrom(grid,3,2);
            }
        });
    }

    private static void setupPanel(GridPane grid){

        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);

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

        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);

        int i = 1;
        int j = 0;
        for (Button b: buttons
             ) {
            if(i == 3){
                i++;
            }
            if(j == 2){
                j++;
            }
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
                game13,game14,game15,game16);

        /*
        ,game10,game11,game12,
                game13,game14,game15,game16,game17,game18,game19,game20,
                game21,game22,game23,game24,game25
         */

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(15);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(70.0/4);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(70.0/4);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(0);
        ColumnConstraints col5 = new ColumnConstraints();
        col5.setPercentWidth(70.0/4);
        ColumnConstraints col6 = new ColumnConstraints();
        col6.setPercentWidth(70.0/4);
        ColumnConstraints col7 = new ColumnConstraints();
        col7.setPercentWidth(15);
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(25);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(25);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(0);
        RowConstraints row4 = new RowConstraints();
        row4.setPercentHeight(25);
        RowConstraints row5 = new RowConstraints();
        row5.setPercentHeight(25);
        grid.getRowConstraints().addAll(row1,row2,row3,row4,row5);
        grid.getColumnConstraints().addAll(col1,col2,col3,col4,col5,col6,col7);



    }
}
