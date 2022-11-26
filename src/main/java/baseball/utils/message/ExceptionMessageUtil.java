package baseball.utils.message;

import baseball.utils.consts.GameNumberConst;

public enum ExceptionMessageUtil {
    WRONG_NUMBER("정답은 숫자여야 합니다"),

    WRONG_NUMBER_RANGE("정답으로 입력되는 숫자는 %d부터 %d 사이의 값이여야 합니다.",
            GameNumberConst.MIN_VALUE,
            GameNumberConst.MAX_VALUE),

    WRONG_SIZE("정답으로 입력되는 숫자는 중복되지 않은 %d자리 숫자여야 합니다.",
            GameNumberConst.NUMBER_SIZE);

    private final String message;

    ExceptionMessageUtil(String baseMessage, Object... replaces) {
        this.message = String.format(baseMessage, replaces);
    }

    public String findFullMessage() {
        return this.message;
    }
}
