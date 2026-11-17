package com.chronos;

import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Plate extends GameObject {
    private boolean pressed = false;

    public Plate(Rect bounds) {
        super(bounds);
    }

    public void updatePressed(List<Rect> occupants) {
        pressed = false;
        for (Rect r : occupants) {
            if (bounds.overlaps(r)) {
                pressed = true;
                break;
            }
        }
    }

    public boolean isPressed() { return pressed; }

    @Override
    public void render(GraphicsContext g, double time) {
        g.setFill(pressed ? Color.web("#50fa7b") : Color.web("#f1fa8c"));
        g.fillRect(bounds.getX(), bounds.getY(), bounds.getW(), bounds.getH());
    }
}