package baseball.domain.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import baseball.domain.number.BaseBallNumbers;
import baseball.dto.controller.GameCommandDto;
import baseball.dto.controller.GameResultDto;
import baseball.dto.input.ReadPlayerAnswerDto;
import baseball.dto.input.ReadPlayerCommandDto;
import baseball.utils.game.GameStatus;
import baseball.utils.message.ExceptionMessageUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class BaseBallGameTest {

    private final String defaultComputerAnswer = "123";

    @Nested
    @DisplayName("calculateGameResult 메소드는")
    class DescribeCalculateGameResultMethodTest {

        @Nested
        @DisplayName("만약 1 ~ 9까지의 중복되지 않는 세 자릿수의 유효한 숫자가 저장된 ReadPlayerAnswerDto가 주어지면")
        class ContextWithReadPlayerAnswerDtoTest {

            @ParameterizedTest
            @CsvSource(
                    value = {
                        "456:0:0",
                        "453:1:0",
                        "231:0:3",
                        "125:2:0",
                        "123:3:0",
                    },
                    delimiter = ':'
            )
            @DisplayName("게임 결과를 계산해 GameResultDto를 반환한다")
            void it_returns_gameResultDto(String playerAnswer, long expectedStrike, long expectedBall) {
                ReadPlayerAnswerDto dto = new ReadPlayerAnswerDto(playerAnswer);
                BaseBallGame baseBallGame = new BaseBallGame(new BaseBallNumbers(defaultComputerAnswer));

                GameResultDto actual = baseBallGame.calculateGameResult(dto);

                assertThat(actual.getStrike()).isSameAs(expectedStrike);
                assertThat(actual.getBall()).isSameAs(expectedBall);
            }
        }

        @Nested
        @DisplayName("만약 유효하지 않은 ReadPlayerAnswerDto가 주어지면")
        class ContextWithInvalidReadPlayerAnswerDtoTest {

            @ParameterizedTest
            @ValueSource(strings = {"121", "1234", "12", "@12", " 12", "012"})
            @DisplayName("IllegalArgumentException 예외가 발생한다")
            void it_throws_exception(String playerAnswer) {
                ReadPlayerAnswerDto dto = new ReadPlayerAnswerDto(playerAnswer);

                BaseBallGame baseBallGame = new BaseBallGame(new BaseBallNumbers(defaultComputerAnswer));

                assertThatThrownBy(() -> baseBallGame.calculateGameResult(dto))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }
    }

    @Nested
    @DisplayName("calculateGameCommand 메소드는")
    class DescribeCalculateGameCommandMethodTest {

        private BaseBallGame baseBallGame;

        @BeforeEach
        void initGameComputer() {
            baseBallGame = new BaseBallGame(new BaseBallNumbers(defaultComputerAnswer));
        }

        @Nested
        @DisplayName("만약 1 또는 2의 유효한 command가 저장된 ReadPlayerCommandDto가 주어지면")
        class ContextWithReadPlayerCommandDtoTest {

            @ParameterizedTest
            @CsvSource(
                    value = {
                        "1:GAME_PLAY",
                        "2:APPLICATION_EXIT",
                    },
                    delimiter = ':'
            )
            @DisplayName("게임 재시작 유뮤에 따라 로직을 수행하고 다음 게임 상태를 계산해 GameCommandDto로 반환한다")
            void it_returns_GameCommandDto(String input, GameStatus expected) {
                ReadPlayerCommandDto dto = new ReadPlayerCommandDto(input);

                GameCommandDto gameCommandDto = baseBallGame.calculateGameCommand(dto);

                assertThat(gameCommandDto.getNextGameStatus()).isSameAs(expected);
            }
        }

        @Nested
        @DisplayName("만약 유효하지 않은 ReadPlayerCommandDto가 주어지면")
        class ContextWithInvalidReadPlayerCommandDtoTest {

            @ParameterizedTest
            @ValueSource(strings = {"0", "3", "@", " "})
            @DisplayName("IllegalArgumentException 예외가 발생한다")
            void it_throws_exception(String input) {
                ReadPlayerCommandDto dto = new ReadPlayerCommandDto(input);

                assertThatThrownBy(() -> baseBallGame.calculateGameCommand(dto))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage(ExceptionMessageUtil.WRONG_COMMAND.findFullMessage());
            }
        }
    }
}