package baseball.domain.game;

import baseball.domain.number.BaseBallNumbers;
import baseball.dto.controller.GameCommandDto;
import baseball.dto.controller.GameResultDto;
import baseball.dto.input.ReadPlayerAnswerDto;
import baseball.dto.input.ReadPlayerCommandDto;
import baseball.utils.game.GameStatus;

public class BaseBallGame {

    private BaseBallNumbers computerAnswer;

    public BaseBallGame(final BaseBallNumbers computerAnswer) {
        this.computerAnswer = computerAnswer;
    }

    public GameResultDto calculateGameResult(final ReadPlayerAnswerDto dto) {
        BaseBallNumbers playerAnswer = new BaseBallNumbers(dto.getPlayerInput());

        long strike = computerAnswer.calculateStrike(playerAnswer);
        long ball = computerAnswer.calculateBall(playerAnswer);

        return new GameResultDto(strike, ball);
    }

    public GameCommandDto calculateGameCommand(final ReadPlayerCommandDto dto) {
        GameCommand gameCommand = GameCommand.findGameCommand(dto.getPlayerCommand());

        if (gameCommand == GameCommand.RETRY) {
            this.computerAnswer = new BaseBallNumbers();
            return new GameCommandDto(GameStatus.GAME_PLAY);
        }
        return new GameCommandDto(GameStatus.APPLICATION_EXIT);
    }
}
