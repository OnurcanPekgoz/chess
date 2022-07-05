package com.argela.chess.repository;

import com.argela.chess.exception.GameSessionNotFoundException;
import com.argela.chess.model.ChessBoard;

public interface ChessRepository{

    public ChessBoard getBySessionID(String sessionID) throws GameSessionNotFoundException;
    public void save(String sessionID, ChessBoard chessBoard);
    public void deleteByID(String sessionID) throws GameSessionNotFoundException;
}