package com.example.demo.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.GameException;
import com.example.demo.model.ChessBoard;
import com.example.demo.repository.ChessRepository;

@Service
public class ChessService {
    @Autowired
    ChessRepository chessRepository;
    @Autowired
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
        return chessRepository.getbySessionID(sessionID);
    }

    public String createSessionID() {
        return UUID.randomUUID().toString();
    }
}
