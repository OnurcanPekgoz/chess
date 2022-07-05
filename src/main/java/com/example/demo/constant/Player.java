package com.example.demo.constant;

public enum Player {
    ONE, TWO;

    public Player getOtherPlayer() {
        if (this == ONE) {
            return TWO;
        }
        return ONE;
    }
}
