package com.chronos;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    double Playerx=300;
    double Playery=100;
    double PlayerSpeed;
    double TimeLeft=30.0;

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(800, 450);
        GraphicsContext g = canvas.getGraphicsContext2D();
        Scene scene=new Scene(new StackPane(canvas));
        g.setFill(Color.web("#1e1e2e"));
        g.fillRect(0, 0, 800, 450);
        g.setFill(Color.CORNFLOWERBLUE);
        g.fillRect(300, 100, 40, 40);
       g.fillRect(0, 0, 40, 40);
       g.fillRect(760, 0, 40, 40);
       g.fillRect(760, 410, 40, 40);

        stage.setTitle("Chronos");
        stage.setScene(scene);
        scene.setOnKeyPressed(event ->{switch(event.getCode()){
            case W:
            case UP:
                Playery-=20;
                break;
            case S:
            case DOWN:
                Playery+=20;
                break;
            case A:
            case LEFT:
                Playerx-=20;
                break;
            case D:
            case RIGHT:
                Playerx+=20;
                break;
            default:
                break;

        }
    });
        AnimationTimer gameLoop = new AnimationTimer() {
    @Override
    public void handle(long now) {
        update();
        render(g);
    }
};
gameLoop.start();
        stage.show();
    }
    private void update() {
   TimeLeft-=0.016;
   if(TimeLeft<=0)
   {
    Playerx=300;
    Playery=100;
    TimeLeft=30.0;
   }
}

private void render(GraphicsContext g) {
    g.setFill(Color.web("#1e1e2e"));
    g.fillRect(0, 0, 800, 450);

    g.setFill(Color.CORNFLOWERBLUE);
    g.fillRect(0, 0, 40, 40);
    g.fillRect(760, 0, 40, 40);
    g.fillRect(760, 410, 40, 40);

    g.fillRect(Playerx, Playery, 40, 40);
    g.setFill(Color.WHITE);
    g.fillText("Time Left "+String.format("%.1f",TimeLeft),20,30);
}

    public static void main(String[] args) {
        launch(args);
    }
}