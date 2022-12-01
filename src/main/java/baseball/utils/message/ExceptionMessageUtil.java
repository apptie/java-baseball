package baseball.utils.message;

import baseball.utils.consts.GameCommandConst;
import baseball.utils.consts.GameNumberConst;

public enum ExceptionMessageUtil {
    WRONG_NUMBER("정답은 숫자여야 합니다"),

    WRONG_NUMBER_RANGE("정답으로 입력되는 숫자는 %d부터 %d 사이의 값이여야 합니다.",
            GameNumberConst.MIN_VALUE,
            GameNumberConst.MAX_VALUE),

    WRONG_SIZE("정답으로 입력되는 숫자는 중복되지 않은 %d자리 숫자여야 합니다.",
            GameNumberConst.NUMBER_SIZE),

    WRONG_COMMAND("게임 재시작 선택의 경우 %s 또는 %s만을 선택할 수 있습니다.",
            GameCommandConst.RETRY,
            GameCommandConst.EXIT),

    WRONG_GENERATOR("정답으로 생성되는 숫자는 %d부터 %d 사이의 값이여야 합니다.",
            GameNumberConst.MIN_VALUE,
            GameNumberConst.MAX_VALUE),

    NOT_FOUND_VIEW("지정한 View를 찾을 수 없습니다.");

    private final String message;

    ExceptionMessageUtil(String baseMessage, final Object... replaces) {
        this.message = String.format(baseMessage, replaces);
    }

    public String findFullMessage() {
        return this.message;
    }
}
