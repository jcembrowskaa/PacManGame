public enum MapElement {
    street,
    wall,
    point,
    redGhost,
    pinkGhost,
    blueGhost,
    pacman,
    apple,
    blueberry,
    cherry,
    strawberry,
    star;
    public boolean isBoost() {
        return this == apple || this == blueberry || this == cherry || this == strawberry || this == star;
    }

    }
