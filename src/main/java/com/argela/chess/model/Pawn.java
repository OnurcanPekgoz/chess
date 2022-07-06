package com.argela.chess.model;
import com.argela.chess.constant.Point;
import com.argela.chess.constant.Player;

public class Pawn extends Piece {

    public Pawn(Player player) {
        super(player);
    }

    public boolean isMoveValid(ChessBoard chessBoard, Player player, Point source, Point destination) {

        return true;
    }

    public boolean validatePawnForward(ChessBoard chessBoard, Player player, Point source, Point destination) { // one step forward
                                                                                                        
        if (chessBoard.board[destination.getX()][destination.getY()] == null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validatePawnTwoForward(ChessBoard chessBoard, Player player, Point source, Point destination) {// two step forward                                                                                                  
        switch (player) {

            case White:
                if (source.getX() != 6) {
                    return false;
                } else if (chessBoard.board[destination.getX()][destination.getY()] == null) {
                    return true;
                } else {
                    return false;
                }

            case Black:
                if (source.getX() != 1) {
                    return false;
                } else if (chessBoard.board[destination.getX()][destination.getY()] == null) {
                    return true;
                } else {
                    return false;
                }

        }
        return false;
    }
    
    public boolean validatePawnCross(ChessBoard chessBoard, Player player, Point source, Point destination) {// cross
        if (chessBoard.board[destination.getX()][destination.getY()].getPlayer() == chessBoard.board[source.getX()][source.getY()].getPlayer()){// same color
            return false;
        }
        else{
            return true;
        }
    }
}
