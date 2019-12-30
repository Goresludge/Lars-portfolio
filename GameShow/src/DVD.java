import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.util.Duration;

import java.io.File;

public class DVD {

    private static String musicFile = "src/mp3/sandstorm.mp3";
    private static Media sound = new Media(new File(musicFile).toURI().toString());
    private static MediaPlayer mediaPlayer = new MediaPlayer(sound);
    private static PathTransition pathTransition = new PathTransition();
    private static PathTransition gameTransition = new PathTransition();
    private static PathTransition mirrorTransition = new PathTransition();
    private static PathTransition mirrorTransition1 = new PathTransition();
    private static PathTransition mirrorTransition2 = new PathTransition();
    private static PathTransition mirrorTransition3 = new PathTransition();
    private static RotateTransition rotateTransition = new RotateTransition();
    private static RotateTransition rotateTransition1 = new RotateTransition();
    private static RotateTransition rotateTransition2 = new RotateTransition();
    private static RotateTransition rotateTransition3 = new RotateTransition();
    private static RotateTransition rotateTransition4 = new RotateTransition();
    private static Button startButton = new Button("start");

    public static void display(GridPane grid){

        grid.setId("black");
        setupScreen(grid);

        dvdIntro(grid);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                startDVD(grid);
            }
        });

    }

    private static void startDVD(GridPane grid){

        grid.getChildren().clear();
        grid.getColumnConstraints().clear();
        grid.getRowConstraints().clear();
        mediaPlayer.play();
        Image image = new Image("img/DVD.png");
        ImageView imageView = new ImageView();
        ImageView mirrorImage = new ImageView();
        ImageView mirrorImage1 = new ImageView();
        ImageView mirrorImage2 = new ImageView();
        ImageView mirrorImage3 = new ImageView();
        mirrorImage.setImage(image);
        mirrorImage.setScaleX(-1);
        mirrorImage.setFitWidth(StartScreen.getScreenWidth()/5);
        mirrorImage.setFitHeight(StartScreen.getScreenHeight()/5);
        mirrorImage1.setImage(image);
        mirrorImage1.setScaleX(-1);
        mirrorImage1.setFitWidth(StartScreen.getScreenWidth()/5);
        mirrorImage1.setFitHeight(StartScreen.getScreenHeight()/5);
        mirrorImage2.setImage(image);
        mirrorImage2.setScaleX(-1);
        mirrorImage2.setFitWidth(StartScreen.getScreenWidth()/5);
        mirrorImage2.setFitHeight(StartScreen.getScreenHeight()/5);
        mirrorImage3.setImage(image);
        mirrorImage3.setScaleX(-1);
        mirrorImage3.setFitWidth(StartScreen.getScreenWidth()/5);
        mirrorImage3.setFitHeight(StartScreen.getScreenHeight()/5);
        imageView.setImage(image);
        imageView.setFitWidth(StartScreen.getScreenWidth()/5);
        imageView.setFitHeight(StartScreen.getScreenHeight()/5);
        int absLeft = (int)(imageView.getFitWidth()/2);
        int absTop = (int)(imageView.getFitHeight()/2);
        int absRight = (int)(StartScreen.getScreenWidth()-imageView.getFitWidth()/2);
        int absBot = (int)(StartScreen.getScreenHeight()-imageView.getFitHeight()/2);
        double height = StartScreen.getScreenHeight();
        double width = StartScreen.getScreenWidth();
        grid.add(imageView,0,0);
        Path path = new Path();
        MoveTo moveTo = new MoveTo(width/2, height/2);
        LineTo lineMidMid = new LineTo(width/2,height/2);
        LineTo lineRB = new LineTo(absRight,absBot);
        LineTo lineRT = new LineTo(absRight,absTop);
        LineTo lineLB = new LineTo(absLeft,absBot);
        LineTo lineLT = new LineTo(absLeft,absTop);
        LineTo lineB5 = new LineTo(absRight/1.2,absBot);
        LineTo lineB4 = new LineTo(absRight/1.5,absBot);
        LineTo lineB3 = new LineTo(absRight/2,absBot);
        LineTo lineB2 = new LineTo(absRight/3,absBot);
        LineTo lineB1 = new LineTo(absRight/5,absBot);
        LineTo lineT5 = new LineTo(absRight/1.2,absTop);
        LineTo lineT4 = new LineTo(absRight/1.5,absTop);
        LineTo lineT3 = new LineTo(absRight/2,absTop);
        LineTo lineT2 = new LineTo(absRight/3,absTop);
        LineTo lineT1 = new LineTo(absRight/5,absTop);
        LineTo lineL1 = new LineTo(absLeft,absBot/3);
        LineTo lineL2 = new LineTo(absLeft,absBot/2);
        LineTo lineL3 = new LineTo(absLeft,absBot/1.5);
        LineTo lineR1 = new LineTo(absRight,absBot/3);
        LineTo lineR2 = new LineTo(absRight,absBot/2);
        LineTo lineR3 = new LineTo(absRight,absBot/1.5);
        path.getElements().add(moveTo);
        path.getElements().addAll(lineL1,lineT2,lineR3,lineB4,lineT4,lineB3,lineT2,lineL3,lineB1,lineT3,lineR2,lineB3,lineLT
        ,lineB2,lineT4,lineR2,lineB3,lineL1,lineT2,lineR3,lineB4,lineT4,lineB3,lineT2,lineL3,lineB1,lineT3,
                lineR2,lineB3,lineLT,lineB2,lineT4,lineR2,lineB3,lineT2,lineL3,lineB1,lineT3,lineR2,lineB3,lineLT
                ,lineB2,lineT4,lineR2,lineB3,lineL1,lineT2,lineR3,lineB4,lineT4,lineB3,lineT2,lineL3,lineB1,lineT3,
                lineR2,lineB3,lineLT,lineB2,lineT4,lineR2,lineB3,lineL1,lineT2,lineR3,lineB4,lineT4,lineB3,lineT2,lineL3,lineB1,lineT3,lineR2,lineB3,lineLT
                ,lineB2,lineT4,lineR2,lineB3,lineL1,lineT2,lineR3,lineB4,lineT4,lineB3,lineT2,lineL3,lineB1,lineT3,
                lineR2,lineB3,lineLT,lineB2,lineT4,lineR2,lineB3,lineT2,lineL3,lineB1,lineT3,lineR2,lineB3,lineLT
                ,lineB2,lineT4,lineR2,lineB3,lineL1,lineT2,lineR3,lineB4,lineT4,lineB3,lineT2,lineL3,lineB1,lineT3,
                lineR2,lineB3,lineLT,lineB2,lineT4,lineR2,lineB3,lineT1,lineL1,lineB2,lineT4,lineR1,lineB5,lineT4,lineB3,lineT2,lineB1,lineMidMid);
        gameTransition.setDuration(Duration.seconds(300));
        gameTransition.setNode(imageView);
        gameTransition.setPath(path);
        gameTransition.setCycleCount(1);
        gameTransition.setAutoReverse(false);
        gameTransition.play();
        Timeline timeline3 = new Timeline(
                new KeyFrame(Duration.seconds(3)));
        timeline3.play();
        timeline3.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                grid.setId("blue");
                gameTransition.setRate(3);
            }
        });
        Timeline timeline15 = new Timeline(
                new KeyFrame(Duration.seconds(15)));
        timeline15.play();
        timeline15.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                grid.setId("red");
                grid.add(mirrorImage,0,0);
                Path mpath = new Path();
                MoveTo moveTo = new MoveTo(lineLT.getX(), lineLT.getY());
                mpath.getElements().add(moveTo);
                mpath.getElements().addAll(lineB4,lineR3,lineT3,lineB1,lineL2,lineT1,lineB3,lineRT);
                mirrorTransition.setDuration(Duration.seconds(60));
                mirrorTransition.setNode(mirrorImage);
                mirrorTransition.setPath(mpath);
                mirrorTransition.setCycleCount(20);
                mirrorTransition.setAutoReverse(true);
                mirrorTransition.play();
            }
        });
        Timeline timeline19 = new Timeline(
                new KeyFrame(Duration.millis(18500)));
        timeline19.play();
        timeline19.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                grid.setId("black");
                grid.add(mirrorImage1,0,0);
                Path m1path = new Path();
                MoveTo moveTo = new MoveTo(lineRT.getX(), lineRT.getY());
                m1path.getElements().add(moveTo);
                m1path.getElements().addAll(lineB5,lineT4,lineB3,lineT2,lineB1,lineL2,lineT1,lineB2,lineT3,lineB4,lineRT);
                mirrorTransition1.setDuration(Duration.seconds(60));
                mirrorTransition1.setNode(mirrorImage1);
                mirrorTransition1.setPath(m1path);
                mirrorTransition1.setCycleCount(20);
                mirrorTransition1.setAutoReverse(true);
                mirrorTransition1.play();
            }
        });
        Timeline timeline22 = new Timeline(
                new KeyFrame(Duration.millis(22500)));
        timeline22.play();
        timeline22.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                grid.setId("blue");
                grid.add(mirrorImage2,0,0);
                Path m2path = new Path();
                MoveTo moveTo = new MoveTo(lineRB.getX(), lineRB.getY());
                m2path.getElements().add(moveTo);
                m2path.getElements().addAll(lineT3,lineB1,lineL3,lineT2,lineB4,lineR2,lineT5,lineB3,lineT1,lineL1,lineB2,lineT4,lineRT);
                mirrorTransition2.setDuration(Duration.seconds(60));
                mirrorTransition2.setNode(mirrorImage2);
                mirrorTransition2.setPath(m2path);
                mirrorTransition2.setCycleCount(20);
                mirrorTransition2.setAutoReverse(true);
                mirrorTransition2.play();
                mirrorTransition.setRate(6);
                mirrorTransition1.setRate(6);
                mirrorTransition2.setRate(6);
                mirrorTransition3.setRate(6);
                gameTransition.setRate(4);
            }
        });

        Timeline timeline23 = new Timeline(
                new KeyFrame(Duration.millis(23300)));
        timeline23.play();
        timeline23.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                grid.setId("black");
                grid.add(mirrorImage3,0,0);
                Path m3path = new Path();
                MoveTo moveTo = new MoveTo(lineLB.getX(), lineLB.getY());
                m3path.getElements().add(moveTo);
                m3path.getElements().addAll(lineR3,lineL2,lineR1,lineT3,lineL1,lineR3,lineB4,lineT1,lineL2,lineB3,lineRT);
                mirrorTransition3.setDuration(Duration.seconds(60));
                mirrorTransition3.setNode(mirrorImage3);
                mirrorTransition3.setPath(m3path);
                mirrorTransition3.setCycleCount(20);
                mirrorTransition3.setAutoReverse(true);
                mirrorTransition3.play();
            }
        });
        Timeline timeline29 = new Timeline(
                new KeyFrame(Duration.millis(29400)));
        timeline29.play();
        timeline29.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                grid.setId("blue");
                mirrorTransition.setRate(10);
                mirrorTransition1.setRate(10);
                mirrorTransition2.setRate(10);
                mirrorTransition3.setRate(10);
                gameTransition.setRate(5);
            }
        });

        Timeline timeline43 = new Timeline(
                new KeyFrame(Duration.millis(43000)));
        timeline43.play();
        timeline43.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                grid.setId("black");
                rotateTransition.setInterpolator(Interpolator.LINEAR);
                rotateTransition.setAxis(Rotate.Z_AXIS);
                rotateTransition.setNode(imageView);
                rotateTransition.setByAngle(360);
                rotateTransition.setCycleCount(Animation.INDEFINITE);
                rotateTransition.setDuration(Duration.millis(2000));
                rotateTransition.setAutoReverse(false);
                rotateTransition.play();
                rotateTransition1.setInterpolator(Interpolator.LINEAR);
                rotateTransition1.setAxis(Rotate.Z_AXIS);
                rotateTransition1.setNode(mirrorImage);
                rotateTransition1.setByAngle(360);
                rotateTransition1.setCycleCount(Animation.INDEFINITE);
                rotateTransition1.setDuration(Duration.millis(2000));
                rotateTransition1.setAutoReverse(false);
                rotateTransition1.play();
                rotateTransition2.setInterpolator(Interpolator.LINEAR);
                rotateTransition2.setAxis(Rotate.Z_AXIS);
                rotateTransition2.setNode(mirrorImage1);
                rotateTransition2.setByAngle(360);
                rotateTransition2.setCycleCount(Animation.INDEFINITE);
                rotateTransition2.setDuration(Duration.millis(2000));
                rotateTransition2.setAutoReverse(false);
                rotateTransition2.play();
                rotateTransition3.setInterpolator(Interpolator.LINEAR);
                rotateTransition3.setAxis(Rotate.Z_AXIS);
                rotateTransition3.setNode(mirrorImage2);
                rotateTransition3.setByAngle(360);
                rotateTransition3.setCycleCount(Animation.INDEFINITE);
                rotateTransition3.setDuration(Duration.millis(2000));
                rotateTransition3.setAutoReverse(false);
                rotateTransition3.play();
                rotateTransition3.setInterpolator(Interpolator.LINEAR);
                rotateTransition4.setAxis(Rotate.Z_AXIS);
                rotateTransition4.setNode(mirrorImage3);
                rotateTransition4.setByAngle(360);
                rotateTransition4.setCycleCount(Animation.INDEFINITE);
                rotateTransition4.setDuration(Duration.millis(2000));
                rotateTransition4.setAutoReverse(false);
                rotateTransition4.play();
            }
        });
        Timeline timeline60 = new Timeline(
                new KeyFrame(Duration.millis(59800)));
        timeline60.play();
        timeline60.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gameTransition.stop();
                mirrorTransition.stop();
                mirrorTransition1.stop();
                mirrorTransition2.stop();
                mirrorTransition3.stop();
                rotateTransition.stop();
                rotateTransition1.stop();
                rotateTransition2.stop();
                rotateTransition3.stop();
                rotateTransition4.stop();
            }
        });

    }

    private static void addStartButton(GridPane grid){
        grid.add(startButton,0,0);
    }

    private static void dvdIntro(GridPane grid){

        grid.getColumnConstraints().clear();
        grid.getRowConstraints().clear();

        Image image = new Image("img/DVD.png");
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitWidth(StartScreen.getScreenWidth()/5);
        imageView.setFitHeight(StartScreen.getScreenHeight()/5);
        int absLeft = (int)(imageView.getFitWidth()/2);
        int absTop = (int)(imageView.getFitHeight()/2);
        int absRight = (int)(StartScreen.getScreenWidth()-imageView.getFitWidth()/2);
        int absBot = (int)(StartScreen.getScreenHeight()-imageView.getFitHeight()/2);
        double height = StartScreen.getScreenHeight();
        double width = StartScreen.getScreenWidth();
        grid.add(imageView,(int)width/2,(int)height/2);
        Path path = new Path();
        MoveTo moveTo = new MoveTo(width/2, height/2);
        LineTo line1 = new LineTo(absLeft,absTop);
        LineTo line2 = new LineTo(absRight,absBot);
        LineTo line3 = new LineTo(width/2,height/2);
        path.getElements().add(moveTo);
        path.getElements().addAll(line1,line2,line3);
        pathTransition.setDuration(Duration.seconds(1));
        pathTransition.setNode(imageView);
        pathTransition.setPath(path);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(false);
        pathTransition.play();
        pathTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                addStartButton(grid);
            }
        });
    }

    private static void addButtons(GridPane grid){

        Button lagA;
        Button lagB;
        lagA = new Button(StartScreen.getLagnamn1());
        lagB = new Button(StartScreen.getLagnamn2());

        //setupScreen(grid,lagA,lagB);
        /*
        lagA.setOnAction(e -> {
            new creenTransitionFrom(grid);
            scaleTransition.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    grid.getChildren().clear();
                    grid.getColumnConstraints().clear();
                    grid.getRowConstraints().clear();
                    grid.setBackground(null);
                    GameShowPanel.result(grid,3,0);
                }
            });
        });
        lagB.setOnAction(e -> {
            screenTransitionFrom(grid);
            scaleTransition.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    grid.getChildren().clear();
                    grid.getColumnConstraints().clear();
                    grid.getRowConstraints().clear();
                    grid.setBackground(null);
                    GameShowPanel.result(grid,0,3);
                }
            });

        });

         */
    }

    private static void setupScreen(GridPane grid){
        grid.setPadding(new Insets(0,0,0,0));
        grid.setVgap(0);
        grid.setHgap(0);
/*
        GridPane.setConstraints(lagA,0,4);
        GridPane.setConstraints(lagB,6,4);
        GridPane.setHalignment(lagA,HPos.CENTER);
        GridPane.setHalignment(lagB,HPos.CENTER);

        lagA.setId("teamButtons");
        lagB.setId("teamButtons");

        GridPane.setFillWidth(lagA,true);
        GridPane.setFillWidth(lagB,true);

        grid.getChildren().addAll(lagA,lagB);
*/
    }
}
