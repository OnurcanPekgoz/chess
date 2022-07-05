package com.argela.chess.model;

public class Piece {
    public enum Color{
        White,Black
    }
    Color color;
    public Piece(Color color){
        this.color=color;
    }
    public String getPieceType(){
        return this.getClass().getSimpleName();
    }
    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    
}
