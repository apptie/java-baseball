package baseball.utils.generator;

import static org.assertj.core.api.Assertions.assertThat;

import baseball.utils.consts.GameNumberConst;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;

class StandardBaseBallNumberGeneratorTest {

    private final BaseBallNumberGenerator generator = new StandardBaseBallNumberGenerator(
            GameNumberConst.MIN_VALUE, GameNumberConst.MAX_VALUE);

    @Nested
    @DisplayName("generate 메소드는")
    class DescribeGenerateMethodTest {

        @Nested
        @DisplayName("만약 호출하면")
        class ContextWithoutParameterTest {

            @RepeatedTest(10)
            @DisplayName("랜덤 값을 반환한다")
            void it_returns_randomNumbers() {
                int randomNumber = generator.generate();

                assertThat(randomNumber)
                        .isGreaterThanOrEqualTo(GameNumberConst.MIN_VALUE)
                        .isLessThanOrEqualTo(GameNumberConst.MAX_VALUE);

            }
        }
    }
}