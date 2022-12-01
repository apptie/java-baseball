package baseball.view.exception;

import baseball.utils.message.ExceptionMessageUtil;

public class NotFoundViewException extends RuntimeException {

    public NotFoundViewException() {
        super(ExceptionMessageUtil.NOT_FOUND_VIEW.findFullMessage());
    }
}
