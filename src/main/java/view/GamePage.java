package view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Parent;

import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Ball;
import model.BallAnimation;
import model.Table;
import model.Wood;

public class GamePage extends Application {
    private static Pane pane;
    private static Wood wood;
    public void start(Stage stage) throws Exception {
        Table table = Table.getInstance();
        Pane pane = new Pane(table);
        Ball.drawBalls(pane);
        for (int i = 0; i < 21; i += 2)
            pane.getChildren().add(Ball.getBalls().get(i));
        Wood wood = Wood.getInstance(pane);
        pane.getChildren().add(wood);
        GamePage.pane = pane;
        GamePage.wood = wood;
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        table.requestFocus();
        stage.show();
    }
    public static void addWood() {
        pane.getChildren().add(wood); }
    public static void removeWood() {
        pane.getChildren().remove(wood); }
    public static void main(String[] args) {
        launch();
    }
}
