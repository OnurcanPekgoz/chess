package com.argela.chess.model;
import java.awt.Point;
import com.argela.chess.constant.Player;

public class Knight extends Piece {
    
    public Knight(Player player) {
        super(player);
    }

    public boolean isMoveValid(ChessBoard chessBoard, Player player,Point source,Point destination){
        
        return true;
    } 
}
