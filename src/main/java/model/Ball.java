package model;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import sun.rmi.runtime.NewThreadAction;

import java.util.ArrayList;
public class Ball extends Circle {
    private static ArrayList<Ball> balls = new ArrayList<>();
    private static BallAnimation ballAnimation = new BallAnimation();
    private static Ball whiteBall;
    private int score;
    private double v, theta;
    private Pane parent;
    private Ball(int x, int y, int score, String address, Pane pane) {
        super(x, y, 22);
        Image image = new Image(getClass().getResource(address).toExternalForm(), false);
        ImagePattern imagePattern = new ImagePattern(image);
        this.v = 0;
        this.setFill(imagePattern);
        this.theta = 0;
        this.parent = pane;
        this.score = score;
        balls.add(this);
    }
    public static void drawBalls(Pane pane) {
        balls.add(new Ball(450, 360, -100, "/view/images/0.png", pane));
        balls.add(new Ball(800, 360, 1, "/view/images/1.png", pane));
        balls.add(new Ball(844, 382, 2, "/view/images/2.png", pane));
        balls.add(new Ball(844, 338, 3, "/view/images/3.png", pane));
        balls.add(new Ball(888, 316, 4, "/view/images/4.png", pane));
        balls.add(new Ball(888, 360, 16, "/view/images/8.png", pane));
        balls.add(new Ball(888, 404, 5, "/view/images/5.png", pane));
        balls.add(new Ball(932, 426, 6, "/view/images/6.png", pane));
        balls.add(new Ball(932, 382, 7, "/view/images/7.png", pane));
        balls.add(new Ball(932, 338, 9, "/view/images/9.png", pane));
        balls.add(new Ball(932, 294, 10, "/view/images/10.png", pane));
        Ball.whiteBall = balls.get(0);
    }
    public Pane GetParent() { return this.parent; }
    public int getScore() { return this.score; }
    public BallAnimation getBallAnimation() { return this.ballAnimation; }
    public static ArrayList<Ball> getBalls() { return balls; }
    public void move(double dx, double dy) {
        this.setCenterX(this.getCenterX() + dx);
        this.setCenterY(this.getCenterY() + dy);
    }
    public void setTheta(double theta) { this.theta = theta; }
    public void setV(double v) { this.v = v; }
    public double getTheta() { return this.theta; }
    public double getV() { return this.v; }
    public boolean hasCollisionWithBall(Ball ball) {
        return ball.getBoundsInParent().intersects(this.getLayoutBounds());
    }
    public boolean hasCollisionWithWall(Line line) {
        return line.getBoundsInParent().intersects(this.getLayoutBounds());
    }
    public boolean hasCollisionWithWood(Rectangle wood) {
        return wood.getBoundsInParent().intersects(this.getLayoutBounds());
    }
    public boolean hasCollisionWithHole(Circle hole) {
        //System.out.println("4444444444444444444");
        return hole.getLayoutBounds().intersects(this.getLayoutBounds());
    }
    public static Ball getWhiteBall() { return Ball.whiteBall; }
    public void applyCollision(Ball ball) {
        double xDist, yDist;
        Ball A = this;
        Ball B = ball;
        xDist = A.getCenterX() - B.getCenterX();
        yDist = A.getCenterY() - B.getCenterY();
        double distSquared = xDist * xDist + yDist * yDist;
        if (distSquared <= 4 * ball.getRadius() * ball.getRadius()) {
            double aV_X, aV_Y, bV_X, bV_Y;
            bV_X = B.getV() * Math.cos(Math.toRadians(B.getTheta()));
            aV_X = A.getV() * Math.cos(Math.toRadians(A.getTheta()));
            bV_Y = B.getV() * Math.sin(Math.toRadians(B.getTheta()));
            aV_Y = A.getV() * Math.sin(Math.toRadians(A.getTheta()));
            double xVelocity = bV_X - aV_X;
            double yVelocity = bV_Y - aV_Y;
            double dotProduct = xDist * xVelocity + yDist * yVelocity;
            if (dotProduct > 0) {
                //System.out.println("6666666666666666666666666666");
                double collisionScale = dotProduct / distSquared;
                double xCollision = xDist * collisionScale;
                double yCollision = yDist * collisionScale;
                double collisionWeightA = 1;
                double collisionWeightB = 1;
                aV_X = collisionWeightA * xCollision + aV_X;
                aV_Y = collisionWeightA * yCollision + aV_Y;
                bV_X = bV_X - collisionWeightB * xCollision;
                bV_Y = bV_Y - collisionWeightB * yCollision;
                A.setV(Math.sqrt(aV_X * aV_X + aV_Y * aV_Y));
                B.setV(Math.sqrt(bV_X * bV_X + bV_Y * bV_Y));
                A.setTheta(Math.toDegrees(Math.atan2(aV_Y, aV_X)));
                B.setTheta(Math.toDegrees(Math.atan2(bV_Y, bV_X)));

                if (A.getTheta() < -360) {
                    A.setTheta(A.getTheta() + 360);
                }
                if (B.getTheta() < -360) {
                    B.setTheta(B.getTheta() + 360);
                }
                if (A.getTheta() > 360) {
                    A.setTheta(A.getTheta() - 360);
                }
                if (B.getTheta() > 360) {
                    B.setTheta(B.getTheta() - 360);
                }
            }
        }
    }
    public void applyPocket() {
        if (Table.getTotalHits() < 0)
            Table.setTotalScore(-this.score);
        else
            Table.setTotalScore(this.score);
        Table.updateScore();
        if (this == whiteBall || Table.getTotalScore() < 0)
            System.exit(0);
        this.parent.getChildren().remove(this);
    }
    public void getPocket() {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(300), this);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setCycleCount(1);
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), this);
        scaleTransition.setByX(-1f);
        scaleTransition.setByY(-1f);
        scaleTransition.setCycleCount(1);
        ParallelTransition parallelTransition = new ParallelTransition(fadeTransition, scaleTransition);
        balls.remove(this);
        balls.remove(this);
        parallelTransition.play();
        fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Ball.this.applyPocket();
            }
        });
    }

    public void decrementV() { this.v -= .001;}
}
