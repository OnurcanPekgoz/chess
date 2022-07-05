package com.argela.chess.model;

import com.argela.chess.constant.GameState;
import com.argela.chess.constant.Player;
import com.argela.chess.model.Piece.Color;

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
            Piece blackPawn = new Pawn(Color.Black);
            board[1][i] = blackPawn;
        }

        Piece blackRook1 = new Rook(Color.Black);
        board[0][0] = blackRook1;
        Piece blackRook2 = new Rook(Color.Black);
        board[0][7] = blackRook2;

        Piece blackKnight1 = new Knight(Color.Black);
        board[0][1] = blackKnight1;
        Piece blackKnight2 = new Knight(Color.Black);
        board[0][6] = blackKnight2;

        Piece blackBishop1 = new Bishop(Color.Black);
        board[0][2] = blackBishop1;
        Piece blackBishop2 = new Bishop(Color.Black);
        board[0][5] = blackBishop2;

        Piece blackQueen = new Queen(Color.Black);
        board[0][3] = blackQueen;

        Piece blackKing = new King(Color.Black);
        board[0][4] = blackKing;

        for (int i = 0; i < 8; i++) {
            Piece whitePawn = new Pawn(Color.White);
            board[6][i] = whitePawn;
        }

        Piece whiteRook1 = new Rook(Color.White);
        board[7][0] = whiteRook1;
        Piece whiteRook2 = new Rook(Color.White);
        board[7][7] = whiteRook2;

        Piece whiteKnight1 = new Knight(Color.White);
        board[7][1] = whiteKnight1;
        Piece whiteKnight2 = new Knight(Color.White);
        board[7][6] = whiteKnight2;

        Piece whiteBishop1 = new Bishop(Color.White);
        board[7][2] = whiteBishop1;
        Piece whiteBishop2 = new Bishop(Color.White);
        board[7][5] = whiteBishop2;

        Piece whiteQueen = new Queen(Color.White);
        board[7][3] = whiteQueen;

        Piece whiteKing = new King(Color.White);
        board[7][4] = whiteKing;
    }
}