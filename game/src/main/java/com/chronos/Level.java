package com.chronos;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;

public class Level {
    public static final double WIDTH = 800;
    public static final double HEIGHT = 450;

    private final String name;
    private final String hint;
    private final double startX, startY, runTime;
    private final List<GameObject> objects = new ArrayList<>();
    private final List<Plate> plates = new ArrayList<>();
    private final List<Door> doors = new ArrayList<>();
    private Exit exit;

    public Level(String name, String hint, double startX, double startY, double runTime) {
        this.name = name;
        this.hint = hint;
        this.startX = startX;
        this.startY = startY;
        this.runTime = runTime;
    }

    public void add(GameObject o) {
        objects.add(o);
        if (o instanceof Plate p) plates.add(p);
        if (o instanceof Door d) doors.add(d);
        if (o instanceof Exit e) exit = e;
    }

    public boolean isBlocked(Rect r) {
        if (r.getX() < 0 || r.getY() < 0
                || r.getX() + r.getW() > WIDTH || r.getY() + r.getH() > HEIGHT) {
            return true;
        }
        for (GameObject o : objects) {
            if (o.blocks() && o.getBounds().overlaps(r)) {
                return true;
            }
        }
        return false;
    }

    public void updateObjects(List<Rect> occupants) {
        for (Plate p : plates) p.updatePressed(occupants);
        for (Door d : doors) d.updateState();
    }

    public boolean isExitReached(Rect r) {
        return exit != null && exit.getBounds().overlaps(r);
    }

    public void render(GraphicsContext g, double time) {
        for (GameObject o : objects) {
            o.render(g, time);
        }
    }

    public String getName() { return name; }
    public String getHint() { return hint; }
    public double getStartX() { return startX; }
    public double getStartY() { return startY; }
    public double getRunTime() { return runTime; }
}