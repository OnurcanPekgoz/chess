package tr.com.argela.chess.exception;

import lombok.Getter;
import tr.com.argela.chess.constant.Point;
import tr.com.argela.chess.constant.StoneType;

@Getter
public class InvalidTurnException extends GameException {
    Point source;
    Point dest;
    StoneType stoneType;

    public InvalidTurnException(Point source, Point dest, StoneType stoneType) {
        super("Illegal Move from " + source + " to " + dest + " for stone " + stoneType + " because its invalid turn");
        this.source = source;
        this.dest = dest;
        this.stoneType = stoneType;
    }
}
