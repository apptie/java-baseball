package baseball.domain.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import baseball.utils.message.ExceptionMessageUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class GameCommandTest {

    @Nested
    @DisplayName("findGameCommand 메소드는")
    class DescribeFindGameCommandMethodTest {

        @Nested
        @DisplayName("만약 유효한 command가 주어지면")
        class ContextWithCommandTest {

            @ParameterizedTest
            @CsvSource(
                value = {
                    "1:RETRY",
                    "2:EXIT"
                },
                delimiter = ':'
            )
            @DisplayName("command에 맞는 GameCommand를 반환한다")
            void it_returns_gameCommand(String input, GameCommand expected) {
                GameCommand actual = GameCommand.findGameCommand(input);

                assertThat(actual).isSameAs(expected);
            }
        }

        @Nested
        @DisplayName("만약 유효하지 않은 command가 주어지면")
        class ContextWithInvalidCommandTest {

            @ParameterizedTest
            @ValueSource(strings = {"0", "3", "a", "@", " "})
            @DisplayName("command에 맞는 GameCommand를 반환한다")
            void it_returns_gameCommand(String invalidInput) {
                assertThatThrownBy(() -> GameCommand.findGameCommand(invalidInput))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ExceptionMessageUtil.WRONG_COMMAND.findFullMessage());
            }
        }
    }
}