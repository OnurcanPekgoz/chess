package com.argela.chess.model;
import com.argela.chess.constant.Point;
import com.argela.chess.constant.Player;

public abstract class Piece {
    private Player player;
    public Piece(Player player){
        this.player=player;
    }

    public String getPieceType() {
        return this.getClass().getSimpleName();
    }

    public Player getPlayer() {
        return player;
    }
    

    public abstract boolean isMoveValid(ChessBoard chessBoard,Player player,Point source,Point destination);


}
