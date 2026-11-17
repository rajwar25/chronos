package com.chronos;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

public class Player {
    public static final double SIZE = 40;

    private double x, y;
    private final double speed = 200;
    private final DropShadow glow = new DropShadow(15, Color.CORNFLOWERBLUE);

    public Player(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getSpeed() { return speed; }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Rect bounds() {
        return new Rect(x, y, SIZE, SIZE);
    }

    public void move(double dx, double dy, Level level) {
        x += dx;
        if (level.isBlocked(bounds())) x -= dx;
        y += dy;
        if (level.isBlocked(bounds())) y -= dy;
    }

    public void render(GraphicsContext g) {
        g.setEffect(glow);
        g.setFill(Color.CORNFLOWERBLUE);
        g.fillRect(x, y, SIZE, SIZE);
        g.setEffect(null);
        g.setStroke(Color.WHITE);
        g.setLineWidth(2);
        g.strokeRect(x, y, SIZE, SIZE);
    }
}