package battleship.controller;

import battleship.classes.GameRecorder;

public class GameRecordTableRow {
    GameRecorder record;
    String playerOne;
    String playerTwo;
    String winner;
    int movesCount;

    public GameRecorder getRecord() {
        return record;
    }

    public String getPlayerOne() {
        return playerOne;
    }

    public String getPlayerTwo() {
        return playerTwo;
    }

    public String getWinner() {
        return winner;
    }

    public int getMovesCount() {
        return movesCount;
    }

    GameRecordTableRow(GameRecorder recorder) {
        record = recorder;
        playerOne = String.valueOf(recorder.getPlayerOneId());
        playerTwo = String.valueOf(recorder.getPlayerTwoId());
        winner = playerOne;
        movesCount = recorder.getRecords().size();
    }
}
