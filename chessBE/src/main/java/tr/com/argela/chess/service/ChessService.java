package tr.com.argela.chess.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.com.argela.chess.constant.GameState;
import tr.com.argela.chess.constant.Point;
import tr.com.argela.chess.constant.StoneType;
import tr.com.argela.chess.exception.CheckHappeningException;
import tr.com.argela.chess.exception.GameException;
import tr.com.argela.chess.exception.IllegalMoveException;
import tr.com.argela.chess.exception.InvalidTurnException;
import tr.com.argela.chess.model.ChessBoard;
import tr.com.argela.chess.model.Piece;
import tr.com.argela.chess.constant.Player;
import tr.com.argela.chess.repository.ChessRepository;

@Service
public class ChessService {

    @Autowired
    ChessRepository chessRepository;

    Logger logger = LoggerFactory.getLogger(ChessService.class);

    public String createNewGame() {
        String sessionID = createSessionID();
        ChessBoard chessBoard = new ChessBoard(sessionID);
        chessRepository.save(sessionID, chessBoard);
        if (logger.isInfoEnabled()) {
            logger.debug("NewGame] sessionId:" + sessionID);
        }
        return sessionID;
    }

    public ChessBoard getBoard(String sessionID) throws GameException {
        return chessRepository.getBySessionID(sessionID);
    }

    public String createSessionID() {
        return UUID.randomUUID().toString();
    }

    public ChessBoard move(String sessionId, Point source, Point dest) throws GameException {
        ChessBoard board = getBoard(sessionId);
        Piece stone = board.getStone(source);

        if (stone.check(board)) {
            System.out.println("Check");
            if (!stone.resolveCheckMate(board)) {
                System.out.println("Check Mate");
                board.setGameState(GameState.COMPLETED);
                board.setWinnerPlayer(board.getCurrentPlayer().getOtherPlayer());
                return board;
            } else {
                if (!stone.resolveCheck(board, source, dest)) {
                    throw new CheckHappeningException();
                }
            }
        }


        if (!stone.isValidMove(board, board.getCurrentPlayer(), source, dest)) {
            throw new IllegalMoveException(source, dest, stone.getStoneType());
        }

        if (board.getCurrentPlayer() != stone.getPlayer()) {
            throw new InvalidTurnException(source, dest, stone.getStoneType());
        }

        board.putStone(stone, dest);
        stone.setHasMoved(true);
        board.putStone(null, source);
        board.swapTurn();
        board.pieceListFixer(board);
        return board;
    }
}
