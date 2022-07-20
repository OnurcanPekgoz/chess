package tr.com.argela.chess.model;

import java.util.ArrayList;
import java.util.List;

import tr.com.argela.chess.constant.Player;
import tr.com.argela.chess.constant.Point;
import tr.com.argela.chess.constant.StoneType;
import tr.com.argela.chess.exception.GameException;

public class Queen extends Piece {

    public Queen(Player player, StoneType stoneType) {
        super(player, stoneType);
    }

    public boolean isValidMove(ChessBoard chessBoard, Player player, Point source, Point destination)
            throws GameException {
        ActionType actionType = resolveAction(source, destination, chessBoard);
        return validateMove(actionType, chessBoard);
    }

    @Override
    public List<Point> getPossibleMoves(Point currentPosition) throws GameException {
        List<Point> possibleMoveList = new ArrayList<Point>();
        int sourceX = currentPosition.getX();
        int sourceY = currentPosition.getY();

        for (int x = -7; x + sourceX >= -7 && x + sourceX <= 7; x++) {
            possibleMoveList.add(new Point(x + sourceX, 0));
        }
        for (int y = -7; y + sourceY >= -7 && y + sourceY <= 7; y++) {
            possibleMoveList.add(new Point(0, y + sourceY));
        }
        for (int x = -7; x + sourceX >= -7 && x + sourceX <= 7; x++) {
            for (int y = -7; y + sourceY >= -7 && y + sourceY <= 7; y++) {
                if (x == y) {
                    possibleMoveList.add(new Point(x + sourceX, y + sourceY));
                }
            }
        }
        return possibleMoveList;
    }

}
