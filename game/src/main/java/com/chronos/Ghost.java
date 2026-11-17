package com.chronos;

import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

public class Ghost {
    private final List<Frame> frames;
    private int index = 0;
    private double x, y;
    private final DropShadow glow = new DropShadow(20, Color.CYAN);

    public Ghost(List<Frame> frames) {
        this.frames = frames;
        reset();
    }

    public void reset() {
        index = 0;
        x = frames.get(0).x();
        y = frames.get(0).y();
    }

    public void update(double elapsed) {
        while (index + 1 < frames.size() && frames.get(index + 1).t() <= elapsed) {
            index++;
        }
        x = frames.get(index).x();
        y = frames.get(index).y();
    }

    public Rect bounds() {
        return new Rect(x, y, Player.SIZE, Player.SIZE);
    }

    public void render(GraphicsContext g) {
        g.setEffect(glow);
        g.setFill(Color.rgb(139, 233, 253, 0.55));
        g.fillRect(x, y, Player.SIZE, Player.SIZE);
        g.setEffect(null);
    }
}