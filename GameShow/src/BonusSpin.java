import javafx.animation.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class BonusSpin {

    private static ScaleTransition scaleTransition = new ScaleTransition();
    private static RotateTransition rotateTransition = new RotateTransition();
    private static ImageView bonusWheel = new ImageView("bonusWheel.png");
    private static boolean gameEnabled = true;

    public static void display(GridPane grid){

        Button lagA;
        Button lagB;
        lagA = new Button(StartScreen.getLagnamn1());
        lagB = new Button(StartScreen.getLagnamn2());
        setupScreen(grid,lagA,lagB);

        grid.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent key) {
                if (key.getCode().toString().equalsIgnoreCase("s")) {
                    slowDownWheel();
                }

            }
        });

        lagA.setOnAction(e -> {
            screenTransitionFrom(grid);
            scaleTransition.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    grid.getChildren().clear();
                    grid.getColumnConstraints().clear();
                    grid.getRowConstraints().clear();
                    gameEnabled = false;
                    //mediaPlayer.stop();
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
                    gameEnabled = false;
                    //mediaPlayer.stop();
                    GameShowPanel.result(grid,0,3);
                }
            });
        });
        addWheel(grid);
        screenTransitionTo(grid);

    }

    private static void addWheel(GridPane grid){

        bonusWheel.setFitHeight(StartScreen.getScreenHeight()/1.3);
        bonusWheel.setFitWidth(StartScreen.getScreenHeight()/1.3);
        GridPane.setHalignment(bonusWheel,HPos.CENTER);
        GridPane.setValignment(bonusWheel,VPos.CENTER);
        grid.add(bonusWheel,1,2);
        ImageView wheelPointer = new ImageView("wheelPointer.png");
        wheelPointer.setFitWidth(StartScreen.getScreenHeight()/20);
        wheelPointer.setFitHeight(StartScreen.getScreenHeight()/15);
        GridPane.setValignment(wheelPointer,VPos.CENTER);
        GridPane.setHalignment(wheelPointer,HPos.CENTER);
        grid.add(wheelPointer,1,0);
        runAnimation(bonusWheel);
    }

    private static void runAnimation(ImageView bonusWheel){
        rotateTransition.setInterpolator(Interpolator.LINEAR);
        rotateTransition.setAxis(Rotate.Z_AXIS);
        rotateTransition.setNode(bonusWheel);
        rotateTransition.setByAngle(360);
        rotateTransition.setCycleCount(Animation.INDEFINITE);
        rotateTransition.setDuration(Duration.millis(3000));
        rotateTransition.setAutoReverse(false);
        rotateTransition.play();
    }

    private static void getResult(){
        double rotateAngle = bonusWheel.getRotate();
        while(rotateAngle > 360){
            rotateAngle = rotateAngle-360;
        }
        if(rotateAngle < 22.5 && rotateAngle > 0){
            System.out.println(16);
        }
        else if(rotateAngle < 22.5*2 && rotateAngle > 22.5){
            System.out.println(15);
        }
        else if(rotateAngle < 22.5*3 && rotateAngle > 22.5*2){
            System.out.println(14);
        }
        else if(rotateAngle < 22.5*4 && rotateAngle > 22.5*3){
            System.out.println(13);
        }
        else if(rotateAngle < 22.5*5 && rotateAngle > 22.5*4){
            System.out.println(12);
        }
        else if(rotateAngle < 22.5*6 && rotateAngle > 22.5*5){
            System.out.println(11);
        }
        else if(rotateAngle < 22.5*7 && rotateAngle > 22.5*6){
            System.out.println(10);
        }
        else if(rotateAngle < 22.5*8 && rotateAngle > 22.5*7){
            System.out.println(9);
        }
        else if(rotateAngle < 22.5*9 && rotateAngle > 22.5*8){
            System.out.println(8);
        }
        else if(rotateAngle < 22.5*10 && rotateAngle > 22.5*9){
            System.out.println(7);
        }
        else if(rotateAngle < 22.5*11 && rotateAngle > 22.5*10){
            System.out.println(6);
        }
        else if(rotateAngle < 22.5*12 && rotateAngle > 22.5*11){
            System.out.println(5);
        }
        else if(rotateAngle < 22.5*13 && rotateAngle > 22.5*12){
            System.out.println(4);
        }
        else if(rotateAngle < 22.5*14 && rotateAngle > 22.5*13){
            System.out.println(3);
        }
        else if(rotateAngle < 22.5*15 && rotateAngle > 22.5*14){
            System.out.println(2);
        }
        else if(rotateAngle < 22.5*16 && rotateAngle > 22.5*15){
            System.out.println(1);
        }
        else {
            System.out.println("ERROR");
        }
    }

    private static void slowDownWheel(){
        final IntegerProperty i = new SimpleIntegerProperty(0);
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(1),
                        event -> {
                            i.set(i.get() +1);
                            if(i.get() < 30){
                                rotateTransition.stop();
                                rotateTransition.setDuration(Duration.seconds(3 +i.get()));
                                rotateTransition.play();
                            }
                            else {
                                rotateTransition.pause();
                                getResult();
                            }

                        }
                )
        );
        timeline.setCycleCount(30);
        timeline.play();
    }

    private static void setupScreen(GridPane grid,Button lagA,Button lagB){

        GridPane.setConstraints(lagA,0,4);
        GridPane.setConstraints(lagB,2,4);
        GridPane.setHalignment(lagA,HPos.CENTER);
        GridPane.setHalignment(lagB,HPos.CENTER);

        lagA.setId("teamButtons");
        lagB.setId("teamButtons");

        GridPane.setFillWidth(lagA,true);
        GridPane.setFillWidth(lagB,true);

        grid.getChildren().addAll(lagA,lagB);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(15);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(70);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(15);
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
        grid.getColumnConstraints().addAll(col1,col2,col3);
    }


    private static void screenTransitionTo(GridPane grid){
        Circle circle = new Circle();
        circle.setFill(Color.BLACK);
        circle.setCenterX(50);
        circle.setCenterY(50);
        circle.setRadius(1400);
        GridPane.setHalignment(circle,HPos.CENTER);
        GridPane.setValignment(circle,VPos.CENTER);
        grid.add(circle,1,2);

        scaleTransition.setDuration(Duration.millis(1000));
        scaleTransition.setByX(-1.0);
        scaleTransition.setByY(-1.0);
        scaleTransition.setCycleCount(1);
        scaleTransition.setNode(circle);
        scaleTransition.setAutoReverse(false);
        scaleTransition.play();

    }

    private static void screenTransitionFrom(GridPane grid){
        System.out.println(rotateTransition.getCurrentTime());
        Circle circle = new Circle();
        circle.setFill(Color.BLACK);
        circle.setCenterX(1);
        circle.setCenterY(1);
        circle.setRadius(1);
        GridPane.setHalignment(circle,HPos.CENTER);
        GridPane.setValignment(circle,VPos.CENTER);
        grid.add(circle,1,2);
        scaleTransition.setDuration(Duration.millis(1000));
        scaleTransition.setByX(1400);
        scaleTransition.setByY(1400);
        scaleTransition.setCycleCount(1);
        scaleTransition.setNode(circle);
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();

    }
}
