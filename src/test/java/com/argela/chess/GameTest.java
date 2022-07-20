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
                        ChessBoard board;
                        board = chessService.move(sessionId, new Point(1, 1), new Point(1, 2));// pawn one step forward
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

                        Point source = new Point(0, 0);
                        Point dest = null;

                        List<Point> actions = new ArrayList();
                        actions.add(new Point(0, 2));// rook up
                        actions.add(new Point(5, 2));// rook right
                        actions.add(new Point(5, 4));// rook up
                        actions.add(new Point(4, 4));// rook left
                        actions.add(new Point(4, 2));// rook down

                        for (Point next : actions) {
                                if (dest != null) {
                                        source = dest;
                                }
                                dest = next;
                                board = chessService.move(sessionId, source, dest);
                                printBoard(board);
                        }

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
                                                actions.add(new Point(3 + x, 4 + y));
                                                actions.add(source);
                                        }
                                }
                        }

                        int index = 0;
                        for (Point next : actions) {
                                if (dest != null) {
                                        source = dest;
                                }
                                dest = next;
                                board = chessService.move(sessionId, source, dest);
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

                        Point source = new Point(2, 0);
                        Point dest = null;

                        List<Point> actions = new ArrayList();
                        actions.add(new Point(0, 2));// bishop left up
                        actions.add(new Point(4, 6));// bishop right up eat
                        actions.add(new Point(5, 5));// bishop right down
                        actions.add(new Point(3, 3));// bishop left down

                        for (Point next : actions) {
                                if (dest != null) {
                                        source = dest;
                                }
                                dest = next;
                                board = chessService.move(sessionId, source, dest);
                                printBoard(board);
                        }

                });

        }

        @Test
        public void test_Queen_Move() {
                String sessionId = createNewGame();
                assertDoesNotThrow(() -> {
                        ChessBoard board;
                        board = chessService.move(sessionId, new Point(4, 1), new Point(4, 2));// pawn one step forward
                        printBoard(board);

                        Point source = new Point(3, 0);
                        Point dest = null;

                        List<Point> actions = new ArrayList();
                        actions.add(new Point(6, 3));// queen right up
                        actions.add(new Point(6, 4));// queen up
                        actions.add(new Point(4, 4));// queen left
                        actions.add(new Point(3, 3));// queen left down
                        actions.add(new Point(2, 4));// queen left up
                        actions.add(new Point(3, 3));// queen right down
                        actions.add(new Point(6, 3));// queen right
                        actions.add(new Point(6, 2));// queen down

                        for (Point next : actions) {
                                if (dest != null) {
                                        source = dest;
                                }
                                dest = next;
                                board = chessService.move(sessionId, source, dest);
                                printBoard(board);
                        }

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

                        actions.add(new Point(4, 1));// king up
                        actions.add(new Point(5, 2));// king right up
                        actions.add(new Point(6, 2));// king right
                        actions.add(new Point(5, 3));// king left up
                        actions.add(new Point(4, 3));// king left
                        actions.add(new Point(3, 2));// king left down
                        actions.add(new Point(4, 1));// king right down
                        actions.add(new Point(4, 0));// king down
                        for (Point next : actions) {
                                if (dest != null) {
                                        source = dest;
                                }
                                dest = next;
                                board = chessService.move(sessionId, source, dest);
                                printBoard(board);
                        }
                });
        }

        @Test
        public void test_Turn() {
                String sessionId = createNewGame();
                assertDoesNotThrow(() -> {
                        ChessBoard board;

                        board = chessService.move(sessionId, new Point(4, 1), new Point(4, 2));// white pawn one step
                                                                                               // forward
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(4, 6), new Point(4, 5));// black pawn one step
                                                                                               // forward
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(3, 0), new Point(5, 2));// white queen top right
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(5, 6), new Point(5, 5));// black pawn one step
                                                                                               // forward
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(5, 2), new Point(5, 5));// white queen up eat
                                                                                               // black pawn
                        printBoard(board);
                });
        }

        @Test
        public void test_pawnTwoStep() {
                String sessionId = createNewGame();
                assertDoesNotThrow(() -> {
                        ChessBoard board;

                        board = chessService.move(sessionId, new Point(4, 1), new Point(4, 3));// white pawn two step
                                                                                               // forward
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(5, 6), new Point(5, 4));// black pawn two step
                                                                                               // forward
                        printBoard(board);
                        /*
                         * board = chessService.move(sessionId, new Point(4, 3), new Point(4, 5));
                         * printBoard(board);
                         */
                });
        }

        @Test
        public void test_pawnCrossEat() {
                String sessionId = createNewGame();
                assertDoesNotThrow(() -> {
                        ChessBoard board;

                        board = chessService.move(sessionId, new Point(4, 1), new Point(4, 3));// white pawn two step
                                                                                               // forward
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(5, 6), new Point(5, 4));// black pawn two step
                                                                                               // forward
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(4, 3), new Point(5, 4));// white pawn cross eat
                        printBoard(board);
                });
        }

        @Test
        public void test_pawnOneStepEat() {
                String sessionId = createNewGame();
                assertDoesNotThrow(() -> {
                        ChessBoard board;

                        board = chessService.move(sessionId, new Point(4, 1), new Point(4, 3));// white pawn two step
                                                                                               // forward
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(4, 6), new Point(4, 4));// black pawn two step
                                                                                               // forward
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(4, 3), new Point(4, 4));// white pawn forward eat
                        printBoard(board);
                });
        }

        @Test
        public void test_OutOfBorder() {
                String sessionId = createNewGame();
                assertDoesNotThrow(() -> {
                        ChessBoard board;
                        board = chessService.move(sessionId, new Point(0, 1), new Point(0, 3));
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(7, 6), new Point(7, 5));
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(0, 0), new Point(0, 2));
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(7, 5), new Point(7, 4));
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(0, 2), new Point(1, 2));
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(7, 4), new Point(7, 3));
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(1, 2), new Point(1, 6));
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(7, 3), new Point(7, 2));
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(1, 6), new Point(1, 7));
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(7, 2), new Point(6, 1));
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(1, 7), new Point(1, 8));
                        printBoard(board);

                });
        }

        @Test
        public void test_Check() {
                String sessionId = createNewGame();
                assertDoesNotThrow(() -> {
                        ChessBoard board = chessService.getBoard(sessionId);
                        board = chessService.move(sessionId, new Point(5, 1), new Point(5, 2));
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(4, 6), new Point(4, 4));
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(6, 1), new Point(6, 3));
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(3, 7), new Point(7, 3));
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(1, 1), new Point(1, 2));
                        printBoard(board);

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
                                Piece piece = board.getStone(new Point(x, y));
                                String text = piece != null ? piece.getStoneType() + "[" + piece.getPlayer() + "]"
                                                : " - ";
                                cells.add(text);
                        }
                        st.addRow(cells.toArray(new String[0]));
                }

                st.print();
        }
}
