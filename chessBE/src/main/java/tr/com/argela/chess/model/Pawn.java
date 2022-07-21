package tr.com.argela.chess.model;

import java.util.ArrayList;
import java.util.List;

import tr.com.argela.chess.constant.Player;
import tr.com.argela.chess.constant.Point;
import tr.com.argela.chess.constant.StoneType;
import tr.com.argela.chess.exception.GameException;

public class Pawn extends Piece {
    public boolean hasMoved;

    public Pawn(Player player, StoneType stoneType) {
        super(player, stoneType);
    }

    public boolean isValidMove(ChessBoard chessBoard, Player player, Point source, Point destination)
            throws GameException {
        ActionType actionType = resolveAction(source, destination, chessBoard);
        return validateMove(actionType, chessBoard);
    }

    @Override
    public List<Point> getPossibleMoves(Point currentPosition) throws GameException {
        // TODO Auto-generated method stub
        List<Point> possibleMoveList = new ArrayList<Point>();
        int sourceX = currentPosition.getX();
        int sourceY = currentPosition.getY();

        if (sourceX - 1 >= 0 && sourceX + 1 <= 7 && sourceY - 1 >= 0 && sourceY + 1 <= 7) {
            possibleMoveList.add(new Point(1 + sourceX, 0 + sourceY));
            possibleMoveList.add(new Point(1 + sourceX, 1 + sourceY));
            possibleMoveList.add(new Point(-1 + sourceX, 1 + sourceY));
        }
        return possibleMoveList;
    }
}
