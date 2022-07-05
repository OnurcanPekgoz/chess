package com.argela.chess.model;

public abstract class Piece {
    public enum Color {
        White, Black
    }

    Color color;

    public Piece(Color color) {
        this.color = color;
    }

    public String getPieceType() {
        return this.getClass().getSimpleName();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public abstract boolean isMoveValid(int sourcex,int sourcey, int destinationx,int destinationy);

    public void move(String sessionID, int sourcex,int sourcey, int destinationx,int destinationy){
        ChessBoard chessBoard= new ChessBoard(sessionID);
        chessBoard.board[destinationx][destinationy]=chessBoard.board[sourcex][sourcey];
        chessBoard.board[destinationx][destinationy]=null;
    }
}
