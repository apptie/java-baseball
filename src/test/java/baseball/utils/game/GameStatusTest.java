package baseball.utils.game;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class GameStatusTest {

    @Nested
    @DisplayName("findNextPlay 메소드는")
    class DescribeFindNextPlayTest {

        @Nested
        @DisplayName("만약 strike의 값이 주어지면")
        class ContextWithStrikeTest {

            @ParameterizedTest
            @CsvSource(
                    value = {
                        "0:GAME_PLAY",
                        "1:GAME_PLAY",
                        "2:GAME_PLAY",
                        "3:GAME_EXIT"
                    },
                    delimiter = ':'
            )
            @DisplayName("strike 값에 따른 다음 게임 진행 상태를 반환한다")
            void it_returns_nextGameStatus(int strike, GameStatus expected) {
                GameStatus actual = GameStatus.findNextPlay(strike);

                assertThat(actual).isSameAs(expected);
            }
        }
    }

    @Nested
    @DisplayName("playable 메소드는")
    class DescribePlayableMethodTest {

        @Nested
        @DisplayName("만약 호출하면")
        class ContextWithoutParameterTest {

            @ParameterizedTest
            @CsvSource(
                    value = {
                        "APPLICATION_START:true",
                        "GAME_PLAY:true",
                        "GAME_EXIT:true",
                        "APPLICATION_EXIT:false"
                    },
                    delimiter = ':'
            )
            @DisplayName("게임을 계속 진행하는지 여부를 반환한다")
            void it_returns_playable(GameStatus gameStatus, boolean expected) {
                boolean actual = gameStatus.playable();

                assertThat(actual).isSameAs(expected);
            }
        }
    }
}