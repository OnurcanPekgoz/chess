package com.argela.chess.constant;

public enum Player {
    White, Black;

    public Player getOtherPlayer() {
        if (this == White) {
            return Black;
        }
        return White;
    }
}
