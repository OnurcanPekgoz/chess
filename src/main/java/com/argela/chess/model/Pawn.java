package com.argela.chess.model;

import com.argela.chess.constant.Player;

public class Pawn extends Piece {

    public Pawn(Color color) {
        super(color);
        // TODO Auto-generated constructor stub
    }

    public boolean isMoveValid(int sourcex, int sourcey, int destinationx, int destinationy) {
        if (chessBoard.currentPlayer == Player.ONE && color == Color.Black) { // white turn black piece
            return false;
        }
        if (chessBoard.currentPlayer == Player.TWO && color == Color.White) { // black turn white piece
            return false;
        }
        if (sourcex < 0 || sourcex > 7 || sourcey < 0 || sourcey > 7 || destinationx < 0 || destinationx > 7
                || destinationy < 0 || destinationy > 7) {
            return false;// out of board
        }
        if (chessBoard.currentPlayer == Player.ONE) { // white turn
            if (sourcex == destinationx+1 && sourcey == destinationy) { // white pawn forward
                if (chessBoard.board[destinationx][destinationy] == null) {
                    return true;// free space can move
                }
                else{
                    return false;// dest not free
                }
            }
            if(sourcex == destinationx+1 && sourcey== destinationy-1 && sourcey == destinationy + 1){// white pawn cross
                if(chessBoard.board[destinationx][destinationy].color==Color.Black){ //dest black
                    return true;
                }
                else{
                    return false; //dest not black
                }
            }
            if(sourcex == destinationx+2 && sourcey == destinationy){// white pawn two forward
                if(sourcex!=6){
                    return false; // pawn not in start pos
                }
                else if (chessBoard.board[destinationx][destinationy] == null) {
                    return true;// free space can move
                }
                else{
                    return false;// dest not free
            }
            }
        }

        if (chessBoard.currentPlayer == Player.TWO) { // black turn
            if (sourcex == destinationx-1 && sourcey == destinationy) { // black pawn forward
                if (chessBoard.board[destinationx][destinationy] == null) {
                    return true;// free space can move
                }
                else{
                    return false;// dest not free
                }
            }
            if(sourcex == destinationx-1 && sourcey== destinationy-1 && sourcey == destinationy + 1){// black pawn cross
                if(chessBoard.board[destinationx][destinationy].color==Color.White){ //dest black
                    return true;
                }
                else{
                    return false; //dest not black
                }
            }
            if(sourcex == destinationx-2 && sourcey == destinationy){// black pawn two forward
                if(sourcex!=1){
                    return false; // pawn not in start pos
                }
                else if (chessBoard.board[destinationx][destinationy] == null) {
                    return true;// free space can move
                }
                else{
                    return false;// dest not free
            }
            }
        }
        return true;
    }
}
