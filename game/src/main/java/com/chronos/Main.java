package com.chronos;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(800, 450);
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.web("#1e1e2e"));
        g.fillRect(0, 0, 800, 450);
        g.setFill(Color.CORNFLOWERBLUE);
        g.fillRect(100, 200, 40, 40);

        stage.setTitle("Chronos");
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}