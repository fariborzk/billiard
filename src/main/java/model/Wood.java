package model;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import javax.crypto.spec.PSource;
import java.security.PublicKey;

public class Wood extends Rectangle {
    private static Wood wood;
    private Rotate rotate;
    double theta, mouseDeltaTheta, keyDeltaTheta;
    private Parent parent;
    private Wood(Parent parent) {
        super(0 , 355, 410, 10);
        this.rotate = new Rotate(0, Ball.getWhiteBall().getCenterX(),Ball.getWhiteBall().getCenterY());
        this.getTransforms().add(rotate);
        ImagePattern imagePattern = new ImagePattern(new Image(getClass().getResource("/view/images/wood.png").toExternalForm(),
                false));
        this.parent = parent;
        theta = 180;
        mouseDeltaTheta = .5;
        keyDeltaTheta = 1;
        this.setFill(imagePattern);
    }
    public Rotate getRotation() { return rotate; }
    public double getMouseDeltaTheta() { return mouseDeltaTheta; }
    public double getKeyDeltaTheta() { return keyDeltaTheta; }
    public double getTheta() { return this.theta; }
    public Parent getPane() { return this.parent; }
    public void setTheta(double theta) { this.theta = theta; }
    public static Wood getInstance(Parent parent) {
        wood = new Wood(parent);
        return wood;
    }
    public static Wood getInstance() { return wood; }
    public void setRotation() {
        wood.getRotation().setPivotX(Ball.getWhiteBall().getCenterX());
        wood.getRotation().setPivotY(Ball.getWhiteBall().getCenterY());
        wood.getRotation().setAngle(180 - wood.getTheta());
    }

    public void changeRotation(Direction direction, Role role) {
        if (direction == Direction.DOWN)
            wood.setTheta(wood.getTheta() + (role == Role.KEY ? wood.getKeyDeltaTheta() : wood.getMouseDeltaTheta()));
        else
            wood.setTheta(wood.getTheta() - (role == Role.KEY ? wood.getKeyDeltaTheta() : wood.getMouseDeltaTheta()));
        wood.setRotation();
    }

    public void createRotation() {
        wood.setTheta(180);
        parent.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println(event.getX()  + "  " + event.getY());
            }
        });
        wood.getTransforms().remove(rotate);
        rotate = new Rotate(0, Ball.getWhiteBall().getCenterX(), Ball.getWhiteBall().getCenterY());
        wood.setY(wood.getRotation().getPivotY() - 5);
        wood.setX(wood.getRotation().getPivotX() - 450);
        wood.getTransforms().add(rotate);
    }
}
