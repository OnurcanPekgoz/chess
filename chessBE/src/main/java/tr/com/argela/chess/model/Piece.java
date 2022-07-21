package tr.com.argela.chess.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.*;
import tr.com.argela.chess.constant.MoveAmountType;
import tr.com.argela.chess.constant.MoveType;
import tr.com.argela.chess.constant.Pair;
import tr.com.argela.chess.constant.Player;
import tr.com.argela.chess.constant.Point;
import tr.com.argela.chess.constant.StoneType;
import tr.com.argela.chess.exception.DirectionBlockedException;
import tr.com.argela.chess.exception.EatingNotAllowedException;
import tr.com.argela.chess.exception.GameException;
import tr.com.argela.chess.exception.IllegalMoveException;
import tr.com.argela.chess.exception.OutofBoardException;

@Getter
@Setter
@ToString
public abstract class Piece {

    Player player;
    StoneType stoneType;
    boolean hasMoved = false;

    public Piece(Player player, StoneType stoneType) {
        this.player = player;
        this.stoneType = stoneType;
    }

    public abstract boolean isValidMove(ChessBoard chessBoard, Player player, Point source, Point destination)
            throws GameException;

    public abstract List<Point> getPossibleMoves(Point currentPosition) throws GameException;

    public ActionType resolveAction(Point source, Point dest, ChessBoard board) throws GameException {

        int xDiff = dest.getX() - source.getX();
        int yDiff = dest.getY() - source.getY();
        Piece sourcePiece = board.getStone(source);
        if (sourcePiece == null) {
            throw new IllegalMoveException(source, dest, stoneType);
        }
        if (sourcePiece != null && sourcePiece.getStoneType() == StoneType.Pawn) {
            switch (yDiff * getPlayer().getDirection()) {
                case 2:
                    if (hasMoved == false && board.getStone(dest) == null) {
                        return addPositionParameters(source, dest, xDiff, yDiff, resolvePawnTwoStep());
                    }
            }
        }
        if (sourcePiece != null && sourcePiece.getStoneType() == StoneType.Pawn) {
            if (yDiff * getPlayer().getDirection() == 1 && Math.abs(xDiff) == 1) {
                if (board.getStone(dest) != null) {
                    return addPositionParameters(source, dest, xDiff, yDiff, resolvePawnCrossEating());
                }
            }
        }

        if (sourcePiece != null && sourcePiece.getStoneType() == StoneType.Pawn) {
            if (yDiff * getPlayer().getDirection() == 1 && xDiff == 0) {
                if (board.getStone(dest) == null) {
                    return addPositionParameters(source, dest, xDiff, yDiff, resolvePawnOneStep());
                }
            }
        }
        if (sourcePiece != null && sourcePiece.getStoneType() != StoneType.Pawn) {
            if (xDiff == 0) {
                if (yDiff == 0) {
                    throw new IllegalMoveException(source, dest, stoneType);
                }
                return addPositionParameters(source, dest, xDiff, yDiff, resolveYAxisAction(xDiff, yDiff));
            }
        }

        if (yDiff == 0) {
            return addPositionParameters(source, dest, xDiff, yDiff, resolveXAxisAction(xDiff, yDiff));
        }

        if (Math.abs(xDiff) == Math.abs(yDiff)) {
            return addPositionParameters(source, dest, xDiff, yDiff, resolveCrossAction(yDiff));
        }

        if ((Math.abs(xDiff) + Math.abs(yDiff) == 3))
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

    private ActionType resolvePawnTwoStep() {
        return new ActionType(MoveType.UP, MoveAmountType.ONE);
    }

    private ActionType resolvePawnCrossEating() {
        return new ActionType(MoveType.FORWARD_CROSS, MoveAmountType.ONE);
    }

    private ActionType resolvePawnOneStep() {
        return new ActionType(MoveType.UP, MoveAmountType.ONE);
    }

    private ActionType resolveXAxisAction(int xDiff, int yDiff) {
        switch (xDiff) { // * getPlayer().getDirection()
            case 1:
                return new ActionType(MoveType.RIGHT, MoveAmountType.ONE);
            case -1:
                return new ActionType(MoveType.LEFT, MoveAmountType.ONE);
            default:
                return new ActionType(xDiff > 0 ? MoveType.RIGHT : MoveType.LEFT, MoveAmountType.MULTI);
        }
    }

    private ActionType resolveYAxisAction(int xDiff, int yDiff) {
        switch (yDiff * getPlayer().getDirection()) {
            case 1:
                return new ActionType(MoveType.UP, MoveAmountType.ONE);
            case -1:
                return new ActionType(MoveType.DOWN, MoveAmountType.ONE);
            default:
                return new ActionType(yDiff > 0 ? MoveType.UP : MoveType.DOWN, MoveAmountType.MULTI);
        }
    }

    private ActionType resolveLTypeAction(int xDiff, int yDiff) {
        return new ActionType(MoveType.L_TYPE, MoveAmountType.ONE);
    }

    private ActionType resolveCrossAction(int yDiff) {
        MoveType moveType = MoveType.ALL_CROSS;
        MoveAmountType moveAmountType = MoveAmountType.MULTI;

        if (Math.abs(yDiff * getPlayer().getDirection()) == 1) {
            switch (stoneType) {
                case Pawn:
                    if (yDiff * getPlayer().getDirection() == 1) {
                        moveType = MoveType.FORWARD_CROSS;
                    }
                    break;
                default:
                    moveType = MoveType.ALL_CROSS;
            }
            moveAmountType = MoveAmountType.ONE;
        }
        return new ActionType(moveType, moveAmountType);
    }

    public boolean validateMove(ActionType actionType, ChessBoard chessBoard) throws GameException {
        if (!actionType.getDest().isInBoard()) {
            throw new OutofBoardException(actionType.getDest());
        }
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

        if (actionType.getMoveAmountType() == MoveAmountType.ONE) {
            return true;
        }

        if (stoneType.isValidMove(MoveType.L_TYPE)) {
            return true;
        }

        if (stoneType.isValidMove(MoveType.FORWARD_CROSS)) {
            return true;
        }
        Piece destPiece = chessBoard.getStone(actionType.getDest());
        if (destPiece != null && destPiece.getPlayer() == chessBoard.getStone(actionType.getSource()).getPlayer()) {
            throw new EatingNotAllowedException(actionType.getSource(), actionType.getDest(), actionType.getDest(),
                    stoneType);
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
            if (chessBoard.getStone(new Point(xIndex, yIndex)) != null) {
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
            if (chessBoard.getStone(new Point(xIndex, yIndex)) != null) {
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
            if (chessBoard.getStone(new Point(xIndex, yIndex)) != null) {
                throw new DirectionBlockedException(actionType.getSource(), actionType.getDest(),
                        new Point(xIndex, yIndex), stoneType);
            }
        }
        return true;
    }

    private boolean hasStoneOnUp(ActionType actionType, ChessBoard chessBoard) throws GameException {
        int xIndex = actionType.getSource().getX();
        int yIndex = actionType.getSource().getY();
        for (int i = 1; i < Math.abs(actionType.getYDiff()); i++) {
            yIndex++;
            if (chessBoard.getStone(new Point(xIndex, yIndex)) != null) {
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
            if (chessBoard.getStone(new Point(xIndex, yIndex)) != null) {
                throw new DirectionBlockedException(actionType.getSource(), actionType.getDest(),
                        new Point(xIndex, yIndex), stoneType);
            }
        }
        return true;
    }

    public boolean check(ChessBoard board) throws GameException {
        Map<Player, Point> kingPositions = board.getKingPositions();
        Point whiteKingPos = kingPositions.get(Player.White);
        Point blackKingPos = kingPositions.get(Player.Black);
        List<Pair> whitePiecesList = board.getWhitePiecesList();
        List<Pair> blackPiecesList = board.getBlackPiecesList();

        if (board.getCurrentPlayer() == Player.White) {
            for (int i = 0; i < blackPiecesList.size(); i++) {
                try {
                    if (board.getStone(blackPiecesList.get(i).getPoint()).isValidMove(board, board.getCurrentPlayer(),
                            blackPiecesList.get(i).getPoint(), whiteKingPos)) {
                        return true;
                    }
                } catch (GameException e) {

                }
            }
        }

        if (board.getCurrentPlayer() == Player.Black) {
            for (int i = 0; i < whitePiecesList.size(); i++) {
                try {
                    if (board.getStone(whitePiecesList.get(i).getPoint()).isValidMove(board, board.getCurrentPlayer(),
                            whitePiecesList.get(i).getPoint(), blackKingPos)) {
                        return true;
                    }
                } catch (GameException e) {

                }

            }
        }
        return false;
    }

    public boolean resolveCheck(ChessBoard board, Point source, Point dest) throws GameException {
        if (fakeMove(board, source, dest)) {
            return true;
        }
        return false;
    }

    // work in progress
    public boolean resolveCheckMate(ChessBoard board) throws GameException {
        List<Pair> whitePiecesList = board.getWhitePiecesList();
        List<Pair> blackPiecesList = board.getBlackPiecesList();

        boolean resolved = false;

        switch (board.getCurrentPlayer()) {
            case White: {
                for (int i = 0; i < whitePiecesList.size(); i++) {
                    try {
                        Point source = whitePiecesList.get(i).getPoint();
                        List<Point> possibleMoves = getPossibleMoves(source);
                        for (int j = 0; j < possibleMoves.size(); j++) {
                            if (isValidMove(board, board.getCurrentPlayer(), source, possibleMoves.get(j))) {
                                resolved = fakeMove(board, source, possibleMoves.get(j));
                            }
                            if (resolved) {
                                return true;
                            }
                        }
                    } catch (IllegalMoveException e) {

                    }

                }
                break;
            }

            case Black: {
                for (int i = 0; i < blackPiecesList.size(); i++) {
                    try {
                        Point source = blackPiecesList.get(i).getPoint();
                        List<Point> possibleMoves = getPossibleMoves(source);
                        for (int j = 0; j < possibleMoves.size(); j++) {
                            if (isValidMove(board, board.getCurrentPlayer(), source, possibleMoves.get(j))) {
                                resolved = fakeMove(board, source, possibleMoves.get(j));
                            }
                            if (resolved) {
                                return true;
                            }
                        }
                    } catch (IllegalMoveException e) {

                    }

                }
                break;
            }
        }
        return false;
    }

    public boolean fakeMove(ChessBoard board, Point source, Point dest) throws GameException {
        Piece piece = board.getStone(source);
        Piece destPiece = board.getStone(dest);
        board.putStone(piece, dest);
        board.putStone(null, source);
        board.pieceListFixer(board);
        if (piece != null) {
            if (!piece.check(board)) {
                board.putStone(piece, source);
                board.putStone(destPiece, dest);
                return true;
            } else {
                board.putStone(piece, source);
                board.putStone(destPiece, dest);
                return false;
            }
        }
        return false;
    }
}
