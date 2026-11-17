package com.chronos;

import javafx.scene.canvas.GraphicsContext;

public abstract class GameObject {
    protected final Rect bounds;

    public GameObject(Rect bounds) {
        this.bounds = bounds;
    }

    public Rect getBounds() { return bounds; }

    public boolean blocks() { return false; }

    public abstract void render(GraphicsContext g, double time);
}