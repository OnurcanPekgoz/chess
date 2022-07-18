package com.argela.chess;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import tr.com.argela.chess.Application;
import tr.com.argela.chess.constant.Player;
import tr.com.argela.chess.constant.Point;
import tr.com.argela.chess.constant.StoneType;
import tr.com.argela.chess.exception.GameException;
import tr.com.argela.chess.model.ChessBoard;
import tr.com.argela.chess.model.King;
import tr.com.argela.chess.model.Piece;
import tr.com.argela.chess.service.ChessService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
public class GameTest {

        @Autowired
        ChessService chessService;

        private String createNewGame() {
                return chessService.createNewGame();
        }

        @Test
        public void test_Pawn_Move() {
                String sessionId = createNewGame();
                assertDoesNotThrow(() -> {
                        ChessBoard board = chessService.move(sessionId, new Point(0, 1), new Point(0, 2));// pawn one
                                                                                                          // step
                                                                                                          // forward
                        printBoard(board);
                });
        }

        @Test
        public void test_Knight_Move() {
                String sessionId = createNewGame();
                assertDoesNotThrow(() -> {
                        ChessBoard board = chessService.getBoard(sessionId);

                        for (int y = 0; y < 8; y++) {
                                for (int x = 0; x < 8; x++) {
                                        board.putStone(null, new Point(x, y));
                                }
                        }

                        Point source = new Point(3, 4);
                        board.putStone(new King(Player.White, StoneType.Knight), source);

                        Point dest = null;

                        List<Point> actions = new ArrayList();
                        for (int y = -2; y <= 2; y++) {
                                for (int x = -2; x <= 2; x++) {
                                        if (Math.abs(x) + Math.abs(y) == 3) {
                                                actions.add(new Point(3+x,4+ y));
                                                actions.add(source);
                                        }
                                }
                        }

                        int index=0;
                        for (Point next : actions) {
                                if (dest != null) {
                                        source = dest;
                                }
                                dest = next;
                                board = chessService.move(sessionId, source, dest);// king up
                                System.out.println(index++);
                                printBoard(board);
                        }
                });
        }

        @Test
        public void test_Bishop_Move() {
                String sessionId = createNewGame();
                assertDoesNotThrow(() -> {
                        ChessBoard board;

                        board = chessService.move(sessionId, new Point(1, 1), new Point(1, 2));// pawn one step forward
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(2, 0), new Point(0, 2));// bishop left-up
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(0, 2), new Point(4, 6));// bishop right-up eat
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(4, 6), new Point(5, 5));// bishop right-down
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(5, 5), new Point(4, 4));// bishop left-down
                        printBoard(board);
                });
        }

        @Test
        public void test_Rook_Move() {
                String sessionId = createNewGame();
                assertDoesNotThrow(() -> {
                        ChessBoard board;
                        board = chessService.move(sessionId, new Point(0, 1), new Point(0, 2));// pawn one step forward
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(0, 2), new Point(0, 3));// pawn one step forward
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(0, 0), new Point(0, 2));// rook up
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(0, 2), new Point(7, 2));// rook right
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(7, 2), new Point(7, 5));// rook up
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(7, 5), new Point(4, 5));// rook left
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(4, 5), new Point(4, 3));// rook down
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(4, 3), new Point(1, 3));// rook left
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(1, 3), new Point(1, 6));// rook up eat
                        printBoard(board);
                });
        }

        @Test
        public void test_Queen_Move() {
                String sessionId = createNewGame();
                assertDoesNotThrow(() -> {
                        ChessBoard board;
                        board = chessService.move(sessionId, new Point(4, 1), new Point(4, 2));// pawn one step forward
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(3, 0), new Point(5, 2));// queen right-up
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(5, 2), new Point(5, 3));// queen up
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(5, 3), new Point(7, 3));// queen right
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(7, 3), new Point(6, 4));// queen left-up
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(6, 4), new Point(1, 4));// queen left
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(1, 4), new Point(1, 3));// queen down
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(1, 3), new Point(0, 2));// queen left-down
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(1, 1), new Point(1, 2));// pawn one step forward
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(0, 2), new Point(1, 1));// queen right-down
                        printBoard(board);
                });
        }

        @Test
        public void test_King_Move() {
                String sessionId = createNewGame();
                assertDoesNotThrow(() -> {
                        ChessBoard board;

                        board = chessService.move(sessionId, new Point(4, 1), new Point(4, 2));// pawn one step forward
                        printBoard(board);

                        Point source = new Point(4, 0);
                        Point dest = null;

                        List<Point> actions = new ArrayList();

                        actions.add(new Point(4, 1));
                        actions.add(new Point(5, 2));
                        actions.add(new Point(6, 2));
                        actions.add(new Point(5, 3));
                        actions.add(new Point(4, 3));
                        actions.add(new Point(3, 2));
                        actions.add(new Point(4, 1));
                        actions.add(new Point(4, 0));
                        for (Point next : actions) {
                                if (dest != null) {
                                        source = dest;
                                }
                                dest = next;
                                board = chessService.move(sessionId, source, dest);// king up
                                printBoard(board);
                        }
                });
        }

        private void printBoard(ChessBoard board) {
                // test code
                TableView st = new TableView();

                st.setShowVerticalLines(true);
                st.setHeaders("#", "0", "1", "2", "3", "4", "5", "6", "7");
                for (int y = 7; y >= 0; y--) {
                        List<String> cells = new ArrayList();
                        cells.add(y + "");
                        for (int x = 0; x <= 7; x++) {
                                Piece piece = board.getBoard()[y][x];
                                String text = piece != null ? piece.getStoneType() + "[" + piece.getPlayer() + "]"
                                                : " - ";
                                cells.add(text);
                        }
                        st.addRow(cells.toArray(new String[0]));
                }

                st.print();
        }
}
