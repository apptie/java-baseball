package baseball.view;

public final class GuideView {

    private GuideView() {
    }

    public static void printStartLog() {
        printLog(GuideViewMessage.GAME_START.message);
    }

    public static void printEndLog() {
        printLog(GuideViewMessage.GAME_END.message);
    }

    private static void printLog(String message) {
        System.out.println(message);
    }

    private enum GuideViewMessage {
        GAME_START("숫자 야구 게임을 시작합니다."),
        GAME_END("3개의 숫자를 모두 맞히셨습니다! 게임 종료");

        private final String message;

        GuideViewMessage(String message) {
            this.message = message;
        }
    }
}
