package tr.com.argela.chess.model;

import lombok.Getter;
import lombok.Setter;
import tr.com.argela.chess.constant.MoveAmountType;
import tr.com.argela.chess.constant.MoveType;
import tr.com.argela.chess.constant.Point;

@Getter
@Setter
public class ActionType {

    MoveType moveType;
    MoveAmountType moveAmountType;
    Point source;
    Point dest;
    int xDiff;
    int yDiff;

    public ActionType(MoveType moveType, MoveAmountType moveAmountType) {
        this.moveType = moveType;
        this.moveAmountType = moveAmountType;
    }
}
