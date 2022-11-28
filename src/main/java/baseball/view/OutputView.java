package baseball.view;

import baseball.dto.output.PrintExceptionMessageDto;
import baseball.dto.output.PrintGuideMessageDto;
import baseball.dto.output.PrintResultDto;

public class OutputView {

    private OutputView() {
    }

    private static class OutputViewSingletonHelper {
        private static final OutputView OUTPUT_VIEW = new OutputView();
    }

    public static OutputView getInstance(){
        return OutputViewSingletonHelper.OUTPUT_VIEW;
    }

    public void printResult(final PrintResultDto dto) {
        print(OutputViewMessage.findFullMessage(dto.getStrike(), dto.getBall()));
    }

    public void printExceptionMessage(final PrintExceptionMessageDto dto) {
        print(OutputViewMessage.findFullMessage(dto.getMessage()));
    }

    public void printGuideMessage(final PrintGuideMessageDto dto) {
        print(findGuideMessage(dto.isEndOfGame()));
    }

    private String findGuideMessage(boolean isEndOfGame) {
        if (isEndOfGame) {
            return OutputViewMessage.GAME_END.message;
        }
        return OutputViewMessage.GAME_START.message;
    }

    private void print(String message) {
        System.out.println(message);
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
