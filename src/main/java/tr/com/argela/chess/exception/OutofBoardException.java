package tr.com.argela.chess.exception;

import lombok.Getter;
import tr.com.argela.chess.constant.Point;

@Getter
public class OutofBoardException extends GameException {

    Point dest;

    public OutofBoardException(Point dest) {
        super(dest + " is out of board ");
        this.dest = dest;
    }
}
