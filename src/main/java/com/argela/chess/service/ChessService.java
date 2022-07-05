package com.argela.chess.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.argela.chess.exception.GameException;
import com.argela.chess.model.ChessBoard;
import com.argela.chess.repository.ChessRepository;

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
}
