package com.argela.chess.repository.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import com.argela.chess.exception.GameSessionNotFoundException;
import com.argela.chess.model.ChessBoard;
import com.argela.chess.repository.ChessRepository;

public class MemoryChessRepository implements ChessRepository {
    Map<String, ChessBoard> gameSessions = new LinkedHashMap<>();

    
    public void save(String sessionId, ChessBoard chessBoard) {
        gameSessions.put(sessionId, chessBoard);
    }

    
    public ChessBoard getBySessionID (String sessionID) throws GameSessionNotFoundException {
        ChessBoard chessBoard = gameSessions.get(sessionID);
        if (chessBoard == null) {
            throw new GameSessionNotFoundException(sessionID);
        }
        return chessBoard;
    }

    
    public void deleteByID(String sessionID) throws GameSessionNotFoundException {
        ChessBoard chessBoard = gameSessions.remove(sessionID);
        if (chessBoard == null) {
            throw new GameSessionNotFoundException(sessionID);
        }
    }
}
