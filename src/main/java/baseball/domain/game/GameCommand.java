package baseball.domain.game;

import baseball.utils.consts.GameCommandConst;
import baseball.utils.message.ExceptionMessageUtil;
import java.util.Arrays;

public enum GameCommand {
    RETRY(GameCommandConst.RETRY), EXIT(GameCommandConst.EXIT);

    private final String command;

    GameCommand(String command) {
        this.command = command;
    }

    public static GameCommand findGameCommand(String command) {
        return Arrays.stream(GameCommand.values())
                .filter(gameCommand -> gameCommand.command.equals(command))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessageUtil
                        .WRONG_COMMAND.findFullMessage()));
    }
}
