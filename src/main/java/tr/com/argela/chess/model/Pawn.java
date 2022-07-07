package tr.com.argela.chess.model;

import ch.qos.logback.core.joran.action.Action;
import tr.com.argela.chess.constant.Player;
import tr.com.argela.chess.constant.Point;
import tr.com.argela.chess.constant.StoneType;
import tr.com.argela.chess.exception.GameException;

public class Pawn extends Piece {

    public Pawn(Player player, StoneType stoneType) {
        super(player, stoneType);
    }

    public boolean isValidMove(ChessBoard chessBoard, Player player, Point source, Point destination) throws GameException {
        ActionType actionType = resolveAction(source, destination);
        return validateMove(actionType);
    }
}