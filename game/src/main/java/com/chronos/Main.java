package com.chronos;

import java.util.HashSet;
import java.util.Set;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
    private final Set<KeyCode> keys = new HashSet<>();
    private final Game game = new Game();
    private long last = 0;

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(Level.WIDTH, Level.HEIGHT);
        GraphicsContext g = canvas.getGraphicsContext2D();
        Scene scene = new Scene(new StackPane(canvas));

        scene.setOnKeyPressed(e -> {
            keys.add(e.getCode());
            game.onKeyPressed(e.getCode());
        });
        scene.setOnKeyReleased(e -> keys.remove(e.getCode()));

        AnimationTimer loop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (last == 0) {
                    last = now;
                    return;
                }
                double dt = (now - last) / 1e9;
                last = now;
                game.update(dt, keys);
                game.render(g);
            }
        };
        loop.start();

        stage.setTitle("Chronos");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}