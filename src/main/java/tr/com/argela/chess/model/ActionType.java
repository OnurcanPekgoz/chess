package tr.com.argela.chess.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tr.com.argela.chess.constant.MoveAmountType;
import tr.com.argela.chess.constant.MoveType;

@Getter
@AllArgsConstructor
public class ActionType {

    MoveType moveType;
    MoveAmountType moveAmountType;

}
