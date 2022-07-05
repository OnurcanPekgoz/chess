package com.example.demo.repository;

import com.example.demo.exception.GameSessionNotFoundException;
import com.example.demo.model.ChessBoard;

public interface ChessRepository{
    //need exceptions
    public ChessBoard getbySessionID(String sessionID) throws GameSessionNotFoundException;
    public void save(String sessionID, ChessBoard chessBoard);
    public void deleteByID(String sessionID) throws GameSessionNotFoundException;
}