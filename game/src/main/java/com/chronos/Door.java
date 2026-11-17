package com.chronos;

import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Door extends GameObject {
    private final List<Plate> plates;
    private boolean open = false;

    public Door(Rect bounds, Plate... plates) {
        super(bounds);
        this.plates = List.of(plates);
    }

    public void updateState() {
        open = true;
        for (Plate p : plates) {
            if (!p.isPressed()) {
                open = false;
            }
        }
    }

    public boolean isOpen() { return open; }

    @Override
    public boolean blocks() { return !open; }

    @Override
    public void render(GraphicsContext g, double time) {
        if (!open) {
            g.setFill(Color.web("#ff5555"));
            g.fillRect(bounds.getX(), bounds.getY(), bounds.getW(), bounds.getH());
        } else {
            g.setStroke(Color.web("#50fa7b"));
            g.setLineWidth(2);
            g.strokeRect(bounds.getX(), bounds.getY(), bounds.getW(), bounds.getH());
        }
    }
}