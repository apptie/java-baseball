package baseball.domain.number;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import baseball.domain.number.exception.WrongGeneratorException;
import baseball.helper.common.DefaultBaseBallNumberGeneratorField;
import baseball.utils.generator.BaseBallNumberGenerator;
import baseball.utils.generator.StandardBaseBallNumberGenerator;
import baseball.utils.message.ExceptionMessageUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class BaseBallNumbersTest {

    @Nested
    @DisplayName("BaseBallNumberGenerator generator를 매개변수로 받는 생성자는")
    class DescribeBaseBallNumberGeneratorConstructorTest {

        @Nested
        @DisplayName("만약 유효한 generator가 주어지면")
        class ContextWithBaseBallNumberGeneratorTest extends DefaultBaseBallNumberGeneratorField {

            @RepeatedTest(10)
            @DisplayName("BaseBallNumbers를 반환한다")
            void it_returns_baseBallNumbers() {
                assertThatCode(() -> new BaseBallNumbers(generator)).doesNotThrowAnyException();
            }
        }

        @Nested
        @DisplayName("만약 유효하지 않은 generator가 주어지면")
        class ContextWithInvalidBaseBallNumberGeneratorTest {

            private final BaseBallNumberGenerator invalidGenerator = new StandardBaseBallNumberGenerator(0, 0);

            @RepeatedTest(10)
            @DisplayName("WrongGeneratorException 예외가 발생한다")
            void it_throws_exception() {
                assertThatThrownBy(() -> new BaseBallNumbers(invalidGenerator))
                        .isInstanceOf(WrongGeneratorException.class)
                        .hasMessageContaining(ExceptionMessageUtil.WRONG_GENERATOR.findFullMessage());
            }
        }
    }

    @Nested
    @DisplayName("String playerInput을 받는 생성자는")
    class DescribeStringConstructorTest {

        @Nested
        @DisplayName("만약 1 ~ 9까지의 중복되지 않는 세 자릿수의 유효한 숫자를 입력으로 주어지면")
        class ContextWithInputTest {

            @ParameterizedTest
            @ValueSource(strings = {"123", "234", "456", "567"})
            @DisplayName("BaseBallNumbers를 생성해 반환한다")
            void it_returns_baseBallNumbers(String input) {
                assertThatCode(() -> new BaseBallNumbers(input)).doesNotThrowAnyException();
            }
        }

        @Nested
        @DisplayName("만약 숫자가 아닌 입력이 주어지면")
        class ContextWithInvalidNumberFormatTest {

            @ParameterizedTest
            @ValueSource(strings = {"1@3", "!34", " 56", ",67"})
            @DisplayName("IllegalArgumentException 예외가 발생한다")
            void it_throws_exception(String invalidInput) {
                assertThatThrownBy(() -> new BaseBallNumbers(invalidInput))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage(ExceptionMessageUtil.WRONG_NUMBER.findFullMessage());
            }
        }

        @Nested
        @DisplayName("만약 중복된 숫자를 가진 입력이 주어지면")
        class ContextWithInvalidDuplicateNumberTest {

            @ParameterizedTest
            @ValueSource(strings = {"113", "334", "556", "667"})
            @DisplayName("IllegalArgumentException 예외가 발생한다")
            void it_throws_exception(String invalidInput) {
                assertThatThrownBy(() -> new BaseBallNumbers(invalidInput))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage(ExceptionMessageUtil.WRONG_SIZE.findFullMessage());
            }
        }
    }

    @Nested
    @DisplayName("calculateStrike 메소드는")
    class DescribeCalculateStrikeMethodTest extends DefaultBaseBallNumbersBeforeEach {

        private final BaseBallNumbers defaultBaseBallNumbers = new BaseBallNumbers("123");

        @Nested
        @DisplayName("만약 BaseBallNumbers가 주어지면")
        class ContextWithBaseBallNumbersTest {

            @ParameterizedTest
            @CsvSource(
                    value = {
                        "456:0",
                        "156:1",
                        "126:2",
                        "123:3"
                    },
                    delimiter = ':'
            )
            @DisplayName("strike의 개수를 계산해 반환한다")
            void it_returns_strikeCount(String input, long expected) {
                BaseBallNumbers playerAnswer = new BaseBallNumbers(input);

                long actual = defaultBaseBallNumbers.calculateStrike(playerAnswer);

                assertThat(actual).isSameAs(expected);
            }
        }
    }

    @Nested
    @DisplayName("calculateBall 메소드는")
    class DescribeCalculateBallMethodTest extends DefaultBaseBallNumbersBeforeEach {

        @Nested
        @DisplayName("만약 BaseBallNumbers가 주어지면")
        class ContextWithBaseBallNumbersTest {

            @ParameterizedTest
            @CsvSource(
                    value = {
                        "456:0",
                        "416:1",
                        "412:2",
                        "312:3"
                    },
                    delimiter = ':'
            )
            @DisplayName("ball의 개수를 계산해 반환한다")
            void it_returns_ballCount(String input, long expected) {
                BaseBallNumbers playerAnswer = new BaseBallNumbers(input);

                long actual = defaultBaseBallNumbers.calculateBall(playerAnswer);

                assertThat(actual).isSameAs(expected);
            }
        }
    }

    private abstract class DefaultBaseBallNumbersBeforeEach extends DefaultBaseBallNumberGeneratorField {

        protected BaseBallNumbers defaultBaseBallNumbers;

        @BeforeEach
        void initDefaultBaseBallNumbers() {
            defaultBaseBallNumbers = new BaseBallNumbers(generator);
        }
    }
}

