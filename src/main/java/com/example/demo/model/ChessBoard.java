package com.example.demo.model;

import com.example.demo.constant.GameState;
import com.example.demo.constant.Player;
import lombok.*;

@Getter
@Setter
public class ChessBoard {
    String sessionID;

    Player currentPlayer;
    Player nextPlayer;
    Player winnerPlayer;
    GameState gameState;
    Piece[][] board = new Piece[8][8];

    public ChessBoard(String sessionID) {
        this.sessionID = sessionID;
        initializeBoard();
    }

    public void initializeBoard() {
        currentPlayer = Player.ONE;
        nextPlayer = Player.TWO;
        gameState = GameState.ACTIVE;

        for (int i = 0; i < 8; i++) {
            Piece blackPawn = new Pawn("Black");
            board[1][i] = blackPawn;
        }

        Piece blackRook = new Rook("Black");
        board[0][0] = blackRook;
        board[0][7] = blackRook;

        Piece blackKnight = new Knight("Black");
        board[0][1] = blackKnight;
        board[0][6] = blackKnight;

        Piece blackBishop = new Bishop("Black");
        board[0][2] = blackBishop;
        board[0][5] = blackBishop;

        Piece blackQueen = new Queen("Black");
        board[0][3] = blackQueen;

        Piece blackKing = new King("Black");
        board[0][4] = blackKing;

        for (int i = 0; i < 8; i++) {
            Piece whitePawn = new Pawn("White");
            board[6][i] = whitePawn;
        }

        Piece whiteRook = new Rook("White");
        board[7][0] = whiteRook;
        board[7][7] = whiteRook;

        Piece whiteKnight = new Knight("White");
        board[7][1] = whiteKnight;
        board[7][6] = whiteKnight;

        Piece whiteBishop = new Bishop("White");
        board[7][2] = whiteBishop;
        board[7][5] = whiteBishop;

        Piece whiteQueen = new Queen("White");
        board[7][3] = whiteQueen;

        Piece whiteKing = new King("White");
        board[7][4] = whiteKing;
    }
}