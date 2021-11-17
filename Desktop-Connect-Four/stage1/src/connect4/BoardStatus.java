package connect4;

public enum BoardStatus {
    GAME_IS_NOT_STARTED,
    GAME_IN_PROGRESS,
    X_WINS,
    O_WINS,
    DRAW;

    @Override
    public String toString() {
        return this.name().charAt(0)
                + this.name().substring(1)
                .toLowerCase().replaceAll("_", " ");
    }
}

