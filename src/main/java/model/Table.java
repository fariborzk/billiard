package model;

import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import view.GamePage;

import java.util.ArrayList;

public class Table extends Pane {
    private static Table Instance = null;
    private BallAnimation ballAnimation;
    private int  totalScore;
    private int totalHits;
    private boolean isDirChoose;
    private boolean isPowerChoose;
    private Text textHitsRemain;
    private Text textScore;
    private Slider slider;
    private ArrayList<Circle> holes;
    private String strHits;
    private String strScore;
    private ArrayList<Line> topWalls;
    private ArrayList<Line> sideWalls;
    private ArrayList<Line> diagonalWalls;
    private ArrayList<Line> subDiagonalWalls;
    private Table() {
        super();
        this.isDirChoose = false;
        this.isPowerChoose = false;
        this.totalScore = 0;
        this.totalHits = 40;
        this.strHits = "REMAINING HITS :";
        this.strScore = "SCORE : ";
        topWalls = new ArrayList<>();
        sideWalls = new ArrayList<>();
        diagonalWalls = new ArrayList<>();
        subDiagonalWalls = new ArrayList<>();
        holes = new ArrayList<>();
        ImageView imageView = new ImageView(getClass().getResource("/view/images/table.png").toExternalForm());
        imageView.setFitWidth(1280);
        imageView.setFitHeight(720);
        this.getChildren().add(imageView);
        this.sideWalls.add(new Line(82, 122, 82, 595));
        this.topWalls.add(new Line(120, 638, 592, 638));
        this.topWalls.add(new Line(673, 638, 1151, 638));
        this.sideWalls.add(new Line(1197, 125, 1197, 595));
        this.topWalls.add(new Line(673, 81, 1155, 81));
        this.topWalls.add(new Line(116, 81, 590, 81));
        this.diagonalWalls.add(new Line(60, 620, 84, 595));
        this.diagonalWalls.add(new Line(94, 661, 118, 638));
        this.subDiagonalWalls.add(new Line(116, 82, 93, 60));
        this.subDiagonalWalls.add(new Line(83, 125, 60, 102));
        this.diagonalWalls.add(new Line(593, 82, 603, 61));
        this.subDiagonalWalls.add(new Line(662, 62, 673, 83));
        this.diagonalWalls.add(new Line(1154, 83, 1175, 62));
        this.diagonalWalls.add(new Line(1219, 102, 1197, 125));
        this.subDiagonalWalls.add(new Line(1199, 595, 1219, 617));
        this.subDiagonalWalls.add(new Line(1155, 638, 1176, 660));
        this.subDiagonalWalls.add(new Line(594, 639, 603, 660));
        this.diagonalWalls.add(new Line(664, 663, 672, 637));
        for (int i = 0; i < 4; i++) {
            topWalls.get(i).setOpacity(0);
            this.getChildren().add(topWalls.get(i));
        }
        for (int i = 0; i < 2; i++) {
            sideWalls.get(i).setOpacity(0);
            this.getChildren().add(sideWalls.get(i));
        }
        for (int i = 0; i < 6; i++) {
            diagonalWalls.get(i).setOpacity(0);
            this.getChildren().add(diagonalWalls.get(i));
        }
        for (int i = 0; i < 6; i++) {
            subDiagonalWalls.get(i).setOpacity(0);
            this.getChildren().add(subDiagonalWalls.get(i));
        }
        this.holes.add(new Circle(60, 67, 20, Color.rgb(0xff, 0xff, 0xff, 0)));
        this.holes.add(new Circle(60, 655, 20, Color.rgb(0xff, 0xff, 0xff, 0)));
        this.holes.add(new Circle(633, 667, 20, Color.rgb(0xff, 0xff, 0xff, 0)));
        this.holes.add(new Circle(1211, 655, 20, Color.rgb(0xff, 0xff, 0xff, 0)));
        this.holes.add(new Circle(1211, 67, 20, Color.rgb(0xff, 0xff, 0xff, 0)));
        this.holes.add(new Circle(633, 52, 20, Color.rgb(0xff, 0xff, 0xff, 0)));
        for (int i = 0; i < 6; i++)
            this.getChildren().add(holes.get(i));
        Slider slider = new Slider(20, 100, 10);
        slider.setOrientation(Orientation.VERTICAL);
        slider.setLayoutX(23);
        slider.setLayoutY(395);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setValueChanging(true);
        slider.setMajorTickUnit(20);
        this.slider = slider;
        this.getChildren().add(slider);
        this.textHitsRemain = new Text();
        textHitsRemain.setX(120);
        textHitsRemain.setY(660);
        textHitsRemain.setFont(Font.font("Abyssinica SIL",FontWeight.BOLD,FontPosture.ITALIC,25));
        textHitsRemain.setFill(Color.BLUE);
        textHitsRemain.setStroke(Color.BLACK);
        textHitsRemain.setStrokeWidth(2);
        textHitsRemain.setText(strHits + 40);
        this.getChildren().add(textHitsRemain);
        this.textScore = new Text();
        textScore.setX(750);
        textScore.setY(660);
        textScore.setFont(Font.font("Abyssinica SIL",FontWeight.BOLD,FontPosture.ITALIC,25));
        textScore.setFill(Color.BLUE);
        textScore.setStroke(Color.BLACK);
        textScore.setStrokeWidth(2);
        textScore.setText(strScore + 0);
        this.getChildren().add(textScore);
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!Table.this.isPowerChoose && Table.this.isDirChoose) {
                    isPowerChoose = true;
                    slider.setDisable(true);
                    slider.setVisible(false);
                    Wood wood = Wood.getInstance();
                    new WoodAnimation(wood).play();
                }
                else if (!Table.this.isDirChoose) {
                    isDirChoose = true;
                    slider.setVisible(true);
                    slider.setDisable(false);
                }

            }
        });
        this.setOnMouseMoved(new EventHandler<MouseEvent>() {
            double y = 0;
            @Override
            public void handle(MouseEvent event) {
                if (!isDirChoose) {
                    Wood wood = Wood.getInstance();
                    if (event.getY() > y)
                        wood.changeRotation(Direction.DOWN, Role.MOUSE);
                    else if (event.getY() <= y)
                        wood.changeRotation(Direction.UP, Role.MOUSE);
                    y = event.getY();
                }
            }
        });
        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!isDirChoose) {
                    Wood wood = Wood.getInstance();
                    switch (event.getCode()) {
                        case DOWN:
                            wood.changeRotation(Direction.DOWN, Role.KEY);
                            break;
                        case UP:
                            wood.changeRotation(Direction.UP, Role.KEY);
                            break;
                        case ENTER:
                            Table.this.isDirChoose = true;
                            slider.setVisible(true);
                            slider.setDisable(false);
                    }
                }
                else if (!isPowerChoose && isDirChoose){
                    switch (event.getCode()) {
                        case DOWN:
                        Table.this.slider.setValue(Table.this.slider.getValue() - 2);
                            break;
                        case UP:
                            Table.this.slider.setValue(Table.this.slider.getValue() + 2);
                            break;
                        case ENTER:
                            isPowerChoose = true;
                            slider.setDisable(true);
                            slider.setVisible(false);
                            Wood wood = Wood.getInstance();
                            new WoodAnimation(wood).play();

                    }
                }
            }
        });
    }
    public static Table getInstance() {
        if (Instance == null)
            Instance = new Table();
        return Instance;
    }
    public static void updateScore() { Table.getInstance().textScore.setText(getInstance().strScore + getInstance().totalScore); }
    public static void updateHits() {
        Table.decrementTotalHits();
        Table.getInstance().textHitsRemain.setText(getInstance().strHits + getInstance().totalHits);
    }
    public void prepareTable() {
        //System.out.println("222222222222222222222222222222222");
        isDirChoose = false;
        isPowerChoose = false;
        Wood wood = Wood.getInstance();
        wood.createRotation();
        System.out.println("-----------------");
        System.out.println(wood.getX());
        System.out.println(wood.getY());
        System.out.println(wood.getRotation().getPivotX());
        System.out.println(wood.getRotation().getPivotY());
        System.out.println(Ball.getWhiteBall().getCenterX());
        System.out.println(Ball.getWhiteBall().getCenterY());
        System.out.println(Ball.getWhiteBall().getLayoutX());
        System.out.println(Ball.getWhiteBall().getLayoutY());
        for (int i = 0; i < Ball.getBalls().size(); i += 2)
            Ball.getBalls().get(i).setTheta(0);
        wood.setDisable(false);
        wood.setVisible(true);

    }
    public boolean getPowerChoose() { return this.isPowerChoose; }
    public static int getTotalHits() { return Instance.totalHits; }
    public static int getTotalScore() { return Instance.totalScore; }
    public static void decrementTotalHits() { Instance.totalHits--; }
    public static void setTotalScore(int score) { Instance.totalScore += score; }
    public ArrayList<Circle> getHoles() { return this.holes; }
    public ArrayList<Line> getSideWalls() { return this.sideWalls; }
    public ArrayList<Line> getDiagonalWalls() { return this.diagonalWalls; }
    public ArrayList<Line> getSubDiagonalWalls() { return this.subDiagonalWalls; }
    public ArrayList<Line> getTopWalls() { return this.topWalls; }
    public Slider getSlider() { return this.slider; }
}


