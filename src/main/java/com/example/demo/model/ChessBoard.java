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
    Piece[][] board= new Piece[8][8];

    public ChessBoard(String sessionID){
        this.sessionID=sessionID;
        initializeBoard();
    }

    public void initializeBoard(){
        currentPlayer=Player.ONE;
        nextPlayer=Player.TWO;
        gameState=GameState.ACTIVE;

        for(int i=0;i<8;i++){
            Piece blackPawn= new Pawn("Black");
            board[1][i]=blackPawn;
        }
        for(int i=0;i<8;i++){
            Piece whitePawn= new Pawn("White");
            board[6][i]=whitePawn;
        }
    }
}