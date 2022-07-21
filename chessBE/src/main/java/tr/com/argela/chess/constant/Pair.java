package tr.com.argela.chess.constant;

import lombok.Getter;
import lombok.Setter;
import tr.com.argela.chess.model.Piece;

@Getter
@Setter
public class Pair {
    Piece piece;
    Point point;
    public Pair(Piece piece,Point point){
        this.piece=piece;
        this.point=point;
    }
}
