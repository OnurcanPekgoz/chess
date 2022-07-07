package tr.com.argela.chess.model;

import lombok.*;
import tr.com.argela.chess.constant.MoveAmountType;
import tr.com.argela.chess.constant.MoveType;
import tr.com.argela.chess.constant.Player;
import tr.com.argela.chess.constant.Point;
import tr.com.argela.chess.constant.StoneType;
import tr.com.argela.chess.exception.GameException;
import tr.com.argela.chess.exception.IllegalMoveException;

@Getter
public abstract class Piece {

    Player player;
    StoneType stoneType;

    public Piece(Player player, StoneType stoneType) {
        this.player = player;
        this.stoneType = stoneType;
    }

    public abstract boolean isValidMove(ChessBoard chessBoard, Player player, Point source, Point destination) throws GameException;

    public ActionType resolveAction(Point source, Point dest) throws GameException {

        int xDiff = dest.getX() - source.getX();
        int yDiff = dest.getY() - source.getY();

        if (xDiff == 0) {
            if (yDiff == 0) {
                throw new IllegalMoveException(source, dest, stoneType);
            }
            return resolveYAxisAction(xDiff, yDiff);
        }

        if (yDiff == 0) {
            return resolveXAxisAction(xDiff, yDiff);
        }

        if (xDiff == yDiff) {
            return resolveCrossAction(yDiff);
        }

        if ((Math.abs(xDiff) == 1 && Math.abs(yDiff) == 2) || (Math.abs(xDiff) == 2 && Math.abs(yDiff) == 1))
            return resolveLTypeAction(xDiff, yDiff);

        return null;
    }

    // @Todo: Onurcan doldur
    private ActionType resolveXAxisAction(int xDiff, int yDiff) {
        switch (xDiff) {
            case 1: 
                return new ActionType(MoveType.RIGHT, MoveAmountType.ONE);
            case -1:
                return new ActionType(MoveType.LEFT, MoveAmountType.ONE);
            default:
                return new ActionType(xDiff > 0 ? MoveType.RIGHT : MoveType.LEFT, MoveAmountType.MULTI);
        }
    }

    // @Todo: Onurcan doldur
    private ActionType resolveLTypeAction(int xDiff, int yDiff) {
        return new ActionType(MoveType.L_TYPE, MoveAmountType.ONE); 
    }

    private ActionType resolveCrossAction(int yDiff) {
        MoveType moveType=MoveType.ALL_CROSS;
        MoveAmountType  moveAmountType =MoveAmountType.MULTI;

        if (yDiff * getPlayer().getDirection() == 1) {
            switch (stoneType) {
                case Pawn:
                    moveType = MoveType.FORWARD_CROSS;
                    break;
                default:
                    moveType = MoveType.ALL_CROSS;
            }
            moveAmountType=MoveAmountType.ONE;
        }
        return new ActionType(moveType, moveAmountType);
    }

    private ActionType resolveYAxisAction(int xDiff, int yDiff) {
        switch (yDiff * getPlayer().getDirection()) {
            case 1: // Forward Single Move
                return new ActionType(MoveType.UP, MoveAmountType.ONE);
            case -1:
                return new ActionType(MoveType.DOWN, MoveAmountType.ONE);
            default:
                return new ActionType(xDiff > 0 ? MoveType.UP : MoveType.DOWN, MoveAmountType.MULTI);
        }
    }

    public boolean validateMove(ActionType actionType){
      
        switch(actionType.getMoveAmountType()){
            case MULTI:{
                if(stoneType.getMoveAmountType() == MoveAmountType.ONE){
                    return false;
                }
                break;
            }     
        }
        return getStoneType().isValidMove(actionType.getMoveType());
    } 

}

