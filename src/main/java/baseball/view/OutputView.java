package baseball.view;

import baseball.dto.output.PrintExceptionMessageDto;
import baseball.dto.output.PrintGuideMessageDto;
import baseball.dto.output.PrintResultDto;
import baseball.utils.game.GameStatus;
import java.util.function.Consumer;

public class OutputView {

    private final Consumer<String> print;

    public OutputView(final Consumer<String> print) {
        this.print = print;
    }

    public void printResult(final PrintResultDto dto) {
        print.accept(OutputViewMessage.findFullMessage(dto.getStrike(), dto.getBall()));
    }

    public void printExceptionMessage(final PrintExceptionMessageDto dto) {
        print.accept(OutputViewMessage.findFullMessage(dto.getMessage()));
    }

    public void printGuideMessage(final PrintGuideMessageDto dto) {
        print.accept(findGuideMessage(dto.isEndOfGame()));
    }

    private String findGuideMessage(boolean isEndOfGame) {
        if (isEndOfGame) {
            return OutputViewMessage.GAME_END.message;
        }
        return OutputViewMessage.GAME_START.message;
    }

    private enum OutputViewMessage {
        NOTHING("낫싱"),
        ONLY_BALL("%d볼"),
        ONLY_STRIKE("%d스트라이크"),
        BALL_AND_STRIKE("%d볼 %d스트라이크"),
        EXCEPTION("[ERROR] %s"),
        GAME_START("숫자 야구 게임을 시작합니다."),
        GAME_END("3개의 숫자를 모두 맞히셨습니다! 게임 종료");

        private static final long NO_SCORE = 0L;

        private final String message;

        OutputViewMessage(String message) {
            this.message = message;
        }

        private static String findFullMessage(String exceptionMessage) {
            return formattingMessage(EXCEPTION.message, exceptionMessage);
        }

        private static String findFullMessage(long strike, long ball) {
            if (strike == NO_SCORE && ball == NO_SCORE){
                return NOTHING.message;
            }
            return findFormattingMessage(strike, ball);
        }

        private static String findFormattingMessage(long strike, long ball) {
            if (strike == NO_SCORE) {
                return formattingMessage(ONLY_BALL.message, ball);
            }
            if (ball == NO_SCORE) {
                return formattingMessage(ONLY_STRIKE.message, strike);
            }
            return formattingMessage(BALL_AND_STRIKE.message, strike, ball);
        }

        private static String formattingMessage(String message, Object... replaces) {
            return String.format(message, replaces);
        }
    }
}
