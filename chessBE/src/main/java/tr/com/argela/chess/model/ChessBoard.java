package tr.com.argela.chess.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.*;
import tr.com.argela.chess.constant.GameState;
import tr.com.argela.chess.constant.Pair;
import tr.com.argela.chess.constant.Player;
import tr.com.argela.chess.constant.Point;
import tr.com.argela.chess.constant.StoneType;

@Getter
@Setter
public class ChessBoard {
    String sessionID;

    Player currentPlayer;
    Player nextPlayer;
    Player winnerPlayer;
    GameState gameState;
    private Piece[][] board = new Piece[8][8];

    Map<Player, Point> kingPositions = new HashMap<>();
    List<Pair> whitePiecesList = new ArrayList<>();
    List<Pair> blackPiecesList = new ArrayList<>();

    public ChessBoard(String sessionID) {
        this.sessionID = sessionID;
        initializeBoard();
    }

    public void initializeBoard() {
        currentPlayer = Player.White;
        nextPlayer = Player.Black;
        gameState = GameState.ACTIVE;

        for (Player player : Player.values()) {
            for (StoneType stoneType : StoneType.values()) {
                for (int xPos : stoneType.getXPos()) {
                    Piece piece = stoneType.getInstance(player);
                    Point point = new Point(xPos, player.getBeginPos() + (stoneType.getYPos() * player.getDirection()));
                    putStone(piece, point);
                }
            }
        }
    }

    public Piece getStone(Point point) {
        return board[point.getY()][point.getX()];
    }

    public void putStone(Piece piece, Point point) {
        board[point.getY()][point.getX()] = piece;
        if (piece != null && piece.getStoneType() == StoneType.King) {
            kingPositions.put(piece.getPlayer(), point);
        }
        if (piece != null && piece.getPlayer() == Player.White) {
            whitePiecesList.add(new Pair(piece, point));
        }
        if (piece != null && piece.getPlayer() == Player.Black) {
            blackPiecesList.add(new Pair(piece, point));
        }
    }

    public void swapTurn() {
        Player tempPlayer = currentPlayer;
        currentPlayer = nextPlayer;
        nextPlayer = tempPlayer;
    }

    public void pieceListFixer(ChessBoard board) {
        for (int i = 0; i < whitePiecesList.size(); i++) {
            if (board.getStone(whitePiecesList.get(i).getPoint()) == null || board.getStone(whitePiecesList.get(i).getPoint()).getPlayer()==Player.Black) {
                whitePiecesList.remove(i);
            }
        }
        for (int i = 0; i < blackPiecesList.size(); i++) {
            if (board.getStone(blackPiecesList.get(i).getPoint()) == null || board.getStone(blackPiecesList.get(i).getPoint()).getPlayer()==Player.White) {
                blackPiecesList.remove(i);
            }
        }
    }
}