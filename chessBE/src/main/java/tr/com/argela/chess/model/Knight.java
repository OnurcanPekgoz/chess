package tr.com.argela.chess.model;

import java.util.ArrayList;
import java.util.List;

import tr.com.argela.chess.constant.Player;
import tr.com.argela.chess.constant.Point;
import tr.com.argela.chess.constant.StoneType;
import tr.com.argela.chess.exception.GameException;

public class Knight extends Piece {

    public Knight(Player player, StoneType stoneType) {
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
        for (int x = -2; x <= 2 && x + sourceX >= 0 && x + sourceX <= 7; x++) {
            for (int y = -7; y <= 2 && y + sourceY >= 0 && y + sourceY <= 7; y++) {
                if ((Math.abs(x) + Math.abs(y) == 3)) {
                    possibleMoveList.add(new Point(x + sourceX, y + sourceY));
                }
            }
        }

        return possibleMoveList;
    }
}
