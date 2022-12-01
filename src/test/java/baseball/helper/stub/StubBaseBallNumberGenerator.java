package baseball.helper.stub;

import baseball.utils.generator.BaseBallNumberGenerator;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StubBaseBallNumberGenerator implements BaseBallNumberGenerator {

    private static final String SPLIT_SEPARATOR = "";

    private final List<Integer> returnValues;
    private int index;

    public StubBaseBallNumberGenerator(String returnValue) {
        this.returnValues = Arrays.stream(returnValue.split(SPLIT_SEPARATOR))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        index = 0;
    }

    @Override
    public int generate() {
        if (index == returnValues.size()) {
            index = 0;
        }
        return returnValues.get(index++);
    }
}
