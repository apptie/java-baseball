package baseball.controller;

import baseball.domain.game.BaseBallGame;
import baseball.domain.number.BaseBallNumbers;
import baseball.dto.controller.GameCommandDto;
import baseball.dto.controller.GameResultDto;
import baseball.dto.input.ReadPlayerAnswerDto;
import baseball.dto.input.ReadPlayerCommandDto;
import baseball.dto.output.PrintExceptionMessageDto;
import baseball.dto.output.PrintGuideMessageDto;
import baseball.dto.output.PrintResultDto;
import baseball.utils.game.GameStatus;
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

    public GameController(final IOViewResolver ioViewResolver) {
        gameStatusMappings = new EnumMap<>(GameStatus.class);

        this.ioViewResolver = ioViewResolver;
        initGameStatusMappings();
    }

    private void initGameStatusMappings() {
        gameStatusMappings.put(GameStatus.APPLICATION_START, this::startGame);
        gameStatusMappings.put(GameStatus.GAME_PLAY, this::playGame);
        gameStatusMappings.put(GameStatus.GAME_EXIT, this::retryGame);
    }

    public GameStatus process(final GameStatus gameStatus) {
        try {
            return gameStatusMappings.get(gameStatus).get();
        } catch (IllegalArgumentException e) {
            ioViewResolver.resolveOutputView(new PrintExceptionMessageDto(e.getMessage()));
            throw e;
        } catch (NullPointerException | NotFoundViewException e) {
            System.out.println(APPLICATION_EXCEPTION_MESSAGE);
            throw e;
        }
    }

    private GameStatus startGame() {
        ioViewResolver.resolveOutputView(new PrintGuideMessageDto(GameStatus.APPLICATION_START));
        baseBallGame = new BaseBallGame(new BaseBallNumbers());

        return GameStatus.GAME_PLAY;
    }

    private GameStatus playGame() {
        ReadPlayerAnswerDto readPlayerAnswerDto = ioViewResolver.resolveInputView(ReadPlayerAnswerDto.class);
        GameResultDto gameResultDto = baseBallGame.calculateGameResult(readPlayerAnswerDto);

        ioViewResolver.resolveOutputView(new PrintResultDto(gameResultDto.getStrike(), gameResultDto.getBall()));
        GameStatus nextGameStatus = gameResultDto.getNextGameStatus();

        if (nextGameStatus == GameStatus.GAME_EXIT) {
            ioViewResolver.resolveOutputView(new PrintGuideMessageDto(nextGameStatus));
        }
        return nextGameStatus;
    }

    private GameStatus retryGame() {
        ReadPlayerCommandDto readPlayerCommandDto = ioViewResolver.resolveInputView(ReadPlayerCommandDto.class);
        GameCommandDto gameCommandDto = baseBallGame.calculateGameCommand(readPlayerCommandDto);

        return gameCommandDto.getNextGameStatus();
    }
}
