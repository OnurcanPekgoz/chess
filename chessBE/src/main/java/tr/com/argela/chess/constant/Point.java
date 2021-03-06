package tr.com.argela.chess.constant;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isInBoard() {
        return x >= 0 && x <= 7 && y >= 0 && y <= 7;
    }
}
