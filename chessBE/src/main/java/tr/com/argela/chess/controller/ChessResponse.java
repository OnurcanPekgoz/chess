package tr.com.argela.chess.controller;

import lombok.Getter;
import lombok.Setter;
import tr.com.argela.chess.model.ChessBoard;

@Getter
@Setter
public class ChessResponse {
    String sessionID;
    ChessBoard chessBoard;
    String message;
    Class errorClass;
    boolean failed;

    public ChessResponse(String sessionID) {
        this.sessionID = sessionID;
        failed = false;
    }

    public ChessResponse(String sessionID, ChessBoard chessBoard) {
        this.sessionID = sessionID;
        this.chessBoard = chessBoard;
        failed = false;
    }

    public ChessResponse(String sessionID, Exception exception) {
        this.sessionID = sessionID;
        this.message = exception.getMessage();
        this.errorClass = exception.getClass();
        failed = true;
    }
}
