package baseball.dto.controller;

import baseball.utils.game.GameStatus;

public class GameCommandDto {

    private final GameStatus nextGameStatus;

    public GameCommandDto(GameStatus nextGameStatus) {
        this.nextGameStatus = nextGameStatus;
    }

    public GameStatus getNextGameStatus() {
        return nextGameStatus;
    }
}
