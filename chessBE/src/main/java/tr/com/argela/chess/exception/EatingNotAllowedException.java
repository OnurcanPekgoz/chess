package tr.com.argela.chess.exception;

import lombok.Getter;
import tr.com.argela.chess.constant.Point;
import tr.com.argela.chess.constant.StoneType;

@Getter
public class EatingNotAllowedException extends GameException {
    Point source;
    Point dest;
    Point blocked;
    StoneType stoneType;

    public EatingNotAllowedException(Point source, Point dest, Point blocked, StoneType stoneType) {
        super("You cannot eat stone at " + blocked + " for stone " + stoneType+" at "+source);
        this.source = source;
        this.dest = dest;
        this.blocked = blocked;
        this.stoneType = stoneType;
    }
}
