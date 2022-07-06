package tr.com.argela.chess.constant;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import tr.com.argela.chess.model.Piece;

@Getter
public enum StoneType {
    Pawn(tr.com.argela.chess.model.Pawn.class, new MoveType[] { MoveType.UP, MoveType.FORWARD_CROSS }, MoveAmountType.ONE,
            1, 0, 1, 2, 3, 4, 5, 6, 7),
    Rook(tr.com.argela.chess.model.Rook.class,
            new MoveType[] { MoveType.UP, MoveType.DOWN, MoveType.LEFT, MoveType.RIGHT }, MoveAmountType.MULTI, 0, 0,
            7),
    Knight(tr.com.argela.chess.model.Knight.class, new MoveType[] { MoveType.L_TYPE }, MoveAmountType.ONE, 0, 1, 6),
    Bishop(tr.com.argela.chess.model.Bishop.class, new MoveType[] { MoveType.ALL_CROSS }, MoveAmountType.MULTI, 0, 2, 5),
    Queen(tr.com.argela.chess.model.Queen.class,
            new MoveType[] { MoveType.ALL_CROSS, MoveType.UP, MoveType.DOWN, MoveType.LEFT, MoveType.RIGHT },
            MoveAmountType.MULTI, 0, 3),
    King(tr.com.argela.chess.model.King.class,
            new MoveType[] { MoveType.ALL_CROSS, MoveType.UP, MoveType.DOWN, MoveType.LEFT, MoveType.RIGHT },
            MoveAmountType.ONE, 0, 4);

    Class<? extends Piece> pieceClass;
    int[] xPos;
    int yPos;
    MoveType[] moveTypes;
    MoveAmountType moveAmountType;
    private List<MoveType>  moveTypeList;
    

    StoneType(Class<? extends Piece> pClass, MoveType[] moveTypes, MoveAmountType moveAmountType, int yPos,
            int... xPositons) {
        pieceClass = pClass;
        this.xPos = xPositons;
        this.yPos = yPos;
        this.moveTypes = moveTypes;
        this.moveAmountType = moveAmountType;
        moveTypeList = Arrays.asList(moveTypes);
    }


    public boolean isValidMove(MoveType moveType){
        return moveTypeList.contains(moveType);
    }

    public Piece getInstance(Player player) {
        try {
            return pieceClass.getConstructor(Player.class, StoneType.class).newInstance(player, this);
        } catch (Exception e) {
            return null;
        }
    }

}