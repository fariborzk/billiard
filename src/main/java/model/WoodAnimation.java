package model;

import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import view.GamePage;

public class WoodAnimation {
    private TranslateTransition backAnimation;
    private TranslateTransition forwardAnimation;
    private Wood wood;
    public WoodAnimation(Wood wood) {
        this.wood = wood;
        backAnimation = new TranslateTransition(Duration.millis(200), wood);
        forwardAnimation = new TranslateTransition(Duration.millis(400), wood);
        backAnimation.setCycleCount(1);
        forwardAnimation.setCycleCount(1);
        backAnimation.setByX(77 * Math.cos(Math.toRadians(wood.getTheta())));
        backAnimation.setByY(-77 * Math.sin(Math.toRadians(wood.getTheta())));
        forwardAnimation.setByX(-95 * Math.cos(Math.toRadians(wood.getTheta())));
        forwardAnimation.setByY(95 * Math.sin(Math.toRadians(wood.getTheta())));
        backAnimation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                backAnimation.stop();
                forwardAnimation.play();
            }
        });
        forwardAnimation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                forwardAnimation.stop();
                //GamePage.removeWood();
                wood.setDisable(true);
                wood.setVisible(false);
                Table.updateHits();
                Ball.getWhiteBall().setTheta(wood.getTheta() + 180);
                Ball.getWhiteBall().setV(Table.getInstance().getSlider().getValue() / 25);
                BallAnimation.getBallAnimation().play();

            }
        });

    }
    public void play() { backAnimation.play(); }
}
