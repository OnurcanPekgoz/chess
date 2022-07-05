package com.argela.chess.constant;

public enum Player {
    ONE, TWO;

    public Player getOtherPlayer() {
        if (this == ONE) {
            return TWO;
        }
        return ONE;
    }
}
