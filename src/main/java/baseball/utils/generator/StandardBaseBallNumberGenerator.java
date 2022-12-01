package baseball.utils.generator;

import camp.nextstep.edu.missionutils.Randoms;

public class StandardBaseBallNumberGenerator implements BaseBallNumberGenerator {

    private final int min;
    private final int max;

    public StandardBaseBallNumberGenerator(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public int generate() {
        return Randoms.pickNumberInRange(min, max);
    }
}
