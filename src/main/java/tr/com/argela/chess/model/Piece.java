package tr.com.argela.chess.model;

import java.util.Arrays;
import java.util.List;

import lombok.*;
import tr.com.argela.chess.constant.MoveAmountType;
import tr.com.argela.chess.constant.MoveType;
import tr.com.argela.chess.constant.Player;
import tr.com.argela.chess.constant.Point;
import tr.com.argela.chess.constant.StoneType;
import tr.com.argela.chess.exception.DirectionBlockedException;
import tr.com.argela.chess.exception.EatingNotAllowedException;
import tr.com.argela.chess.exception.GameException;
import tr.com.argela.chess.exception.IllegalMoveException;

@Getter
@ToString
public abstract class Piece {

    Player player;
    StoneType stoneType;

    public Piece(Player player, StoneType stoneType) {
        this.player = player;
        this.stoneType = stoneType;
    }

    public abstract boolean isValidMove(ChessBoard chessBoard, Player player, Point source, Point destination)
            throws GameException;

    public ActionType resolveAction(Point source, Point dest) throws GameException {

        int xDiff = dest.getX() - source.getX();
        int yDiff = dest.getY() - source.getY();

        if (xDiff == 0) {
            if (yDiff == 0) {
                throw new IllegalMoveException(source, dest, stoneType);
            }
            return addPositionParameters(source, dest, xDiff, yDiff, resolveYAxisAction(xDiff, yDiff));
        }

        if (yDiff == 0) {
            return addPositionParameters(source, dest, xDiff, yDiff, resolveXAxisAction(xDiff, yDiff));
        }

        if (Math.abs(xDiff) == Math.abs(yDiff)) {
            return addPositionParameters(source, dest, xDiff, yDiff, resolveCrossAction(yDiff));
        }

        if ((Math.abs(xDiff) == 1 && Math.abs(yDiff) == 2) || (Math.abs(xDiff) == 2 && Math.abs(yDiff) == 1))
            return addPositionParameters(source, dest, xDiff, yDiff, resolveLTypeAction(xDiff, yDiff));

        throw new IllegalMoveException(source, dest, stoneType);
    }

    private ActionType addPositionParameters(Point source, Point dest, int xDiff, int yDiff,
            ActionType actionType) {
        actionType.setDest(dest);
        actionType.setSource(source);
        actionType.setXDiff(xDiff);
        actionType.setYDiff(yDiff);
        return actionType;
    }

