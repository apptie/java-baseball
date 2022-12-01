package baseball.domain.number.exception;

import baseball.utils.message.ExceptionMessageUtil;

public class WrongGeneratorException extends RuntimeException {

    public WrongGeneratorException() {
        super(ExceptionMessageUtil.WRONG_GENERATOR.findFullMessage());
    }
}
