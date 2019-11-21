import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class BonusSpin {

    private static ScaleTransition scaleTransition = new ScaleTransition();

    public static void display(GridPane grid){

        Button lagA;
        Button lagB;
        lagA = new Button(StartScreen.getLagnamn1());
        lagB = new Button(StartScreen.getLagnamn2());
        setupScreen(grid,lagA,lagB);


        lagA.setOnAction(e -> {
            screenTransitionFrom(grid);
            scaleTransition.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    grid.getChildren().clear();
                    grid.getColumnConstraints().clear();
                    grid.getRowConstraints().clear();
                    //mediaPlayer.stop();
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
                    //mediaPlayer.stop();
                    grid.setBackground(null);
                    GameShowPanel.result(grid,0,3);
                }
            });

        });
        screenTransitionTo(grid);
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
        scaleTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                grid.getChildren().remove(circle);
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
}
