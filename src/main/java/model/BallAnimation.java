package model;


import com.google.gson.internal.bind.util.ISO8601Utils;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.Transition;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import javax.crypto.Cipher;
import java.util.ArrayList;
import java.util.EventListener;

public class BallAnimation extends Transition {
    private static ArrayList<Ball> balls;
    private static ArrayList<Line> sideWalls = Table.getInstance().getSideWalls();
    private static ArrayList<Line> topWalls = Table.getInstance().getTopWalls();
    private static ArrayList<Line> subDiagonalWalls = Table.getInstance().getSubDiagonalWalls();
    private static ArrayList<Line> diagonalWalls = Table.getInstance().getDiagonalWalls();
    private static ArrayList<Circle> holes = Table.getInstance().getHoles();
    private Ball currentBall ;
    private AnimationTimer timer;
    public Ball getCurrentBall() { return currentBall; }
    public void setCurrentBall(Ball currentBall) { this.currentBall = currentBall; }
    private static BallAnimation ballAnimation;
    public BallAnimation() {
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(-1);
        balls = Ball.getBalls();
    }
    @Override
    protected void interpolate(double frac) {
        int c = 0;
        for (int i = 0; i < balls.size(); i += 2) {
            if (balls.get(i).getV() < .1)
                balls.get(i).setV(0);
            if (balls.get(i).getV() <= 0)
                c++;
        }
        //System.out.println("1111111111111111111111111111111");
        if (c == balls.size() / 2 && Table.getInstance().getPowerChoose()) {
            this.stop();
            Table.getInstance().prepareTable();
            return;
        }
        for (int i = 0; i < balls.size(); i += 2) {
            Ball ball = balls.get(i);
            double dy = -ball.getV() * Math.sin(Math.toRadians(ball.getTheta()));
            double dx = ball.getV() * Math.cos(Math.toRadians(ball.getTheta()));
            ball.decrementV();
            //System.out.println("333333333333333333333");
            ball.move(dx, dy);
            for (int j = 0; j < holes.size(); j++) {
                if (ball.hasCollisionWithHole(holes.get(j))) {
                    ball.getPocket();
                    return;
                }
            }

            //System.out.println("55555555555555555555555555");
            for (int j = 0; j < balls.size(); j += 2) {
                System.out.println("001 " + Thread.currentThread());
                double xDist = (ball.getCenterX() - balls.get(j).getCenterX());
                double yDist = (ball.getCenterY() - balls.get(j).getCenterY());
                double distSquared = xDist * xDist + yDist * yDist;
                if (ball != balls.get(j) && Table.getInstance().getPowerChoose() && distSquared <= 4 * ball.getRadius() * ball.getRadius()) {
                    ball.applyCollision(balls.get(j));
                }
            }
            //System.out.println("7777777777777777777777777");
            for (int j = 0; j < 4; j++) {
                if (ball.hasCollisionWithWall(topWalls.get(j))) {
                    ball.setTheta(-ball.getTheta());
                    return;
                }
            }
            for (int j = 0; j < 2; j++) {
                if (ball.hasCollisionWithWall(sideWalls.get(j))) {
                    ball.setTheta(180 - ball.getTheta());
                    return;
                }
            }
            for (int j = 0; j < 6; j++) {
                if (ball.hasCollisionWithWall(diagonalWalls.get(j))) {
                    ball.setTheta(90 - ball.getTheta());
                    return;
                }
            }
            for (int j = 0; j < 6; j++) {
                if (ball.hasCollisionWithWall(subDiagonalWalls.get(j))) {
                    ball.setTheta(90 - ball.getTheta());
                    return;
                }
            }
        }
    }
    public static BallAnimation getBallAnimation() {
        if (ballAnimation == null)
            ballAnimation = new BallAnimation();
        return ballAnimation;
    }

}
