package tr.com.argela.chess.exception;

import lombok.Getter;
import tr.com.argela.chess.constant.Point;
import tr.com.argela.chess.constant.StoneType;

@Getter
public class IllegalMoveException extends GameException {
    Point source;
    Point dest;
    StoneType stoneType;

    public IllegalMoveException(Point source, Point dest, StoneType stoneType) {
        super("Illegal Move from " + source + " to " + dest + " for stone " + stoneType);
        this.source = source;
        this.dest = dest;
        this.stoneType = stoneType;
    }
}
