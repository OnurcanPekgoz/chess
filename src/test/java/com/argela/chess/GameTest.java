package com.argela.chess;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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
                try {
                        ChessBoard board = chessService.move(sessionId, new Point(0, 1), new Point(0, 2));
                        System.out.println(board.getBoard()[1][0] + "--->" + board.getBoard()[2][0]);
                } catch (GameException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }

        }
}
