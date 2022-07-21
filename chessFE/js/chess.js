URL_chess_API = "http://localhost:8080/chess"


chessGameInfo = {
    sessionId: "",
    chessBoard: {},
    source: {
        x: 64,
        y: 64,
    },
    dest: {
        x: 64,
        y: 64,
    }
}

magicNumber = 64;

function startGame() {
    $.get(URL_chess_API + "/start", function (result) {
        chessGameInfo.sessionId = result.sessionID;
        initBoard();
        getBoard();
    });
}

function getBoard() {
    $.get(URL_chess_API + "/board/" + chessGameInfo.sessionId, function (result) {
        chessGameInfo.chessBoard = result.chessBoard;
        drawBoard(chessGameInfo.chessBoard);
    });
}


function sendPlayerMove() {
    request = $.get(URL_chess_API + "/move/" + chessGameInfo.sessionId + "/" + chessGameInfo.source.x + "/" + chessGameInfo.source.y + "/" + chessGameInfo.dest.x + "/" + chessGameInfo.dest.y);

    request.done(function (result) {
        chessGameInfo.source.x= magicNumber;
        chessGameInfo.source.y= magicNumber;
        chessGameInfo.dest.x= magicNumber;
        chessGameInfo.dest.y= magicNumber;
        console.log("[Success] New Move: player: " + chessGameInfo.chessBoard.currentPlayer + " source :(" + chessGameInfo.source.x + "," + chessGameInfo.source.y + "), dest:(" + chessGameInfo.dest.x + "," + chessGameInfo.dest.y+")")        
        chessGameInfo.chessBoard = result.chessBoard;
        drawBoard(chessGameInfo.chessBoard);
    });
    request.fail(function (xhr, statusText, errorThrown) {
        chessGameInfo.source.x= magicNumber;
        chessGameInfo.source.y= magicNumber;
        chessGameInfo.dest.x= magicNumber;
        chessGameInfo.dest.y= magicNumber;
        msg = JSON.parse(xhr.responseText).message;
        console.log("[Fail] New Move: player: " + chessGameInfo.chessBoard.currentPlayer + " source :(" + chessGameInfo.source.x + "," + chessGameInfo.source.y + "), dest:(" + chessGameInfo.dest.x + "," + chessGameInfo.dest.y+")" + " , msg:" + msg)
        alert(msg);
    });
}

function initBoard() {
    $(".playerPanel").show();
    $(".board table").show();
    $(".winnerPanel").hide();
}

function completeGame(chessBoard) {
    $(".winnerPanel").show();
    $(".winnerName").html("Player " + chessBoard.winnerPlayer);
}

function drawBoard(chessBoard) {
    $(".playerName").html("Player " + chessBoard.currentPlayer);

    board = chessBoard.board;
    position = 0;

    $.each(board, function (y, row) {
        $.each(row, function (x, stone) {
            listData = "";
            if (stone == null) {
                listData = "<li><a href='#' class='stoneButton' id='stoneButton_" + position + "' ><span>&nbsp;</span></a></li>"
            } else {
                stonePlayer = stone.player == 'White' ? 'white' : 'black';
                stoneType = stone.stoneType;

                listData = listData + '<li><a href="#" class="stoneButton" id="stoneButton_' + position + '" ><img src="./img/' + stonePlayer + '-' + stoneType + '.png" ></a></li>';
            }

            $("#" + position).html(listData);
            position++;
        });
    });

    if (chessBoard.gameState === 'COMPLETED') {
        completeGame(chessBoard);
    }

    bindStoneButtons();
}

function bindButtons() {
    $(".startButton").click(function () {
        startGame();
    });

    bindStoneButtons();
}



function bindStoneButtons() {
    $(".stoneButton").click(function () {
        for (i = 0; i < 24; i++) {
            $("#td_pit_" + i).css('border-color', 'black');
        }

        if (chessGameInfo.source.x == magicNumber) {
            position=parseInt($(this).attr("id").substr(12));
            chessGameInfo.source.x = position%8;
            chessGameInfo.source.y = Math.floor(position/8);
            return;
        } else {
            position=parseInt($(this).attr("id").substr(12));
            chessGameInfo.dest.x = position%8;
            chessGameInfo.dest.y = Math.floor(position/8);
        }

        if (chessGameInfo.source.x != magicNumber && chessGameInfo.dest.x != magicNumber) {
            sendPlayerMove();
        }
    });
}

function setBoardSize() {
    /*  ratio_x = $(".board").width() / 289;
      ratio_y = $(".board").height() / 290;
      $(".board").width(ratio_x * 289);
      $(".board table tbody").height(ratio_y * 290);
      */
}

function createBoardButtons() {
    pitSize = 8;
    boardRows = [7, 6, 5, 4, 3, 2, 1, 0]

    $.each(boardRows, function (index, boardRow) {
        rowData = "<tr>";
        //Roll dices
        for (pit = 0; pit < pitSize; pit++) {
            pitId = boardRow * 8 + pit;
            rowData = rowData + '<td id="td_pit_' + pitId + '" class="pit ' + '"><ul id="' + (pitId) + '" class="no-bullets"><li>&nbsp;</li></ul></td>';
        }
        rowData = rowData + "</tr>";
        $('.board table> tbody:last').append($(rowData));
    });
}


function setWindowSizeListener() {
    $(window).resize(function () {
        setBoardSize();
    });
}

function initApplication() {
    setWindowSizeListener();
    setBoardSize();
    createBoardButtons();
    bindButtons();
}

