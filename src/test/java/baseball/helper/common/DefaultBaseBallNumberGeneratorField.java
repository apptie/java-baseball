package baseball.helper.common;

import baseball.helper.stub.StubBaseBallNumberGenerator;
import baseball.utils.generator.BaseBallNumberGenerator;

public class DefaultBaseBallNumberGeneratorField {

    protected BaseBallNumberGenerator generator = new StubBaseBallNumberGenerator("123");
}
