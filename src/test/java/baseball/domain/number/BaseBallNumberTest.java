package baseball.domain.number;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class BaseBallNumberTest {

    @Nested
    @DisplayName("int number, int index를 매개변수로 받는 생성자는")
    class DescribeConstructorTest {

        @Nested
        @DisplayName("만약 유효한 숫자와 인덱스를 전달받으면")
        class ContextWithNumberAndIndexTest {

            @ParameterizedTest
            @CsvSource(
                value = {
                    "1:0",
                    "2:1",
                    "3:2",
                    "4:2",
                    "5:2",
                    "6:2",
                    "7:2",
                    "8:2",
                    "9:2"
                },
                delimiter = ':'
            )
            @DisplayName("number, index를 초기화한 BaseBallNumber를 반환한다")
            void it_returns_baseBallNumber(int number, int index) {
                assertThatCode(() -> new BaseBallNumber(number, index)).doesNotThrowAnyException();
            }
        }

        @Nested
        @DisplayName("만약 유효하지 않은 숫자와 유효한 인덱스를 전달받으면")
        class ContextWithInvalidNumberAndIndexTest {

            @ParameterizedTest
            @ValueSource(ints = {0, 10})
            @DisplayName("IllegalArgumentException 예외가 발생한다")
            void it_returns_baseBallNumber(int invalidNumber) {
                assertThatThrownBy(() -> new BaseBallNumber(invalidNumber, 1))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("만약 유효한 숫자와 유효하지 않은 인덱스를 전달받으면")
        class ContextWithNumberAndInvalidIndexTest {

            @ParameterizedTest
            @ValueSource(ints = {3, 4, 5})
            @DisplayName("IllegalArgumentException 예외가 발생한다")
            void it_returns_baseBallNumber(int invalidIndex) {
                assertThatThrownBy(() -> new BaseBallNumber(1, invalidIndex))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }
    }

    @Nested
    @DisplayName("equals 메소드는")
    class DescribeEqualsMethodTest extends DefaultBaseBallNumberBeforeEach {

        @Nested
        @DisplayName("만약 BaseBallNumber가 주어지면")
        class ContextWithBaseBallNumberTest {

            @ParameterizedTest
            @CsvSource(
                    value = {
                        "2:1:false",
                        "1:0:false",
                        "1:1:true"
                    },
                    delimiter = ':'
            )
            @DisplayName("주어진 BaseBallNumber와 number, index가 같은지 여부를 반환한다")
            void it_returns_equals(int number, int index, boolean expected) {
                boolean actual = defaultBaseBallNumber.equals(new BaseBallNumber(number, index));

                assertThat(actual).isSameAs(expected);
            }
        }
    }

    @Nested
    @DisplayName("equalsOnlyNumber 메소드는")
    class DescribeEqualsOnlyNumberMethodTest extends DefaultBaseBallNumberBeforeEach {

        @Nested
        @DisplayName("만약 BaseBallNumber가 주어지면")
        class ContextWithBaseBallNumber {

            @ParameterizedTest
            @CsvSource(
                    value = {
                        "1:0:true",
                        "1:1:false",
                        "2:0:false",
                        "2:1:false"
                    },
                    delimiter = ':'
            )
            @DisplayName("number가 같고 index가 다른지 여부를 반환한다")
            void it_returns_equalsNumber(int number, int index, boolean expected) {
                boolean actual = defaultBaseBallNumber.equalsOnlyNumber(new BaseBallNumber(number, index));
            }
        }
    }

    private abstract class DefaultBaseBallNumberBeforeEach {

        protected BaseBallNumber defaultBaseBallNumber;

        @BeforeEach
        void initDefaultBaseBallNumber() {
            defaultBaseBallNumber = new BaseBallNumber(1, 1);
        }
    }
}