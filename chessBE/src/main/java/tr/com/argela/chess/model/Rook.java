package tr.com.argela.chess.model;

import java.util.ArrayList;
import java.util.List;

import tr.com.argela.chess.constant.Player;
import tr.com.argela.chess.constant.Point;
import tr.com.argela.chess.constant.StoneType;
import tr.com.argela.chess.exception.GameException;

public class Rook extends Piece {

    public Rook(Player player, StoneType stoneType) {
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

        for (int x = -7; x + sourceX >= 0 && x + sourceX <= 7; x++) {
            possibleMoveList.add(new Point(x + sourceX, sourceY));
        }
        for (int y = -7; y + sourceY >= 0 && y + sourceY <= 7; y++) {
            possibleMoveList.add(new Point(sourceX, y + sourceY));
        }
        return possibleMoveList;

    }

}
