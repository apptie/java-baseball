package baseball.domain.game;

import baseball.domain.number.BaseBallNumbers;
import baseball.dto.controller.GameCommandDto;
import baseball.dto.controller.GameResultDto;
import baseball.dto.input.ReadPlayerAnswerDto;
import baseball.dto.input.ReadPlayerCommandDto;
import baseball.utils.game.GameStatus;
import baseball.utils.generator.BaseBallNumberGenerator;

public class BaseBallGame {

    private final BaseBallNumberGenerator generator;
    private BaseBallNumbers computerAnswer;

    public BaseBallGame(BaseBallNumberGenerator generator) {
        this.generator = generator;
        this.computerAnswer = createComputerAnswer();
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
            this.computerAnswer = createComputerAnswer();
            return new GameCommandDto(GameStatus.GAME_PLAY);
        }
        return new GameCommandDto(GameStatus.APPLICATION_EXIT);
    }

    private BaseBallNumbers createComputerAnswer() {
        return new BaseBallNumbers(generator);
    }
}
