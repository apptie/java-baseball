package baseball.view;

import baseball.dto.output.PrintExceptionMessageDto;
import baseball.dto.output.PrintResultDto;
import java.util.function.Consumer;

public class OutputView {

    private final Consumer<String> print;

    public OutputView(Consumer<String> print) {
        this.print = print;
    }

    public void printResult(PrintResultDto dto) {
        print.accept(OutputViewMessage.findFullMessage(dto.getStrike(), dto.getBall()));
    }

    public void printExceptionMessage(PrintExceptionMessageDto dto) {
        print.accept(OutputViewMessage.findFullMessage(dto.getMessage()));
    }

    private enum OutputViewMessage {
        NOTHING("낫싱"),
        ONLY_BALL("%d볼"),
        ONLY_STRIKE("%d스트라이크"),
        BALL_AND_STRIKE("%d볼 %d스트라이크"),
        EXCEPTION("[ERROR] %s");

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