    // @Todo: Onurcan doldur
    private ActionType resolveXAxisAction(int xDiff, int yDiff) {
        switch (xDiff * getPlayer().getDirection()) {
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
        MoveType moveType = MoveType.ALL_CROSS;
        MoveAmountType moveAmountType = MoveAmountType.MULTI;

        if (yDiff * getPlayer().getDirection() == 1) {
            switch (stoneType) {
                case Pawn:
                    moveType = MoveType.FORWARD_CROSS;
                    break;
                default:
                    moveType = MoveType.ALL_CROSS;
            }
            moveAmountType = MoveAmountType.ONE;
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
                return new ActionType(yDiff > 0 ? MoveType.UP : MoveType.DOWN, MoveAmountType.MULTI);
        }
    }

    public boolean validateMove(ActionType actionType, ChessBoard chessBoard) throws GameException {

        switch (actionType.getMoveAmountType()) {
            case MULTI: {
                if (stoneType.getMoveAmountType() == MoveAmountType.ONE) {
                    return false;
                }
                break;
            }
        }
        return getStoneType().isValidMove(actionType.getMoveType()) && hasStoneOnRoad(actionType, chessBoard);
    }

    private boolean hasStoneOnRoad(ActionType actionType, ChessBoard chessBoard) throws GameException {

        if (stoneType.isValidMove(MoveType.L_TYPE)) {
            return true;
        }
        // @Todo: look later
        if (stoneType.isValidMove(MoveType.FORWARD_CROSS)) {
            return true;
        }

        switch (actionType.getMoveType()) {
            case ALL_CROSS:
                return hasStoneOnCross(actionType, chessBoard);
            case LEFT:
                return hasStoneOnLeft(actionType, chessBoard);
            case RIGHT:
                return hasStoneOnRight(actionType, chessBoard);
            case UP:
                return hasStoneOnUp(actionType, chessBoard);
            case DOWN:
                return hasStoneOnDown(actionType, chessBoard);
            default:
                throw new IllegalMoveException(actionType.getSource(), actionType.getDest(), stoneType);
        }
    }

    private boolean hasStoneOnCross(ActionType actionType, ChessBoard chessBoard) throws GameException {
        int xIndex = actionType.getSource().getX();
        int yIndex = actionType.getSource().getY();
        for (int i = 1; i < Math.abs(actionType.getXDiff()); i++) {

            if (actionType.getXDiff() >= 0) {
                xIndex++;
            } else {
                xIndex--;
            }
            if (actionType.getYDiff() >= 0) {
                yIndex++;
            } else {
                yIndex--;
            }
            if (chessBoard.getBoard()[yIndex][xIndex] != null) {
                throw new DirectionBlockedException(actionType.getSource(), actionType.getDest(),
                        new Point(xIndex, yIndex), stoneType);
            }
        }

        return true;
    }

    private boolean hasStoneOnRight(ActionType actionType, ChessBoard chessBoard) throws GameException {
        int xIndex = actionType.getSource().getX();
        int yIndex = actionType.getSource().getY();
        for (int i = 1; i < Math.abs(actionType.getXDiff()); i++) {
            xIndex++;
            if (chessBoard.getBoard()[yIndex][xIndex] != null) {
                throw new DirectionBlockedException(actionType.getSource(), actionType.getDest(),
                        new Point(xIndex, yIndex), stoneType);

            }
        }
        return true;
    }

    private boolean hasStoneOnLeft(ActionType actionType, ChessBoard chessBoard) throws GameException {
        int xIndex = actionType.getSource().getX();
        int yIndex = actionType.getSource().getY();
        for (int i = 1; i < Math.abs(actionType.getXDiff()); i++) {
            xIndex--;
            if (chessBoard.getBoard()[yIndex][xIndex] != null) {
                throw new DirectionBlockedException(actionType.getSource(), actionType.getDest(),
                        new Point(xIndex, yIndex), stoneType);
            }
        }
        Piece destPiece = chessBoard.getStone(actionType.getDest());
        if (destPiece != null && destPiece.getPlayer() == chessBoard.getCurrentPlayer()) {
            throw new EatingNotAllowedException(actionType.getSource(), actionType.getDest(), actionType.getDest(),
                    stoneType);
        }
        return true;
    }

    private boolean hasStoneOnUp(ActionType actionType, ChessBoard chessBoard) throws GameException {
        int xIndex = actionType.getSource().getX();
        int yIndex = actionType.getSource().getY();
        for (int i = 1; i < Math.abs(actionType.getYDiff()); i++) {
            yIndex++;
            if (chessBoard.getBoard()[yIndex][xIndex] != null) {
                throw new DirectionBlockedException(actionType.getSource(), actionType.getDest(),
                        new Point(xIndex, yIndex), stoneType);
            }
        }
        return true;
    }

    private boolean hasStoneOnDown(ActionType actionType, ChessBoard chessBoard) throws GameException {
        int xIndex = actionType.getSource().getX();
        int yIndex = actionType.getSource().getY();
        for (int i = 1; i < Math.abs(actionType.getYDiff()); i++) {
            yIndex--;
            if (chessBoard.getBoard()[yIndex][xIndex] != null) {
                throw new DirectionBlockedException(actionType.getSource(), actionType.getDest(),
                        new Point(xIndex, yIndex), stoneType);
            }
        }
        return true;
    }

}
