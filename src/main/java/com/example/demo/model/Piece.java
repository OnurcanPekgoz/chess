package com.example.demo.model;

public class Piece {
    private String color;
    public Piece(String color){
        this.color=color;
    }
    public String getPieceType(){
        return this.getClass().getSimpleName();
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    
}
