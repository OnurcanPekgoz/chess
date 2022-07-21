package tr.com.argela.chess.exception;

import lombok.Getter;
import tr.com.argela.chess.constant.Point;
import tr.com.argela.chess.constant.StoneType;

@Getter
public class DirectionBlockedException extends GameException {
    Point source;
    Point dest;
    Point blocked;
    StoneType stoneType;

    public DirectionBlockedException(Point source, Point dest, Point blocked, StoneType stoneType) {
        super("Direction blocked while moving from " + source + " to " + dest + " at " + blocked + " for stone "
                + stoneType);
        this.source = source;
        this.dest = dest;
        this.blocked = blocked;
        this.stoneType = stoneType;
    }
}
