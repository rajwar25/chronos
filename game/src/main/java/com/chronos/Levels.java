package com.chronos;

public class Levels {
    public static int count() {
        return 4;
    }

    public static Level create(int index) {
        return switch (index) {
            case 0 -> level1();
            case 1 -> level2();
            case 2 -> level3();
            case 3 -> level4();
            default -> throw new IllegalArgumentException("No level " + index);
        };
    }

    private static void wallWithGap(Level l, double x, double gapY, double gapH) {
        l.add(new Wall(new Rect(x, 0, 20, gapY)));
        l.add(new Wall(new Rect(x, gapY + gapH, 20, Level.HEIGHT - gapY - gapH)));
    }

    private static Level level1() {
        Level l = new Level("Level 1: The Plate",
                "Stand on the plate. Next run, your ghost holds it open.", 60, 60, 20);
        Plate a = new Plate(new Rect(100, 330, 80, 40));
        l.add(a);
        wallWithGap(l, 500, 180, 90);
        l.add(new Door(new Rect(500, 180, 20, 90), a));
        l.add(new Exit(new Rect(680, 180, 80, 90)));
        return l;
    }

    private static Level level2() {
        Level l = new Level("Level 2: Two Plates",
                "One door needs both plates pressed. You need two ghosts.", 60, 200, 20);
        Plate a = new Plate(new Rect(100, 60, 80, 40));
        Plate b = new Plate(new Rect(100, 350, 80, 40));
        l.add(a);
        l.add(b);
        wallWithGap(l, 500, 180, 90);
        l.add(new Door(new Rect(500, 180, 20, 90), a, b));
        l.add(new Exit(new Rect(680, 180, 80, 90)));
        return l;
    }

    private static Level level3() {
        Level l = new Level("Level 3: The Chain",
                "The second plate is behind the first door.", 60, 60, 25);
        Plate a = new Plate(new Rect(100, 330, 80, 40));
        Plate b = new Plate(new Rect(400, 330, 80, 40));
        l.add(a);
        l.add(b);
        wallWithGap(l, 300, 180, 90);
        l.add(new Door(new Rect(300, 180, 20, 90), a));
        wallWithGap(l, 560, 180, 90);
        l.add(new Door(new Rect(560, 180, 20, 90), b));
        l.add(new Exit(new Rect(680, 180, 80, 90)));
        return l;
    }

    private static Level level4() {
        Level l = new Level("Level 4: The Vault",
                "Two plates for the first door, one more behind it.", 60, 200, 25);
        Plate a = new Plate(new Rect(100, 60, 80, 40));
        Plate b = new Plate(new Rect(100, 350, 80, 40));
        Plate c = new Plate(new Rect(400, 330, 80, 40));
        l.add(a);
        l.add(b);
        l.add(c);
        wallWithGap(l, 300, 180, 90);
        l.add(new Door(new Rect(300, 180, 20, 90), a, b));
        wallWithGap(l, 560, 180, 90);
        l.add(new Door(new Rect(560, 180, 20, 90), c));
        l.add(new Exit(new Rect(680, 180, 80, 90)));
        return l;
    }
}