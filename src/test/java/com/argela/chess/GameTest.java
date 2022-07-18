package com.argela.chess;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import tr.com.argela.chess.Application;
import tr.com.argela.chess.constant.Point;
import tr.com.argela.chess.exception.GameException;
import tr.com.argela.chess.model.ChessBoard;
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
                        ChessBoard board = chessService.move(sessionId, new Point(0, 1), new Point(0, 2));
                        printBoard(board);
                });
        }

        @Test
        public void test_Knight_Move() {
                String sessionId = createNewGame();
                assertDoesNotThrow(() -> {
                        ChessBoard board = chessService.move(sessionId, new Point(1, 0), new Point(0, 2));
                        printBoard(board);
                });
        }

        @Test
        public void test_Bishop_Move() {
                String sessionId = createNewGame();
                assertDoesNotThrow(() -> {
                        ChessBoard board;

                        board = chessService.move(sessionId, new Point(1, 1), new Point(1, 2));
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(2, 0), new Point(0, 2));
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(0, 2), new Point(4, 5));
                        printBoard(board);
                });
        }

        @Test
        public void test_Rook_Move() {
                String sessionId = createNewGame();
                assertDoesNotThrow(() -> {
                        ChessBoard board;
                        board = chessService.move(sessionId, new Point(0, 1), new Point(0, 2));
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(0, 2), new Point(0, 3));
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(0, 0), new Point(0, 2));
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(0, 2), new Point(7, 2));
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(7, 2), new Point(7, 5));
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(7, 5), new Point(4, 5));
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(4, 5), new Point(4, 3));
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(4, 3), new Point(1, 3));
                        printBoard(board);
                        board = chessService.move(sessionId, new Point(1, 3), new Point(1, 6));
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
