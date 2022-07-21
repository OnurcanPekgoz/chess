package tr.com.argela.chess.constant;

import lombok.Getter;

@Getter
public enum Player {
    White(0,1),
     Black(7,-1);

    int beginPos;
    int direction;

    Player(int beginPos, int direction) {
        this.beginPos = beginPos;
        this.direction = direction;
    }

    public Player getOtherPlayer() {
        if (this == White) {
            return Black;
        }
        return White;
    }

}
