package baseball.view;

import baseball.dto.input.ReadPlayerAnswerDto;
import baseball.dto.input.ReadPlayerCommandDto;
import baseball.utils.consts.GameCommandConst;
import baseball.utils.consts.GameNumberConst;
import baseball.utils.message.ExceptionMessageUtil;
import camp.nextstep.edu.missionutils.Console;
import java.util.function.Consumer;

public class InputView {

    private static final int COMMAND_LENGTH = 1;

    private final Consumer<String> print;

    public InputView(Consumer<String> print) {
        this.print = print;
    }

    public ReadPlayerAnswerDto readPlayerAnswer() {
        print.accept(InputViewMessage.GAME_PLAY.message);

        String playerAnswer = processPlayerInput();

        validatePlayerAnswer(playerAnswer);
        return new ReadPlayerAnswerDto(playerAnswer);
    }

    private void validatePlayerAnswer(String playerAnswer) {
        if (playerAnswer.length() != GameNumberConst.NUMBER_SIZE) {
            throw new IllegalArgumentException(ExceptionMessageUtil.WRONG_SIZE.findFullMessage());
        }
    }

    public ReadPlayerCommandDto readPlayerCommand() {
        print.accept(InputViewMessage.GAME_COMMAND.message);

        String playerCommand = processPlayerInput();

        validatePlayerCommand(playerCommand);
        return new ReadPlayerCommandDto(playerCommand);
    }

    private void validatePlayerCommand(String playerCommand) {
        if (playerCommand.length() != COMMAND_LENGTH) {
            throw new IllegalArgumentException();
        }
    }

    private String processPlayerInput() {
        return Console.readLine();
    }

    private enum InputViewMessage {
        GAME_PLAY("숫자를 입력해주세요 : "),

        GAME_COMMAND("게임을 새로 시작하려면 %d, 종료하려면 %d를 입력하세요.\n",
                GameCommandConst.RETRY,
                GameCommandConst.EXIT);

        private final String message;

        InputViewMessage(String baseMessage, Object...replaces) {
            this.message = String.format(baseMessage, replaces);
        }
    }
}
