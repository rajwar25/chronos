package com.chronos;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class Game {
    private enum State { PLAYING, LEVEL_COMPLETE, GAME_COMPLETE }

    private int levelIndex = 0;
    private Level level;
    private Player player;
    private final List<Ghost> ghosts = new ArrayList<>();
    private List<Frame> currentRun = new ArrayList<>();
    private double timeLeft;
    private int runNumber;
    private State state;

    public Game() {
        restartLevel();
    }

    private void restartLevel() {
        level = Levels.create(levelIndex);
        player = new Player(level.getStartX(), level.getStartY());
        ghosts.clear();
        currentRun = new ArrayList<>();
        timeLeft = level.getRunTime();
        runNumber = 1;
        state = State.PLAYING;
    }

    private void jumpTo(int index) {
        if (index < Levels.count()) {
            levelIndex = index;
            restartLevel();
        }
    }

    private void startNextRun() {
        ghosts.add(new Ghost(currentRun));
        currentRun = new ArrayList<>();
        for (Ghost gh : ghosts) gh.reset();
        player.setPosition(level.getStartX(), level.getStartY());
        timeLeft = level.getRunTime();
        runNumber++;
    }

    public void onKeyPressed(KeyCode code) {
        switch (code) {
            case R -> restartLevel();
            case DIGIT1 -> jumpTo(0);
            case DIGIT2 -> jumpTo(1);
            case DIGIT3 -> jumpTo(2);
            case DIGIT4 -> jumpTo(3);
            case ENTER -> {
                if (state == State.LEVEL_COMPLETE) {
                    levelIndex++;
                    restartLevel();
                } else if (state == State.GAME_COMPLETE) {
                    levelIndex = 0;
                    restartLevel();
                }
            }
            default -> { }
        }
    }

    public void update(double dt, Set<KeyCode> keys) {
        if (state != State.PLAYING) return;
        timeLeft -= dt;

        double step = player.getSpeed() * dt;
        double dx = 0, dy = 0;
        if (keys.contains(KeyCode.A) || keys.contains(KeyCode.LEFT))  dx -= step;
        if (keys.contains(KeyCode.D) || keys.contains(KeyCode.RIGHT)) dx += step;
        if (keys.contains(KeyCode.W) || keys.contains(KeyCode.UP))    dy -= step;
        if (keys.contains(KeyCode.S) || keys.contains(KeyCode.DOWN))  dy += step;
        player.move(dx, dy, level);

        double elapsed = level.getRunTime() - timeLeft;
        currentRun.add(new Frame(elapsed, player.getX(), player.getY()));
        for (Ghost gh : ghosts) gh.update(elapsed);

        List<Rect> occupants = new ArrayList<>();
        occupants.add(player.bounds());
        for (Ghost gh : ghosts) occupants.add(gh.bounds());
        level.updateObjects(occupants);

        if (level.isExitReached(player.bounds())) {
            state = (levelIndex == Levels.count() - 1) ? State.GAME_COMPLETE : State.LEVEL_COMPLETE;
            return;
        }

        if (timeLeft <= 0) {
            startNextRun();
        }
    }

    public void render(GraphicsContext g) {
        double t = System.nanoTime() / 1e9;
        g.setFont(Font.font("Arial", 14));
        g.setTextAlign(TextAlignment.LEFT);

        g.setFill(Color.web("#1e1e2e"));
        g.fillRect(0, 0, Level.WIDTH, Level.HEIGHT);
        g.setStroke(Color.web("#2a2a3d"));
        g.setLineWidth(1);
        for (double x = 0; x <= Level.WIDTH; x += 40) g.strokeLine(x, 0, x, Level.HEIGHT);
        for (double y = 0; y <= Level.HEIGHT; y += 40) g.strokeLine(0, y, Level.WIDTH, y);

        level.render(g, t);
        for (Ghost gh : ghosts) gh.render(g);
        player.render(g);

        drawHud(g);
        if (state != State.PLAYING) drawOverlay(g);
    }

    private void drawHud(GraphicsContext g) {
        double frac = Math.max(0, timeLeft / level.getRunTime());
        g.setFill(Color.web("#2a2a3d"));
        g.fillRect(0, 0, Level.WIDTH, 8);
        g.setFill(frac > 0.25 ? Color.CORNFLOWERBLUE : Color.web("#ff5555"));
        g.fillRect(0, 0, Level.WIDTH * frac, 8);

        g.setFill(Color.WHITE);
        g.fillText(level.getName() + "    Run " + runNumber + "    Ghosts " + ghosts.size()
                + "    Time " + String.format("%.1f", Math.max(0, timeLeft)), 20, 30);
        g.setFill(Color.web("#8a8aa0"));
        g.fillText(level.getHint(), 20, Level.HEIGHT - 32);
        g.fillText("R: restart level    1-4: jump to level", 20, Level.HEIGHT - 12);
    }

    private void drawOverlay(GraphicsContext g) {
        boolean last = state == State.GAME_COMPLETE;
        g.setFill(Color.rgb(0, 0, 0, 0.6));
        g.fillRect(0, 0, Level.WIDTH, Level.HEIGHT);
        g.setTextAlign(TextAlignment.CENTER);
        g.setFill(Color.web("#50fa7b"));
        g.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        g.fillText(last ? "ALL LOOPS SOLVED" : "LEVEL COMPLETE", Level.WIDTH / 2, Level.HEIGHT / 2);
        g.setFill(Color.WHITE);
        g.setFont(Font.font("Arial", 16));
        g.fillText("Solved in run " + runNumber + ". Press ENTER "
                + (last ? "to play again." : "for the next level."), Level.WIDTH / 2, Level.HEIGHT / 2 + 35);
        g.setTextAlign(TextAlignment.LEFT);
    }
}