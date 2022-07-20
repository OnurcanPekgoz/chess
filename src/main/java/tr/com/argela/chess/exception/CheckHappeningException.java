package tr.com.argela.chess.exception;

public class CheckHappeningException extends GameException {
    public CheckHappeningException() {
        super("Check is happening.");
    }
}
