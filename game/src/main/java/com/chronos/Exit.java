package com.chronos;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class Exit extends GameObject {
    public Exit(Rect bounds) {
        super(bounds);
    }

    @Override
    public void render(GraphicsContext g, double time) {
        double glow = 0.35 + 0.25 * Math.sin(time * 3);
        g.setFill(Color.rgb(80, 250, 123, glow));
        g.fillRect(bounds.getX(), bounds.getY(), bounds.getW(), bounds.getH());
        g.setFill(Color.WHITE);
        g.setTextAlign(TextAlignment.CENTER);
        g.fillText("EXIT", bounds.getX() + bounds.getW() / 2, bounds.getY() + bounds.getH() / 2 + 5);
        g.setTextAlign(TextAlignment.LEFT);
    }
}