package baseball.domain.number;

import baseball.utils.consts.GameNumberConst;
import baseball.utils.message.ExceptionMessageUtil;
import java.util.Objects;

public class BaseBallNumber {

    private final int number;
    private final int index;

    public BaseBallNumber(int number, int index) {
        validateBaseBall(number, index);

        this.number = number;
        this.index = index;
    }

    private void validateBaseBall(int number, int index) {
        validateNumber(number);
        validateIndex(index);
    }

    private void validateNumber(int number) {
        if (!validateNumberRange(number)) {
            throw new IllegalArgumentException(ExceptionMessageUtil.WRONG_NUMBER_RANGE.findFullMessage());
        }
    }

    private boolean validateNumberRange(int number) {
        return (GameNumberConst.MIN_VALUE <= number && number <= GameNumberConst.MAX_VALUE);
    }

    private void validateIndex(int index) {
        if (!validateIndexRange(index)) {
            throw new IllegalArgumentException(ExceptionMessageUtil.WRONG_SIZE.findFullMessage());
        }
    }

    private boolean validateIndexRange(int index) {
        return (0 <= index && index < GameNumberConst.NUMBER_SIZE);
    }

    @Override
    public boolean equals(Object target) {
        if (this == target) {
            return true;
        }
        if (target == null || getClass() != target.getClass()) {
            return false;
        }
        BaseBallNumber targetBaseBallNumber = (BaseBallNumber) target;
        return this.number == targetBaseBallNumber.number && this.index == targetBaseBallNumber.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, index);
    }

    public boolean equalsOnlyNumber(BaseBallNumber baseBallNumber) {
        if (this.index == baseBallNumber.index) {
            return false;
        }
        return this.number == baseBallNumber.number;
    }
}
