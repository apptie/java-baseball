package baseball.dto.controller;

import baseball.utils.game.GameStatus;

public class GameResultDto {

    private final long strike;
    private final long ball;

    public GameResultDto(long strike, long ball) {
        this.strike = strike;
        this.ball = ball;
    }

    public long getStrike() {
        return strike;
    }

    public long getBall() {
        return ball;
    }

    public GameStatus getNextGameStatus() {
        return GameStatus.findNextPlay(this.strike);
    }
}
