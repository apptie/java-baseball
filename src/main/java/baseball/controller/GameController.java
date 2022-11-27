package baseball.controller;

import baseball.domain.game.BaseBallGame;
import baseball.domain.number.BaseBallNumbers;
import baseball.dto.controller.GameCommandDto;
import baseball.dto.controller.GameResultDto;
import baseball.dto.input.ReadPlayerAnswerDto;
import baseball.dto.input.ReadPlayerCommandDto;
import baseball.dto.output.PrintExceptionMessageDto;
import baseball.dto.output.PrintResultDto;
import baseball.utils.game.GameStatus;
import baseball.view.GuideView;
import baseball.view.IOViewResolver;
import baseball.view.exception.NotFoundViewException;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public class GameController {

    private static final String APPLICATION_EXCEPTION_MESSAGE = "애플리케이션이 문제가 발생했습니다.";

    private final IOViewResolver ioViewResolver;
    private final Map<GameStatus, Supplier<GameStatus>> gameStatusMappings;
    private BaseBallGame baseBallGame;

    public GameController(IOViewResolver ioViewResolver) {
        gameStatusMappings = new EnumMap<>(GameStatus.class);

        this.ioViewResolver = ioViewResolver;
        initGameStatusMappings();
    }

    private void initGameStatusMappings() {
        gameStatusMappings.put(GameStatus.APPLICATION_START, this::startGame);
        gameStatusMappings.put(GameStatus.GAME_PLAY, this::playGame);
        gameStatusMappings.put(GameStatus.GAME_EXIT, this::retryGame);
    }

    public GameStatus process(GameStatus gameStatus) {
        try {
            return gameStatusMappings.get(gameStatus).get();
        } catch (IllegalArgumentException e) {
            ioViewResolver.outputViewResolve(new PrintExceptionMessageDto(e.getMessage()));
            throw e;
        } catch (NullPointerException | NotFoundViewException e) {
            System.out.println(APPLICATION_EXCEPTION_MESSAGE);
            throw e;
        }
    }

    private GameStatus startGame() {
        GuideView.printStartLog();

        baseBallGame = new BaseBallGame(new BaseBallNumbers());

        return GameStatus.GAME_PLAY;
    }

    private GameStatus playGame() {
        ReadPlayerAnswerDto readPlayerAnswerDto = ioViewResolver.inputViewResolve(ReadPlayerAnswerDto.class);
        GameResultDto gameResultDto = baseBallGame.calculateGameResult(readPlayerAnswerDto);

        ioViewResolver.outputViewResolve(new PrintResultDto(gameResultDto.getStrike(), gameResultDto.getBall()));
        return gameResultDto.getNextGameStatus();
    }

    private GameStatus retryGame() {
        ReadPlayerCommandDto readPlayerCommandDto = ioViewResolver.inputViewResolve(ReadPlayerCommandDto.class);
        GameCommandDto gameCommandDto = baseBallGame.calculateGameCommand(readPlayerCommandDto);

        return gameCommandDto.getNextGameStatus();
    }
}
