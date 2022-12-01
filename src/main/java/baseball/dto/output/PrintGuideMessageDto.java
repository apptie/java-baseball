package baseball.dto.output;

import baseball.utils.game.GameStatus;

public class PrintGuideMessageDto {

    private final GameStatus gameStatus;

    public PrintGuideMessageDto(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public boolean isEndOfGame() {
        return gameStatus == GameStatus.GAME_EXIT;
    }
}
