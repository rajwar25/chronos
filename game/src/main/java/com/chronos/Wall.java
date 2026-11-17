package com.chronos;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Wall extends GameObject {
    public Wall(Rect bounds) {
        super(bounds);
    }

    @Override
    public boolean blocks() { return true; }

    @Override
    public void render(GraphicsContext g, double time) {
        g.setFill(Color.web("#44475a"));
        g.fillRect(bounds.getX(), bounds.getY(), bounds.getW(), bounds.getH());
    }
}