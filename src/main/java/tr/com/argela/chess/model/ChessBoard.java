package tr.com.argela.chess.model;

import lombok.*;
import tr.com.argela.chess.constant.GameState;
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
    Piece[][] board = new Piece[8][8];

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
                for (int xPos : stoneType.getXPos()){
                    Piece piece = stoneType.getInstance(player);
                    Point point = new Point(xPos, player.getBeginPos() + (stoneType.getYPos() * player.getDirection()));
                    putStone(piece,point);
                }           
            }
        }
    }

    public Piece getStone(Point point) {
        return board[point.getY()][point.getX()];
    }

    public void putStone(Piece piece, Point point) {
        System.out.println("Stone : "+piece.getStoneType() + " to " + point);
        board[point.getY()][point.getX()] = piece;
    }
}