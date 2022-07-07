package tr.com.argela.chess.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tr.com.argela.chess.exception.GameException;
import tr.com.argela.chess.model.ChessBoard;
import tr.com.argela.chess.service.ChessService;

@RestController
@RequestMapping("/chess")
public class ChessController {

    @Autowired
    ChessService chessService;

    Logger logger = LoggerFactory.getLogger(ChessController.class);

 
    @GetMapping("/start")
    public ResponseEntity<ChessResponse> createNewGame() {
        String sessionID = chessService.createNewGame();
        return new ResponseEntity<>(new ChessResponse(sessionID), HttpStatus.OK);
    }


    @GetMapping("/board/{sessionID}")
    public ResponseEntity<ChessResponse> getBoard(@PathVariable String sessionID) {
        try {
            ChessBoard chessBoard = chessService.getBoard(sessionID);
            return new ResponseEntity<>(new ChessResponse(sessionID, chessBoard), HttpStatus.OK);
        } catch (GameException ex) {
            logger.error("[getBoard][FAIL] sessionID:" + sessionID + ", message:" + ex.getMessage());
            return new ResponseEntity<>(new ChessResponse(sessionID, ex), HttpStatus.UNAUTHORIZED);
        }
    }
}
