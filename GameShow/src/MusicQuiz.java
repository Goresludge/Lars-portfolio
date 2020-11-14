import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class MusicQuiz {

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

    static void display(GridPane grid){

        setupPanel(grid);
        grid.setId("MusicQuiz");
        new ScreenTransitionTo(grid,3,2);
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
        GridPane.setHalignment(finishButton,HPos.CENTER);
        finishButton.setId("musicLabels");
        grid.add(finishButton,3,4);
        finishButton.setOnAction(e -> {
            new ScreenTransitionFrom(grid,3,2);
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1)));
            timeline.play();
            timeline.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    grid.getChildren().clear();
                    grid.getColumnConstraints().clear();
                    grid.getRowConstraints().clear();
                    returnResult(grid);
                }
            });
        });

    }


    private static void setupPanel(GridPane grid){

        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);


        Font.loadFont(Jeopardy.class.getResource("font/Chalkduster.ttf").toExternalForm(), 10);

        labelPointLag1.setId("musicLabels");
        labelPointLag2.setId("musicLabels");
        lagnamn1.setId("musicLabels");
        lagnamn2.setId("musicLabels");
        grid.add(labelPointLag1,0,1,2,1);
        grid.add(labelPointLag2,5,1,2,1);
        GridPane.setHalignment(labelPointLag1, HPos.CENTER);
        GridPane.setHalignment(labelPointLag2,HPos.CENTER);
        grid.add(lagnamn1,0,0,2,1);
        grid.add(lagnamn2,5,0,2,1);
        GridPane.setHalignment(lagnamn1,HPos.CENTER);
        GridPane.setHalignment(lagnamn2,HPos.CENTER);
        lag1plus.setId("musicLabels");
        lag1minus.setId("musicLabels");
        lag2plus.setId("musicLabels");
        lag2minus.setId("musicLabels");
        GridPane.setConstraints(lag1plus,1,2);
        GridPane.setConstraints(lag1minus,0,2);
        GridPane.setConstraints(lag2plus,6,2);
        GridPane.setConstraints(lag2minus,5,2);
        GridPane.setHalignment(lag1plus,HPos.CENTER);
        GridPane.setHalignment(lag1minus,HPos.CENTER);
        GridPane.setHalignment(lag2plus,HPos.CENTER);
        GridPane.setHalignment(lag2minus,HPos.CENTER);

        grid.getChildren().addAll(lag1plus,lag2plus,lag1minus,lag2minus);

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


    private static void returnResult(GridPane grid){
        GameShowPanel.result(grid,pointsLag1,pointsLag2);
    }

}
