package com.letsela901015004.shapesliderapp;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ShapeSliderApp extends Application {

    private int currentShapeIndex = 0;//initializing start of shapes
    private List<Shape> shapes;//array initialization for shapes
    private Pane canvas;//declaring canvas

    //declaring buttons
    private Button prevBtn;
    private Button nextBtn;
    private Button changeColorBtn;

    // Variables to store mouse click offset for smooth dragging
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) {

        BorderPane root = new BorderPane();
        canvas = new Pane();
        canvas.getStyleClass().add("pane");
        canvas.setPrefSize(650, 650);
        canvas.setId("canvas");

        // Adding header label with class
        Label headerLabel = new Label("<<..Shape Slider App..>>");
        headerLabel.getStyleClass().add("heading");
        // Centering the header label within the BorderPane
        BorderPane.setAlignment(headerLabel, Pos.CENTER);
        root.setTop(headerLabel);

        // Creating RECTANGLE shape
        Rectangle rectangle = new Rectangle(300, 200, Color.YELLOW);
        rectangle.getStyleClass().add("shape");
        rectangle.setLayoutX(150);
        rectangle.setLayoutY(200);
        addDragHandlers(rectangle);

        // Creating CIRCLE shape
        Circle circle = new Circle(150, Color.BLACK);
        circle.getStyleClass().add("shape");
        circle.setLayoutX(250);
        circle.setLayoutY(200);
        addDragHandlers(circle);

        // Creating TRIANGLE shape
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(0.0, 200.0, 100.0, 0.0, 200.0, 200.0);
        triangle.setFill(Color.GRAY);
        triangle.getStyleClass().add("shape");
        triangle.setLayoutX(150);
        triangle.setLayoutY(200);
        addDragHandlers(triangle);


        //CREATING ARRAY TO HANDLE LIST OF SHAPES
        shapes = Arrays.asList(triangle, rectangle, circle);

        //CANVAS FUNCTION TO GET ALL SHAPES
        canvas.getChildren().addAll(triangle, rectangle, circle);

        // Buttons for navigating shapes
        prevBtn = new Button("Previous");
        nextBtn = new Button("Next");
        changeColorBtn = new Button("Change Background");

        //CREATING GET FUNCTION TO GET BUTTONS
        prevBtn.getStyleClass().add("button");
        nextBtn.getStyleClass().add("button");
        changeColorBtn.getStyleClass().add("button");

        //CREATING PREVIOUS FUNCTION WITH IF STATEMENT TO ALLOW IT PERFOM REQUIRED FUNCTIONALITY
        prevBtn.setOnAction(e -> {
            currentShapeIndex--;
            if (currentShapeIndex < 0) {
                currentShapeIndex = 0;
            }
            updateShape();
        });

        //CREATING NEXT FUNCTION WITH IF STATEMENT TO ALLOW IT PERFOM REQUIRED FUNCTIONALITY
        nextBtn.setOnAction(e -> {
            currentShapeIndex = (currentShapeIndex + 1) % shapes.size();
            updateShape();
        });


        //CREATING CHANGE COLOR BUTTON FUNCTION WITH IF STATEMENT TO ALLOW IT CHANGE COLORS
        changeColorBtn.setOnAction(e -> changeColor());

        //EMBEDDING BUTTON WITHIN HBOX
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(prevBtn, changeColorBtn, nextBtn);
        buttonBox.getStyleClass().add("button-box");

        root.setCenter(canvas);
        root.setBottom(buttonBox);

        //CREATING SCENE
        Scene scene = new Scene(root, 650, 650);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm()); // Load CSS file
        primaryStage.setScene(scene);
        primaryStage.setTitle("..APP..");
        primaryStage.show();


        // Show only the initial shape
        updateShape();
    }

    //Creating method to handle function for changing background color
    private void changeColor() {
        // Generation of  random color value for background
        Random random = new Random();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);

        String rgbaColor = String.format("-fx-background-color: rgba(%d,%d,%d);", red, green, blue);
        canvas.setStyle(rgbaColor);
    }

    //CREATING METHOD TO HANDLE FUNCTION FOR  DRAGGING OF SHAPES
    private void addDragHandlers(Shape shape) {
        shape.setOnMousePressed(event -> {
            xOffset = event.getSceneX() - shape.getLayoutX();
            yOffset = event.getSceneY() - shape.getLayoutY();
        });

        shape.setOnMouseDragged(event -> {
            double x = event.getSceneX() - xOffset;
            double y = event.getSceneY() - yOffset;

            // Ensuring that the shape stays within the canvas bounds
            if (x >= 0 && x <= canvas.getWidth() - shape.getBoundsInLocal().getWidth()) {
                shape.setLayoutX(x);
            }
            if (y >= 0 && y <= canvas.getHeight() - shape.getBoundsInLocal().getHeight()) {
                shape.setLayoutY(y);
            }
        });
    }



    private void updateShape() {
        for (int i = 0; i < shapes.size(); i++) {
            shapes.get(i).setVisible(i == currentShapeIndex);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
