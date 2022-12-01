package baseball.utils.message;

import static org.assertj.core.api.Assertions.assertThat;

import baseball.utils.consts.GameNumberConst;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ExceptionMessageUtilTest {

    @Nested
    @DisplayName("findFullMessage 메소드는")
    class DescribeFindFullMessageMethodTest {

        @Nested
        @DisplayName("만약 상수가 하나인 예외 메세지를 호출하면")
        class ContextWithConstTest {

            @Test
            @DisplayName("상수가 적용된 예외 메세지를 반환한다")
            void it_returns_exception_message() {
                String actual = ExceptionMessageUtil.WRONG_SIZE.findFullMessage();

                assertThat(actual).contains(String.valueOf(GameNumberConst.NUMBER_SIZE));
            }
        }

        @Nested
        @DisplayName("만약 상수가 두개인 예외 메세지를 호출하면")
        class ContextWithConstsTest {

            @ParameterizedTest
            @CsvSource(
                    value = {
                        "WRONG_NUMBER_RANGE:1:9",
                        "WRONG_GENERATOR:1:9",
                        "WRONG_COMMAND:1:2"
                    },
                    delimiter = ':'
            )
            @DisplayName("상수가 적용된 예외 메세지를 반환한다")
            void it_returns_exception_message(ExceptionMessageUtil util,
                    String expectedConst1, String expectedConst2) {
                String actual = util.findFullMessage();

                assertThat(actual)
                        .contains(expectedConst1)
                        .contains(expectedConst2);
            }
        }

        @Nested
        @DisplayName("만약 상수가 존재하지 않는 예외 메세지를 호출하면")
        class ContextWithoutConstTest {

            @Test
            @DisplayName("예외 메세지를 반환한다")
            void it_returns_exception_message() {
                String actual = ExceptionMessageUtil.WRONG_NUMBER.findFullMessage();

                assertThat(actual).contains("정답은 숫자여야 합니다");
            }
        }
    }
}